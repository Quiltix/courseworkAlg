package com.study.algorithm.service;


import com.study.algorithm.model.AlgorithmRepository;
import com.study.algorithm.model.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class AlgorithmServiceTest {

    private AlgorithmService service;
    private  UserRepository userRepository;
    private  AlgorithmRepository algorithmRepository;

    @BeforeEach
    public void setUp() {
        userRepository = Mockito.mock(UserRepository.class);
        algorithmRepository = Mockito.mock(AlgorithmRepository.class);
        service = new AlgorithmService(userRepository,algorithmRepository);
    }

    // Позитивные тесты для calculatePrefix
    @Test
    public void testCalculatePrefix1() {
        assertArrayEquals(new int[]{0}, service.calculatePrefix("a"));
    }

    @Test
    public void testCalculatePrefix2() {
        assertArrayEquals(new int[]{0, 1}, service.calculatePrefix("aa"));
    }

    @Test
    public void testCalculatePrefix3() {
        assertArrayEquals(new int[]{0, 0, 0}, service.calculatePrefix("abc") );
    }

    @Test
    public void testCalculatePrefix4() {
        assertArrayEquals(new int[]{0, 0, 1, 2}, service.calculatePrefix("abab"));
    }

    // Негативный тест для calculatePrefix
    @Test
    public void testCalculatePrefixEmptyString() {
        assertArrayEquals(new int[]{}, service.calculatePrefix(""));
    }

    // Позитивные тесты для calculateKMP
    @Test
    public void testCalculateKMP1() {
        assertEquals(List.of(0, 2, 4), service.calculateKMP("abababab", "abab"));
    }

    @Test
    public void testCalculateKMP2() {
        assertEquals(List.of(0, 3, 6), service.calculateKMP("abcabcabc", "abc"));
    }

    @Test
    public void testCalculateKMP3() {
        assertEquals(List.of(1), service.calculateKMP("a ", " "));
    }

    // Негативные тесты для calculateKMP
    @Test
    public void testCalculateKMP4() {
        assertEquals(List.of(), service.calculateKMP("abc", "d"));
    }

    @Test
    public void testCalculateKMP5() {
        assertEquals(List.of(), service.calculateKMP("abc", "ddddd"));
    }

    // Граничные тесты для calculateKMP
    @Test
    public void testCalculateKMP6() {
        assertEquals(List.of(), service.calculateKMP("", "a"));
    }

    @Test
    public void testCalculateKMP7() {
        assertEquals(List.of(), service.calculateKMP("a", ""));
    }
}


