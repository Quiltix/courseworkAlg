package com.study.algorithm.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AlgorithmService {


    private int[] calculatePrefix(String prefix){
        int[] result = new int[prefix.length()];
        int i = 0,j=0;
        while (i<prefix.length()){
            if (prefix.charAt(i)!=prefix.charAt(j)) {
                if (j == 0) {
                    result[i] = 0;
                    i++;
                }
                else{
                    j = result[j-1];
                }
            }
            else {
                result[i] = j+1;
                i++;
                j++;

            }
        }
        return result;

    }

    private List<Integer> calc(String text,String substring){
        int[] p = calculatePrefix(substring);
        List<Integer> result= new ArrayList<>();
        int i = 0;
        int j = 0;
        int n = text.length();
        int m = substring.length();
        while (i<n){
            if (text.charAt(i) == text.charAt(j)){
                i++;
                j++;
                if (j==m){
                    result.add(i);
                }
            }
            else {
                if (j>0){
                    j = p[j-1];
                }
                else{
                    i++;
                    if (i==n){
                        result.add(-1);
                    }
                }
            }
        }
        return result;
    }
}
