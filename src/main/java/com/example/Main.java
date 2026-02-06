package com.example;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {

    }
    public <T> Map<T,Integer> keyCounter(T[] array){
        Map<T,Integer> map = new HashMap<>();
        for(T element : array){
            map.put(element,map.getOrDefault(element,1)+1);
        }
        return map;
    }
}