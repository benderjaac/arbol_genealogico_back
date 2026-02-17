package com.arbol.services;

import com.arbol.exceptions.BadRequestException;
import com.arbol.dto.UserCreateDto;
import com.arbol.dto.UserSimpleDto;
import com.arbol.exceptions.NotFoundException;
import com.arbol.models.Perfil;
import com.arbol.models.Permiso;
import com.arbol.models.User;
import com.arbol.models.db.Query;
import com.arbol.models.db.Result;
import com.arbol.repositories.DBRepository;
import com.arbol.repositories.PerfilRepository;
import com.arbol.repositories.UserRepository;
import com.arbol.util.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PerfilRepository perfilRepository;

    @Autowired
    private PerfilService perfilService;

    @Autowired
    private DBRepository db;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Result<UserSimpleDto> findAllSimple(Query query){
        query.addFetch("perfil");
        Result<User> result = db.findAll(User.class, query, false);
        List<UserSimpleDto> dtoList = result.getData().stream()
                .map(UserSimpleDto::new)
                .collect(Collectors.toList());
        return new Result<>(dtoList, result.getPagination());
    }

    public UserSimpleDto getUserSimpleById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException(Type.USUARIO, id));
        return new UserSimpleDto(user);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException(Type.USUARIO, id));
    }

    public UserSimpleDto createUser(UserCreateDto userdto) {
        // Validación de datos mínimos
        if (userdto.getPerfilId() == null) {
            throw new BadRequestException("El ID del perfil es obligatorio");
        }
        // Buscar el perfil
        Perfil perfil = perfilRepository.findById(userdto.getPerfilId())
                .orElseThrow(() -> new BadRequestException("Perfil no encontrado"));


        String encodedPassword = passwordEncoder.encode("123456");

        // Crear entidad User
        User user = new User();
        user.setUsername(userdto.getUsername());
        user.setEmail(userdto.getEmail());
        user.setPassword(encodedPassword);
        user.setPerfil(perfil);

        return new UserSimpleDto(userRepository.save(user));
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public void updateUser(Long id, User user){
        User userActual = getUserById(id);
        userActual.setPerfil(user.getPerfil());
        userActual.setEmail(user.getEmail());
        userActual.setUsername(user.getUsername());
        userRepository.save(userActual);
    }

    public User cargarMenu(User user) {
        if (user.getPerfil() != null) {
            List<Permiso> menuEstructurado  = perfilService.getMenuEstructuradoPorPerfil(user.getPerfil().getId());
            user.getPerfil().setMenu(menuEstructurado);
        }
        return user;
    }
}
