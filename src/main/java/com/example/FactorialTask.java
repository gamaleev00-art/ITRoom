package com.example;

import java.util.concurrent.RecursiveTask;

public class FactorialTask extends RecursiveTask<Long> {

    private final long end;
    private final long start;

    public FactorialTask(long end) {
        this(1,end);
    }

    private FactorialTask(long start, long end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        if (start== end){
            return start;
        }
        if (end -start <=1){
            return end*start;
        }

        long half = (start+end)/2;
        FactorialTask leftTask = new FactorialTask(start,half);
        FactorialTask rightTask = new FactorialTask(half+1,end);
        leftTask.fork();
        return rightTask.compute() * leftTask.join();
    }

    private Long factorial(long start, long end) {
        long factorial = 1;
        for (long i = start; i <= end; i++) {
            factorial *= i;
        }
        return factorial;
    }
}
