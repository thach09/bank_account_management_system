package bank.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Customer {
    private String customerId;
    private String name;
    private String phone;
    private String email;
    private List<Account> accounts;

    public Customer() {
        this.accounts = new ArrayList<>();
    }

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
        if (account == null || accounts.contains(account)) {
            return;
        }
        this.accounts.add(account);
    }

    public List<Account> getAccounts() {

        // return accounts;
        return accounts;

    }

    public void inputInfoCustomer(Map<String, Customer> customers) {
        Scanner scanner = new Scanner(System.in);
        boolean cont = true;
        int count = 0;
        if (customers.size() == 0 || customers == null) {
            customerId = String.format("C%03d", ++count);
            System.out.println("Auto generated ID: " + customerId);
        } else {
            customerId = String.format("C%03d", customers.size() + 1);
            System.out.println("Auto generated ID: " + customerId );
        }

        do {
            try {
                System.out.print("Input customer name: ");
                name = scanner.nextLine().trim();
                if (name.equals("")) {
                    System.out.println("This field can not be empty.");
                } else cont = false;
            } catch (Exception e) {
                System.out.println("Please input this field again!!!");
            }
        } while (cont == true);

        do {
            try {
                System.out.print("Input customer phone: ");
                phone = scanner.nextLine().trim();
                if (phone.equals("")) {
                    System.out.println("This field can not be empty.");
                } else cont = false;
            } catch (Exception e) {
                System.out.println("Please input this field again!!!");
            }
        } while (cont);

        do {
            try {
                System.out.print("Input customer email: ");
                email = scanner.nextLine().trim();
                if (email.equals("")) {
                    System.out.println("This field can not be empty.");
                } else cont = false;
            } catch (Exception e) {
                System.out.println("Please input this field again!!!");
            }
        } while (cont);
    }
}
