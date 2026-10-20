package bank.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class Account implements MonthlyAdjustable {
    protected String accountNumber;
    protected long balance;
    protected Date openDate;
    protected Customer owner;
    protected List<Transaction> transactions;

    public Account(String accountNumber, Customer owner, long initialDeposit) {
        this.accountNumber = accountNumber;
        this.owner = owner;
        this.balance = initialDeposit;
        this.openDate = new Date();
        this.transactions = new ArrayList<Transaction>();
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

    @Override
    public void applyMonthlyAdjustable() {
        applyMonthlyAdjustment();
    }

    @Override
    public long calculateMonthlyAdjustable() {
        return calculateMonthlyAdjustment();
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

    public List<Transaction> getTransactions() {
        return transactions;
    }
}
