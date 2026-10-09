package bank.model;

import java.util.ArrayList;
import java.util.List;

public class Customer {
    private String customerId;
    private String fullName;
    private String phoneNumber;
    private List<String> accountNumbers;

    public Customer(String customerId, String fullName, String phoneNumber) {
        this.customerId = customerId;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.accountNumbers = new ArrayList<String>();
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<String> getAccountNumbers() {
        return accountNumbers;
    }

    public void addAccountNumber(String accountNumber) {
        // TODO: add accountNumber to accountNumbers list
        this.accountNumbers.add(accountNumber);
    }
}
