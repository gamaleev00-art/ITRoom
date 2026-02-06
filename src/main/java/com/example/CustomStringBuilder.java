package com.example;

import java.util.Arrays;
import java.util.Stack;

public class CastomStringBuilder {
    private int index;
    private char[] chars;
    private final Stack<Snapshot> history = new Stack<>();

    private record Snapshot(char[] chars) {};

    public CastomStringBuilder(String str) {
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

    public CastomStringBuilder() {
        this.chars = new char[0];
        this.index = 0;
        history.push(new  Snapshot(Arrays.copyOf(chars, chars.length)));
    }

    public CastomStringBuilder append(String str) {
        Snapshot snapshot = history.peek();
        char[] csb = snapshot.chars;
        this.index = csb.length;
        int stringIndex = 0;
        this.chars = Arrays.copyOf(csb, index + str.length());
        while (index < chars.length) {
            chars[index++] = str.charAt(stringIndex++);
        }
        history.push(new  Snapshot(Arrays.copyOf(chars, chars.length)));
        return this;
    }

    public void undo() {
        if (history.isEmpty()) {
            Snapshot snapshot = history.peek();
            history.push(snapshot);
        }
    }

    @Override
    public String toString() {
        return new String(chars);
    }
}
