package com.arbol.services;

import com.arbol.dto.UserDto;
import com.arbol.security.JwtUtil;
import com.arbol.dto.AuthRequest;
import com.arbol.dto.AuthResponse;
import com.arbol.models.User;
import com.arbol.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired private UserRepository userRepository;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private JwtUtil jwtUtil;
    @Autowired private AuthenticationManager authenticationManager;
    @Autowired private PerfilService perfilService;
    @Autowired private UserService userService;
    private Long idperfil_inicial = (long) 1;

    public AuthResponse register(User user){
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("El nombre de usuario ya está registrado");
        }

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("El correo electrónico ya está registrado");
        }

        String rawPassword = user.getPassword();
        user.setPassword(passwordEncoder.encode(rawPassword));
        perfilService.getPerfilById(this.idperfil_inicial).ifPresent(user::setPerfil);

        User nuevoUser = userRepository.save(user);
        userService.cargarMenu(nuevoUser);


        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), rawPassword)
        );

        // si pasa lo anterior, se genera el token
        String token = jwtUtil.generateToken(user.getUsername());
        return new AuthResponse(token, new UserDto(nuevoUser));
    }

    public AuthResponse login(AuthRequest request){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        userService.cargarMenu(user);

        // si pasa lo anterior, se genera el token
        String token = jwtUtil.generateToken(request.getUsername());
        return new AuthResponse(token, new UserDto(user));
    }


}
