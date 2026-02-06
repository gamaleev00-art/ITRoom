package com.example;

public interface Filter<T> {
    T apply(T o);
}
