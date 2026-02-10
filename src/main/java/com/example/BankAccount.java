package com.example;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicInteger;

public class BankAccount {
    private final int id;
    private BigDecimal balance;

    public BankAccount(int id,BigDecimal balance) {
        this.balance = balance;
        this.id = id;
    }

    public synchronized void deposit(BigDecimal amount) {
        balance = balance.add(amount);
    }

    public synchronized boolean withdraw(BigDecimal amount) {
        if (balance.compareTo(amount) >= 0) {
            balance = balance.subtract(amount);
            return true;
        } else {
            System.out.println("Недостаточно средств");
            return false;
        }
    }

    public synchronized BigDecimal getBalance() {
        return balance;
    }
}
