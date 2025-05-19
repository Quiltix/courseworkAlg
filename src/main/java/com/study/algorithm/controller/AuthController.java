package com.study.algorithm.controller;


import com.study.algorithm.dto.auth.JwtAuthenticationResponseDTO;
import com.study.algorithm.dto.auth.LoginRequestDTO;
import com.study.algorithm.dto.auth.RegisterRequestDTO;
import com.study.algorithm.dto.other.MessageDTO;
import com.study.algorithm.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/register")
    public ResponseEntity <MessageDTO> registerUser (@RequestBody @Valid RegisterRequestDTO registerRequestDTO){

        String message = userService.registerUser(registerRequestDTO);

        return ResponseEntity.ok().body(new MessageDTO(message));

    }

    @PostMapping("/login")
    public ResponseEntity<JwtAuthenticationResponseDTO> authenticateUser(@Valid @RequestBody LoginRequestDTO loginRequestDTO) {

        String token = userService.authenticateUser(loginRequestDTO);

        return ResponseEntity.ok().body(new JwtAuthenticationResponseDTO(token));

    }


}
