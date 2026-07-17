package com.banking.authservice.controller;

import com.banking.authservice.dto.LoginRequest;
import com.banking.authservice.dto.RegisterRequest;
import com.banking.authservice.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
     private final AuthService authService;
     @PostMapping("/register")
     public String register(@RequestBody RegisterRequest registerRequest ) {
         return authService.register(registerRequest);

     }
    @PostMapping("/login")
     public String login(@RequestBody LoginRequest loginRequest ) {
         return authService.login(loginRequest);
     }
}
