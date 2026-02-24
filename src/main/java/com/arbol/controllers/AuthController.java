package com.arbol.controllers;

import com.arbol.services.AuthService;
import com.arbol.dto.AuthRequest;
import com.arbol.dto.AuthResponse;
import com.arbol.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
//@CrossOrigin(origins = "http://localhost:4200") // permite solo desde Angular
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody User user){
        AuthResponse response = authService.register(user);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request){
        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }

}
