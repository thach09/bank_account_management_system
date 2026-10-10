package bank.model;

import java.util.ArrayList;
import java.util.List;

public class Customer {
    private String customerId;
    private String name;
    private String phone;
    private String email;
    private List<Account> accounts;

    public Customer(String customerId, String name, String phone, String email) {
        this.customerId = customerId;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.accounts = new ArrayList<Account>();
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void addAccount(Account account) {
        // TODO: add account to customer's account list
        this.accounts.add(account);
    }

    public List<Account> getAccounts() {
        return accounts;
    }
}
