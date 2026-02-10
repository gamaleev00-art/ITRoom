package com.example;

import java.util.concurrent.Callable;
import java.util.concurrent.ThreadLocalRandom;

public class ComplexTask {

    public Integer execute(){
        try {
            Thread.sleep(Math.round(Math.random()*1000));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return 10;
    }
}
