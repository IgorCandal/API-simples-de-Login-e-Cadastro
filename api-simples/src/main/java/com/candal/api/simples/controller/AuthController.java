package com.candal.api.simples.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.candal.api.simples.model.User;
import com.candal.api.simples.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService service;

    public AuthController(UserService service){
        this.service = service;
    }

   @PostMapping("/register")
   public ResponseEntity<?> cadastrar(@RequestBody User users) {
       try {
        User newUser = service.register(users);
        newUser.setPassword(null);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
       } catch (Exception e){
        return ResponseEntity.badRequest().body(Map.of("erro", e.getMessage()));
       }
   }

   @PostMapping("/login")
   public ResponseEntity<?> login(@RequestBody Map<String, String> dadosLogin) {
        
        String email = dadosLogin.get("email");
        String password = dadosLogin.get("password");

        boolean valid = service.validateLogin(email, password);

        if (valid){
            return ResponseEntity.ok(Map.of("message", "Login realizado com sucesso!"));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("erro", "E-mail ou senha inválidos."));
        }
   }
}
