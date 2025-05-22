package com.study.algorithm.controller;


import com.study.algorithm.dto.auth.JwtAuthenticationResponseDTO;
import com.study.algorithm.dto.auth.LoginRequestDTO;
import com.study.algorithm.dto.auth.RegisterRequestDTO;
import com.study.algorithm.dto.other.MessageDTO;
import com.study.algorithm.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
    @Operation(
            summary = "Регистрация пользователя",
            description = "Создает нового пользователя в системе",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Успешная регистрация",
                            content = @Content(schema = @Schema(implementation = MessageDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Некорректные входные данные"
                    )
            }
    )
    public ResponseEntity <MessageDTO> registerUser (@RequestBody @Valid RegisterRequestDTO registerRequestDTO){

        String message = userService.registerUser(registerRequestDTO);

        return ResponseEntity.ok().body(new MessageDTO(message));

    }

    @PostMapping("/login")
    @Operation(
            summary = "Аутентификация пользователя",
            description = "Возвращает JWT токен для авторизованного доступа",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Успешная аутентификация",
                            content = @Content(schema = @Schema(implementation = JwtAuthenticationResponseDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Неверные учетные данные"
                    )
            }
    )
    public ResponseEntity<JwtAuthenticationResponseDTO> authenticateUser(@Valid @RequestBody LoginRequestDTO loginRequestDTO) {

        String token = userService.authenticateUser(loginRequestDTO);

        return ResponseEntity.ok().body(new JwtAuthenticationResponseDTO(token));

    }


}
