package bank.model;

import java.util.ArrayList;
import java.util.List;

public abstract class Account {
    private String accountNumber;
    protected double balance;
    private Customer customer;
    private List<Transaction> transactions;

    public Account(String accountNumber, double balance, Customer customer) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.customer = customer;
        this.transactions = new ArrayList<Transaction>();
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public Customer getCustomer() {
        return customer;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void deposit(double amount) {
        // TODO: validate amount > 0 -> increase balance -> record transaction
    }

    public abstract void withdraw(double amount);

    public abstract void applyMonthlyAdjustment();
}
