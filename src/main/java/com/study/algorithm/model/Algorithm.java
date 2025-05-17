package com.study.algorithm.model;



import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Algorithm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String text;

    private String substring;

    @ManyToOne
    @JoinColumn(name = "User_id")
    private String owner;
}
