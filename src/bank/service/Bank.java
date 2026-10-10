package bank.service;

import bank.model.Account;
import bank.model.Customer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Bank {
    private String bankName;
    private Map<String, Account> accounts;
    private Map<String, Customer> customers;

    public Bank() {
        this.accounts = new HashMap<String, Account>();
        this.customers = new HashMap<String, Customer>();
    }

    public void openAccount(String customerID, Account account) {
        // TODO: find customer by customerID -> store account in accounts -> link to customer
    }

    public Account getAccount(String accountNumber) {
        // TODO: find and return account by accountNumber
        return accounts.get(accountNumber);
    }

    public void deposit(String accountNumber, double amount) {
        // TODO: find account -> validate amount -> delegate deposit
    }

    public void withdraw(String accountNumber, double amount) {
        // TODO: find account -> delegate withdraw
    }

    public void transfer(String fromAcc, String toAcc, double amount) {
        // TODO: validate before modify -> withdraw from fromAcc -> deposit to toAcc
    }

    public void applyMonthlyAdjustments() {
        // TODO: iterate accounts.values() and call acc.applyMonthlyAdjustment()
    }

    public List<Account> getAccountsByCustomer(String customerId) {
        // TODO: return list of accounts belonging to customerId
        return new ArrayList<Account>();
    }

    public List<Account> getAccountsSortedByBalance() {
        // TODO: copy accounts to list -> sort by balance using Comparator -> return
        return new ArrayList<Account>();
    }

    public void saveToFile(String filePath) {
        // TODO: (Lower priority / stretch goal) save accounts and customers to file
    }

    public void loadFromFile(String filePath) {
        // TODO: (Lower priority / stretch goal) load accounts and customers from file
    }
}
