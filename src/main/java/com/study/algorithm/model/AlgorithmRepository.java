package com.study.algorithm.model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AlgorithmRepository extends JpaRepository<Algorithm,Long> {

    List<Algorithm> findByOwner(User user);
}
