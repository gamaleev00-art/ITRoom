package com.example;

import java.util.LinkedList;

public class BlockingQueue<E> {

    private static final int QUEUE_SIZE = 10;

    private final LinkedList<E> queue = new  LinkedList<>();

    public BlockingQueue() {
    }

    public BlockingQueue(E e) {
        queue.addLast(e);
    }

    public synchronized void enqueue(E e) {
        while (queue.size() == QUEUE_SIZE) {
            try {
                wait();
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
                return;
            }
        }
        queue.addLast(e);
        notifyAll();
    }

    public synchronized E dequeue() {
        if (queue.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
                return null;
            }
        }
        E e = queue.removeFirst();
        notifyAll();
        return e;
    }

    public synchronized int size() {
        return queue.size();
    }

    @Override
    public String toString() {
        return queue.toString();
    }
}
