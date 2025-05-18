package com.study.algorithm.service;

import com.study.algorithm.dto.algorithm.RequestAlgorithmDTO;
import com.study.algorithm.model.Algorithm;
import com.study.algorithm.model.AlgorithmRepository;
import com.study.algorithm.model.User;
import com.study.algorithm.model.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AlgorithmService {

    private final UserRepository userRepository;
    private final AlgorithmRepository algorithmRepository;

    public AlgorithmService(UserRepository userRepository, AlgorithmRepository algorithmRepository) {
        this.userRepository = userRepository;
        this.algorithmRepository = algorithmRepository;
    }

    public List<Integer> solveKMP(Authentication authentication, RequestAlgorithmDTO requestAlgorithmDTO){
        String username = authentication.getName();
        User user = userRepository.findUserByUsername(username).orElseThrow(
                ()-> new UsernameNotFoundException("User not found"));
        List<Integer> resKMP = calculateKMP(requestAlgorithmDTO.getText(),requestAlgorithmDTO.getSubstring());
        Algorithm algorithm = Algorithm.builder()
                .text(requestAlgorithmDTO.getText())
                .substring(requestAlgorithmDTO.getSubstring())
                .result(resKMP)
                .owner(user)
                .build();

        algorithmRepository.save(algorithm);

        return resKMP;



    }

    public List<Algorithm> allSolvesByUser(Authentication authentication) {

        String username = authentication.getName();
        User user = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        return algorithmRepository.findByOwner(user);
    }

    public List<Algorithm> allSolves() {
        return algorithmRepository.findAll();
    }




    public int[] calculatePrefix(String prefix){
        int m = prefix.length();
        if (m==0){
            return new int[0];
        }
        int[] pi = new int[m];
        pi[0] = 0;
        int k = 0;

        for (int i = 1; i < m; i++) {
            while (k > 0 && prefix.charAt(k) != prefix.charAt(i)) {
                k = pi[k - 1];
            }
            if (prefix.charAt(k) == prefix.charAt(i)) {
                k++;
            }
            pi[i] = k;
        }
        return pi;
    }

    public List<Integer> calculateKMP(String text,String substring){
        int[] p = calculatePrefix(substring);
        List<Integer> result= new ArrayList<>();
        int i = 0, j = 0;
        int n = text.length();
        int m = substring.length();
        if (n < m || n==0 || m==0) { return result;}
        while (i<n){
            if (text.charAt(i) == substring.charAt(j)){
                i++;
                j++;
                if (j==m){
                    result.add(i - m);
                    j = p[j - 1];
                }
            }
            else {
                if (j>0){
                    j = p[j-1];
                }
                else{
                    i++;
                    if (i==n){break;}
                }
            }
        }
        return result;
    }
}
