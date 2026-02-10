package com.example;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAdder;


public class ComplexTaskExecutor {

    private final int numberOfThreads;

    public ComplexTaskExecutor(int numberOfTasks) {
        this.numberOfThreads = numberOfTasks;
    }

    public void executeTasks(int numberOfTasks)  {
        LongAdder counter = new LongAdder();
        ExecutorService executor = Executors.newFixedThreadPool(numberOfTasks);
        CyclicBarrier barrier = new CyclicBarrier(numberOfTasks,()->{
            System.out.println(counter.sum());
        });
        for (int i = 0; i < numberOfTasks; i++) {
            executor.submit(()->{
                ComplexTask task = new ComplexTask();
                counter.add(task.execute());
                try {
                    barrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        executor.shutdown();
        try {
            executor.awaitTermination(Long.MAX_VALUE,TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
