package com.study.algorithm.model;



import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.crypto.encrypt.Encryptors;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Algorithm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String text;

    private String substring;


    private List<Integer> result;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "owner_id")
    private User owner;

}
