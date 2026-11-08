package bank.ui;

import bank.model.Account;
import bank.model.CheckingAccount;
import bank.model.Customer;
import bank.model.FixedDepositAccount;
import bank.model.SavingsAccount;
import bank.service.Bank;

import java.util.Map;
import java.util.Scanner;

public class ConsoleMenu {
    private Bank bank;
    private Scanner scanner;

    public ConsoleMenu(Bank bank) {
        this.bank = bank;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        int choice = 0;
        boolean con = true;
        do {
            displayMenu();
            try {
                System.out.print("Input your choice: ");
                choice = Integer.parseInt(scanner.nextLine());
                if (choice < 0 || choice > 8) {
                    System.out.println("Choice must be in range [1-8]");
                    System.out.println("------------------------------");
                }
                con = false;
                switch (choice) {
                    case 1:
                        displayOpenAccount();
                        break;
                    case 2:
                        System.out.println("---- Deposit money into an account ---- ");
                        bank.deposit(null, 0);
                        break;
                    case 3:
                        System.out.println("---- Withdraw money ----");
                        bank.withdraw(null, 0);
                        break;
                    case 4:
                        System.out.println("---- Transfer money between two accounts ----");
                        bank.transfer(null, null, 0);
                        break;
                    case 5:
                        System.out.println("---- List all accounts by customer ----");
                        bank.getAccountsByCustomer(null);
                        break;
                    case 6:
                        System.out.println("---- View transactions history for an account ----");
                        // View transactions history
                        break;
                    case 7:
                        System.out.println(
                                "---- Apply a monthly interest / fee adjustment for each account category ----");
                        bank.applyMonthlyAdjustments();
                        break;
                    case 8:
                        System.out.println("---- Sort Accounts by Balance ----");
                        bank.getAccountsSortedByBalance();
                        break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Please input a number in this field.");
            }
        } while (con == true || choice != 0);

        // TODO: menu loop (do-while):
        // 1. displayMenu()
        // 2. read user choice with try-catch (InputMismatchException /
        // NumberFormatException)
        // 3. switch-case calling appropriate Bank operations
        // 4. print operation results (no balance calculation in UI)
    }

    public void displayMenu() {
        System.out.println("1. Open a new account");
        System.out.println("2. Deposit money into an account");
        System.out.println("3. Withdraw money");
        System.out.println("4. Transfer money between two accounts");
        System.out.println("5. List all accounts by customer");
        System.out.println("6. View transactions history for an account");
        System.out.println("7. Apply a monthly interest / fee adjustment for each account category");
        System.out.println("8. Sort Accounts by Balance");
        System.out.println("0. Exit");
    }

    public void displayOpenAccount() {
        int choice = 0;
        Map<String, Customer> customers = this.bank.getCustomers();
        String customerId = "";

        do {
            try {
                System.out.println("---- Open a new account ----");
                System.out.println("1. New customer");
                System.out.println("2. Existed customer");
                System.out.println("0. Exit");
                System.out.print("Choose an option: ");
                choice = Integer.parseInt(scanner.nextLine());
                if (choice < 0 || choice > 2) {
                    System.out.println("You must input choice (1 or 2) to proccess.");
                }
                System.out.println("------------------------------");
                switch (choice) {
                    case 1:
                        System.out.println("---- New Customer ----");
                        Customer newCustomer = new Customer();
                        newCustomer.inputInfoCustomer(customers);
                        bank.addCustomer(newCustomer);
                        System.out.println("Create a new Customer successfully!");
                        System.out.println("------------------------------");
                        displayCreateAccountByType(newCustomer);
                        break;
                    case 2:
                        System.out.println("---- Existed Customer ----");
                        boolean cont = true;
                        do {
                            try {
                                System.out.print("Input Customer ID: ");
                                customerId = scanner.nextLine().trim();
                                if (customerId.equals("")) {
                                    System.out.println("This field can not be empty.");
                                } else
                                    cont = false;
                            } catch (Exception e) {
                                System.out.println("Please input this field again!!!");
                            }
                        } while (cont);
                        Customer existedCustomer = bank.getCustomer(customerId);
                        if (existedCustomer == null) {
                            System.out.println("Customer with this ID(" + customerId + ") is not found.");
                            System.out.println("------------------------------");
                        } else {
                            displayCreateAccountByType(existedCustomer);
                        }
                        break;
                    case 0:
                        System.out.println("Exit Open Account.");
                        System.out.println("------------------------------");
                        break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Please input a number in this field.");
            }
        } while (choice != 0);

    }

    public void displayCreateAccountByType(Customer customer) {
        System.out.println("Create new Account for this " +
                customer.getCustomerId() + " with 3 category accounts.");
        int choice = 0;
        Account newAccount;
        do {
            try {
                System.out.println("1. Checking Account");
                System.out.println("2. Fixed Deposit Account");
                System.out.println("3. Savings Account");
                System.out.println("0. Exit");
                System.out.print("Choose an option: ");
                choice = Integer.parseInt(scanner.nextLine().trim());
                if (choice < 0 || choice > 3) {
                    System.out.println("You must input choice (1, 2 or 3) to proccess.");
                }
                switch (choice) {
                    case 1:
                        newAccount = new CheckingAccount();
                        newAccount.inputInfo(customer.getAccounts(), customer.getCustomerId(), bank.getAccounts().size());
                        bank.openAccount(newAccount.getOwnerID(), newAccount);
                        System.out.println("------------------------------");
                        System.out.println(bank.getCustomers().size() + " - bank has number of customers");
                        System.out.println(bank.getAccounts().size() + " - bank has number of accounts");
                        System.out.println("------------------------------");
                        break;
                    case 2:
                        newAccount = new FixedDepositAccount();
                        newAccount.inputInfo(customer.getAccounts(), customer.getCustomerId(), bank.getAccounts().size());
                        bank.openAccount(newAccount.getOwnerID(), newAccount);
                        System.out.println("------------------------------");
                        System.out.println(bank.getCustomers().size() + " - bank has number of customers");
                        System.out.println(bank.getAccounts().size() + " - bank has number of accounts");
                        System.out.println("------------------------------");
                        break;
                    case 3:
                        newAccount = new SavingsAccount();
                        newAccount.inputInfo(customer.getAccounts(), customer.getCustomerId(), bank.getAccounts().size());
                        bank.openAccount(newAccount.getOwnerID(), newAccount);
                        System.out.println(bank.getCustomers().size() + " - bank has number of customers");
                        System.out.println(bank.getAccounts().size() + " - bank has number of accounts");
                        System.out.println("------------------------------");
                        break;
                    case 0:
                        System.out.println("Exit Create Customer.");
                        System.out.println("------------------------------");
                        break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Please input a number in this field.");
            }
        } while (choice != 0);
    }
}
