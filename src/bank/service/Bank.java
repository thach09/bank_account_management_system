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

        this.bankName = "Our Bank";
        this.accounts = new HashMap<String, Account>();
        this.customers = new HashMap<String, Customer>();
    }

    public void addCustomer(Customer customer) {
        // TODO: register customer in the customers map
        if (customer == null || customers.containsKey(customer.getCustomerId())){
            return;
        }
        customers.put(customer.getCustomerId(), customer);
    }

    public void openAccount(String customerID, Account account) {
        // TODO: find customer by customerID -> store account in accounts -> link to customer
        Customer customer = customers.get(customerID);
        if (customer ==null || account ==null){
            return;
        }
        if (account.getOwner() != customer || accounts.containsKey(account.getAccountNumber())){
            return;
        }
        accounts.put(account.getAccountNumber(), account);
        customer.addAccount(account);
    }

    public Account getAccount(String accountNumber) {
        // TODO: find and return account by accountNumber
        return accounts.get(accountNumber);
    }

    public void deposit(String accountNumber, long amount) {
        // TODO: find account -> validate amount -> delegate deposit
        Account account = getAccount(accountNumber);
        if (account == null || amount <= 0){ {
            return;
        }
        account.deposit(amount, TransactionType.DEPOSIT, "Cash deposit: " + amount);
    }

    public void withdraw(String accountNumber, long amount) {
        // TODO: find account -> delegate withdraw
        Account account = getAccount(accountNumber);
        if (account == null || amount <= 0) {
            return;
        }
        account.withdraw(amount, TransactionType.WITHDRAWAL, "Cash withdrawal: " + amount);
    }

    public void transfer(String fromAcc, String toAcc, long amount) {
        // TODO: validate before modify -> withdraw from fromAcc -> deposit to toAcc
        if (fromAcc == null || toAcc == null || from Acc.equals(toAcc) || amount <= 0) {
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
        // TODO: iterate accounts.values() and call acc.applyMonthlyAdjustment()
        for (Account account : accounts.values()){
            account.applyMonthlyAdjustment();
        }
    }

    public List<Account> getAccountsByCustomer(String customerId) {
        // TODO: return list of accounts belonging to customerId
        Customer customer = customers.get(customerId);
        
        if (customer == null){
            return;
        }
        
        //for (Account account : customer.getCustomer(customerId).getAccount()){
        return new ArrayList<Account>(getCustomer(customerId).getAccounts());
        
        //return new ArrayList<Account>();
    }

    public List<Account> getAccountsSortedByBalance() {
        // TODO: copy accounts to list -> sort by balance using Comparator -> return
        //return new ArrayList<Account>();
        List<Account> sorted = new ArrayList<>(accounts.values());

        sorted.sort(Comparator.comparingLong(Account::getBalance).reversed());
        return sorted;
    }

}
