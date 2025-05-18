package com.study.algorithm.controller;


import com.study.algorithm.dto.algorithm.RequestAlgorithmDTO;
import com.study.algorithm.model.Algorithm;
import com.study.algorithm.service.AlgorithmService;
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
    public ResponseEntity<List<Integer>> solveKMP(Authentication authentication,
                                                  @RequestBody RequestAlgorithmDTO requestAlgorithmDTO) {
        List<Integer> result = algorithmService.solveKMP(authentication, requestAlgorithmDTO);
        return ResponseEntity.ok(result);
    }

    @GetMapping
    public ResponseEntity<List<Algorithm>> getAllSolvesByUser(Authentication authentication) {
        List<Algorithm> result = algorithmService.allSolvesByUser(authentication);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Algorithm>> getAllSolves() {
        List<Algorithm> result = algorithmService.allSolves();
        return ResponseEntity.ok(result);
    }
}
