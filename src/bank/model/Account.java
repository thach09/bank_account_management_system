package bank.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class Account {
    private String accountCode;
    private long balance;
    private Date openDate;
    private Customer owner;
    private List<Transaction> transaction;

    public Account(String accountCode, Customer owner, long initialDeposit) {
        this.accountCode = accountCode;
        this.owner = owner;
        this.balance = initialDeposit;
        this.openDate = new Date();
        this.transaction = new ArrayList<Transaction>();
    }

    public void deposit(long amount) {
        // TODO: validate amount and update balance
    }

    public void withdraw(long amount) {
        // TODO: validate amount and deduct from balance
    }

    public abstract long getMinAllowedBalance();

    public abstract long calculateMonthlyAdjustment();

    public abstract String getAccountType();

    public void applyMonthlyAdjustment() {
        // TODO: calculate monthly adjustment and apply to balance
    }

    private void validateAmount(long amount) {
        // TODO: validate amount > 0
    }

    private void addTransaction(TransactionType type, long amount, String note) {
        // TODO: record transaction to transaction list
    }

    public long getBalance() {
        return balance;
    }

    public String getAccountNumber() {
        return accountCode;
    }

    public Customer getOwner() {
        return owner;
    }

    public List<Transaction> getTransactions() {
        return transaction;
    }
}
