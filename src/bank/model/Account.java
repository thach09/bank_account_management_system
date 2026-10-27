package bank.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class Account {
    private String accountNumber;
    private long balance;
    private Date openDate;
    private Customer owner;
    private List<Transaction> transactions;

    public Account(String accountNumber, Customer owner, long initialDeposit) {
        this.accountNumber = accountNumber;
        this.owner = owner;
        this.balance = initialDeposit;
        this.openDate = new Date();
        this.transactions = new ArrayList<Transaction>();
    }

    public void deposit(long amount, TransactionType type, String note) {
        // TODO: validate amount and update balance
    }

    public void withdraw(long amount, TransactionType type, String note) {
        // TODO: validate amount and deduct from balance
    }

    public abstract long getMinAllowedBalance();

    public abstract long calculateMonthlyAdjustment();

    public abstract String getAccountType();

    public void applyMonthlyAdjustment() {
        // TODO: calculate monthly adjustment and apply to balance
    }

    protected void validateAmount(long amount) {
        // TODO: validate amount > 0
    }

    protected void addTransaction(TransactionType type, long amount, String note) {
        // TODO: record transaction in the transactions list
    }

    public long getBalance() {
        return balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public Customer getOwner() {
        return owner;
    }

    public Date getOpenDate() {
        return openDate;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }
}
