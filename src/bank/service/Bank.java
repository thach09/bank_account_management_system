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

    public void openAccount(String customerId, Account account) {
        // TODO: find customer -> validate account not null -> store in accounts map -> link to customer
    }

    public Account getAccount(String accountNumber) {
        // TODO: find and return account by accountNumber from accounts map
        return accounts.get(accountNumber);
    }

    public void deposit(String accountNumber, double amount) {
        // TODO: find account -> validate amount > 0 -> delegate to account.deposit(amount)
    }

    public void withdraw(String accountNumber, double amount) {
        // TODO: find account -> Bank delegates to account.withdraw(amount); subclass handles its own rule
    }

    public void transfer(String fromAcc, String toAcc, double amount) {
        // TODO: Validate before modify:
        // 1. Validate fromAcc and toAcc exist
        // 2. Validate fromAcc != toAcc
        // 3. Validate amount > 0
        // 4. Validate source can withdraw
        // 5. Execute: source.withdraw(amount) -> target.deposit(amount) -> record history
    }

    public void applyMonthlyAdjustments() {
        // TODO: iterate Account references in accounts.values() and call polymorphic method acc.applyMonthlyAdjustment()
    }

    public List<Account> getAccountsByCustomer(String customerId) {
        // TODO: find customer -> retrieve accounts matching customer's accountNumbers -> return list
        return new ArrayList<Account>();
    }

    public List<Account> getAccountsSortedByBalance() {
        // TODO: copy accounts to List -> sort with Comparator by balance -> return list
        return new ArrayList<Account>();
    }

    public void saveToFile(String filePath) {
        // TODO: (Lower priority / stretch goal) save accounts and customers to file after core works
    }

    public void loadFromFile(String filePath) {
        // TODO: (Lower priority / stretch goal) load accounts and customers from file after core works
    }
}
