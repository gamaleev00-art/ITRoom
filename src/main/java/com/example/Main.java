package com.example;
public class Main {
    public static void main(String[] args) {

        }

    public <T> T[] filter(T[] array, Filter<T> filter){
        T[] newArray = array.clone();
        for(int i=0;i<array.length;i++){
            newArray[i] = filter.apply(array[i]);
        }
        return newArray;
    }
    }