package com.study.algorithm.controller;


import com.study.algorithm.dto.algorithm.RequestAlgorithmDTO;
import com.study.algorithm.model.Algorithm;
import com.study.algorithm.service.AlgorithmService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/algorithm")
public class AlgorithmController {

    private final AlgorithmService algorithmService;

    public AlgorithmController(AlgorithmService algorithmService) {
        this.algorithmService = algorithmService;
    }


    @PostMapping
    @Operation(
            summary = "Решить алгоритм KMP",
            description = "Вычисляет вхождения образца в тексте с использованием алгоритма Кнута-Морриса-Пратта",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Успешное выполнение",
                            content = @Content(schema = @Schema(implementation = List.class))
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Пользователь не авторизован"
                    )
            }
    )
    public ResponseEntity<List<Integer>> solveKMP(Authentication authentication,
                                                  @RequestBody RequestAlgorithmDTO requestAlgorithmDTO) {
        List<Integer> result = algorithmService.solveKMP(authentication, requestAlgorithmDTO);
        return ResponseEntity.ok(result);
    }

    @GetMapping
    @Operation(
            summary = "Получить решения пользователя",
            description = "Возвращает список всех решений текущего пользователя",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Успешное получение данных",
                            content = @Content(schema = @Schema(implementation = Algorithm.class))
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Пользователь не авторизован"
                    )
            }
    )
    public ResponseEntity<List<Algorithm>> getAllSolvesByUser(Authentication authentication) {
        List<Algorithm> result = algorithmService.allSolvesByUser(authentication);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/all")
    @Operation(
            summary = "Получить все решения",
            description = "Возвращает список всех решений в системе (требуются права администратора)",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Успешное получение данных",
                            content = @Content(schema = @Schema(implementation = Algorithm.class)))
                            }
                    )
    public ResponseEntity<List<Algorithm>> getAllSolves() {
        List<Algorithm> result = algorithmService.allSolves();
        return ResponseEntity.ok(result);
    }
}
