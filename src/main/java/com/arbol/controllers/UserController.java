package com.arbol.controllers;

import com.arbol.dto.UserCreateDto;
import com.arbol.dto.UserDto;
import com.arbol.dto.UserSimpleDto;
import com.arbol.models.User;
import com.arbol.models.db.Query;
import com.arbol.models.response.HttpOk;
import com.arbol.security.CustomUserDetails;
import com.arbol.services.CustomUserDetailsService;
import com.arbol.services.UserService;
import com.arbol.util.Response;
import com.arbol.util.Type;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final Response response = new Response(Type.USUARIO);
    private final UserService userService;
    private final CustomUserDetailsService customUserDetailsService;

    @PreAuthorize("hasAuthority('user_select')")
    @PostMapping("/data")
    public ResponseEntity<HttpOk> findAll(
            HttpServletRequest request,
            @RequestBody Query query
    ) {
        return response.find(userService.findAllSimple(query));
    }

    @PreAuthorize("hasAuthority('user_select')")
    @GetMapping("/{id}")
    public ResponseEntity<HttpOk> getUserSimpleById(
        HttpServletRequest request,
        @PathVariable Long id
    )
    {
        return response.find(userService.getUserSimpleById(id));
    }

    @PreAuthorize("hasAuthority('user_insert')")
    @PostMapping
    public ResponseEntity<HttpOk> createUser(@RequestBody UserCreateDto user) {
        UserSimpleDto newUser =  userService.createUser(user);
        return response.create(newUser.getId().toString(), newUser);
    }

    @PreAuthorize("hasAuthority('user_delete')")
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpOk> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return response.delete(id.toString());
    }

    @PreAuthorize("hasAuthority('user_update')")
    @PutMapping("/{id}")
    public ResponseEntity<HttpOk> updateUser(@PathVariable Long id, @RequestBody User user){
        userService.updateUser(id, user);
        return response.update(id.toString());
    }

    @GetMapping("/me")
    public ResponseEntity<HttpOk> getCurrentUser(HttpServletRequest request){
        CustomUserDetails usuario = customUserDetailsService.getUserLogueado();
        User user = userService.getUserById(usuario.getId());
        return response.find(new UserDto(userService.cargarMenu(user)));
    }
}
