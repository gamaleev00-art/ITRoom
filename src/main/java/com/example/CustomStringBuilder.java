package com.example;

import java.util.Arrays;
import java.util.Stack;

public class CustomStringBuilder {
    private int index;
    private char[] chars;
    private final Stack<Snapshot> history = new Stack<>();

    private record Snapshot(char[] chars) {};

    public CustomStringBuilder(String str) {
        this.index = 0;
        if (str == null) {
            chars = new char[0];
        } else {
            this.chars = new char[str.length()];
            while (index < str.length()) {
                chars[index] = str.charAt(index);
                index++;
            }
        }
        history.push(new  Snapshot(Arrays.copyOf(chars, chars.length)));
    }

    public CustomStringBuilder() {
        this.chars = new char[0];
        this.index = 0;
        history.push(new  Snapshot(Arrays.copyOf(chars, chars.length)));
    }

    public CustomStringBuilder append(String str) {
        chars = Arrays.copyOf(chars, str.length()+index);
        int stringIndex = 0;
        while (index < chars.length) {
            chars[index++] = str.charAt(stringIndex++);
        }
        history.push(new  Snapshot(Arrays.copyOf(chars, chars.length)));
        return this;
    }

    public void undo() {
        if (history.size() <= 1) {
            chars = new char[0];
        } else  {
            history.pop();
            Snapshot snapshot = history.peek();
            chars =  snapshot.chars;
            index = snapshot.chars.length;
        }
    }

    @Override
    public String toString() {
        return new String(chars);
    }
}
