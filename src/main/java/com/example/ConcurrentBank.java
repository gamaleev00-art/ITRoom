package com.example;

import java.math.BigDecimal;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class ConcurrentBank {

    private ConcurrentHashMap<Integer, BankAccount> accounts = new ConcurrentHashMap<>();
    AtomicInteger idGenerator = new AtomicInteger(0);

    public BankAccount createAccount(BigDecimal balance) {
        int id = idGenerator.incrementAndGet();
        BankAccount bankAccount = new BankAccount(id, balance);
        accounts.put(id, bankAccount);
        return bankAccount;
    }

    public void transfer(BankAccount fromAccount, BankAccount toAccount, BigDecimal amount) {
        synchronized (fromAccount) {
            synchronized (toAccount) {
                if (fromAccount.withdraw(amount)) {
                    toAccount.deposit(amount);
                }
            }
        }
    }

    public BigDecimal getTotalBalance() {
        synchronized (this) {
            return accounts.values().stream()
                    .map(BankAccount::getBalance)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        }
    }
}
