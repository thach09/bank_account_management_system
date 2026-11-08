package bank.service;

import bank.model.Account;
import bank.model.Customer;
import bank.model.TransactionType;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Bank {
    private String bankName;
    private Map<String, Account> accounts;
    private Map<String, Customer> customers;

    public Bank() {

        this.bankName = "Our Bank";
        this.accounts = new HashMap<String, Account>();
        this.customers = new HashMap<String, Customer>();
    }

    public Map<String, Customer> getCustomers() {
        return customers;
    }

    public Map<String, Account> getAccounts() {
        return accounts;
    }

    public void addCustomer(Customer customer) {
        
        if (customer == null || customers.containsKey(customer.getCustomerId())){
            return;
        }
        customers.put(customer.getCustomerId(), customer);
    }

    public void openAccount(String customerID, Account account) {
        
        Customer customer = customers.get(customerID);
        if (customer == null || account == null){
            return;
        }
        if (account.getOwnerID() != customerID || accounts.containsKey(account.getAccountNumber())){
            return;
        }
        accounts.put(account.getAccountNumber(), account);
        customer.addAccount(account);
    }

    public Customer getCustomer(String customerId) {
        return customers.get(customerId);
    }

    public Account getAccount(String accountNumber) {
        
        return accounts.get(accountNumber);
    }

    public void deposit(String accountNumber, long amount) {
        
        Account account = getAccount(accountNumber);
        if (account == null || amount <= 0) {
            return;
        }
        account.deposit(amount, TransactionType.DEPOSIT, "Cash deposit: " + amount);
    }

    public void withdraw(String accountNumber, long amount) {
       
        Account account = getAccount(accountNumber);
        if (account == null || amount <= 0) {
            return;
        }
        account.withdraw(amount, TransactionType.WITHDRAW, "Cash withdrawal: " + amount);
    }

    public void transfer(String fromAcc, String toAcc, long amount) {
        
        if (fromAcc == null || toAcc == null || fromAcc.equals(toAcc) || amount <= 0) {
            return;
        }
        Account fromAccount = getAccount(fromAcc);
        Account toAccount = getAccount(toAcc);
        if (fromAccount == null || toAccount == null){
            return;

        }

        fromAccount.withdraw(amount, TransactionType.TRANSFER_OUT, "Transfer to account: " + toAcc);
        toAccount.deposit(amount, TransactionType.TRANSFER_IN, "Transfer from account: " + fromAcc);
    }

    public void applyMonthlyAdjustments() {
        
        for (Account account : accounts.values()){
            account.applyMonthlyAdjustment();
        }
    }

    public List<Account> getAccountsByCustomer(String customerId) {
        
        Customer customer = customers.get(customerId);
        
        if (customer == null){
            return null;
        }
        
        //for (Account account : customer.getCustomer(customerId).getAccount()){
        return new ArrayList<Account>(customer.getAccounts());
        
        //return new ArrayList<Account>();
    }

    public List<Account> getAccountsSortedByBalance() {
        
        //return new ArrayList<Account>();
        List<Account> sorted = new ArrayList<>(accounts.values());

        sorted.sort(Comparator.comparingLong(Account::getBalance).reversed());
        return sorted;
    }
}
