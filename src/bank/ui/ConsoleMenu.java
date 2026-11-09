package bank.ui;

import bank.model.Account;
import bank.model.CheckingAccount;
import bank.model.Customer;
import bank.model.FixedDepositAccount;
import bank.model.SavingsAccount;
import bank.model.Transaction;
import bank.model.TransactionType;
import bank.service.Bank;
import java.util.List;
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
                        displayDeposit();
                        break;
                    case 3:
                        System.out.println("---- Withdraw money ----");
                        displayWithdraw();
                        break;
                    case 4:
                        System.out.println("---- Transfer money between two accounts ----");
                        displayTransfer();
                        break;
                    case 5:
                        System.out.println("---- List all accounts by customer ----");
                        displayAccountsByCustomer();
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
        System.out.println("\n1. Open a new account");
        System.out.println("2. Deposit money into an account");
        System.out.println("3. Withdraw money");
        System.out.println("4. Transfer money between two accounts");
        System.out.println("5. List all accounts by customer");
        System.out.println("6. View transactions history for an account");
        System.out.println("7. Apply a monthly interest / fee adjustment for each account category");
        System.out.println("8. Sort Accounts by Balance");
        System.out.println("0. Exit\n");
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
                                } else {
                                    cont = false;
                                }
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
        System.out.println("Create new Account for this "
                + customer.getCustomerId() + " with 3 category accounts.");
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
                        newAccount.inputInfo(customer.getAccounts(), customer.getCustomerId(),
                                bank.getAccounts().size());
                        bank.openAccount(newAccount.getOwnerID(), newAccount);
                        System.out.println("------------------------------");
                        System.out.println(bank.getCustomers().size() + " - bank has number of customers");
                        System.out.println(bank.getAccounts().size() + " - bank has number of accounts");
                        System.out.println("------------------------------");
                        break;
                    case 2:
                        newAccount = new FixedDepositAccount();
                        newAccount.inputInfo(customer.getAccounts(), customer.getCustomerId(),
                                bank.getAccounts().size());
                        bank.openAccount(newAccount.getOwnerID(), newAccount);
                        System.out.println("------------------------------");
                        System.out.println(bank.getCustomers().size() + " - bank has number of customers");
                        System.out.println(bank.getAccounts().size() + " - bank has number of accounts");
                        System.out.println("------------------------------");
                        break;
                    case 3:
                        newAccount = new SavingsAccount();
                        newAccount.inputInfo(customer.getAccounts(), customer.getCustomerId(),
                                bank.getAccounts().size());
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

    private void displayAccountsByCustomer() {
        System.out.print("Enter customer ID: ");
        String customerId = scanner.nextLine().trim();

        List<Account> accounts = bank.getAccountsByCustomer(customerId);

        if (accounts == null || accounts.isEmpty()) {
            System.out.println("No accounts found for customer ID: " + customerId);
        } else {
            System.out.println("Accounts of customer " + customerId + ":");

            for (Account account : accounts) {
                System.out.printf(
                        "Account No: %s | Type: %s | Balance: %,d VND\n",
                        account.getAccountNumber(),
                        account.getAccountType(),
                        account.getBalance());
            }
        }
    }

    // CASE 2: DEPOSIT
    private void displayDeposit() {
        System.out.print("Enter account number: ");
        String accNum = scanner.nextLine().trim();

        // Kiểm tra tài khoản có tồn tại không
        if (bank.getAccount(accNum) == null) {
            System.out.println(">> Account not found!");
            return;
        }

        long amount = 0;

        while (true) {
            try {
                System.out.print("Enter amount to deposit (VND): ");
                amount = Long.parseLong(scanner.nextLine().trim());

                if (amount > 0) {
                    break;
                }

                System.out.println("Amount must be greater than 0.");

            } catch (NumberFormatException e) {
                System.out.println("Invalid number format! Please enter an integer amount.");
            }
        }

        try {
            bank.deposit(accNum, amount);
            System.out.println(">> Deposit successfully! Amount: "
                    + String.format("%,d", amount) + " VND");
            bank.getAccount(accNum).addTransaction(TransactionType.DEPOSIT, amount, "Cash Deposit");

        } catch (Exception e) {
            System.out.println(">> Deposit failed: " + e.getMessage());
        }
    }

    // CASE 3: WITHDRAW
    private void displayWithdraw() {
        System.out.print("Enter account number: ");
        String accNum = scanner.nextLine().trim();

        // Check if account exists
        if (bank.getAccount(accNum) == null) {
            System.out.println(">> Account not found!");
            return;
        }

        long amount = 0;

        while (true) {
            try {
                System.out.print("Enter amount to withdraw (VND): ");
                amount = Long.parseLong(scanner.nextLine().trim());

                if (amount > 0) {
                    break;
                }

                System.out.println("Amount must be greater than 0.");

            } catch (NumberFormatException e) {
                System.out.println(
                        "Invalid number format! Please enter an integer amount.");
            }
        }

        try {
            bank.withdraw(accNum, amount);

            System.out.println(">> Withdrawal successfully! Amount: "
                    + String.format("%,d", amount) + " VND");
            bank.getAccount(accNum).addTransaction(TransactionType.WITHDRAW, amount, "Cash Withdraw");

        } catch (Exception e) {
            System.out.println(">> Withdrawal failed: " + e.getMessage());
        }
    }

    public void displayTransfer() {
        System.out.print("Enter source account number (From account): ");
        String fromAccNum = scanner.nextLine().trim();

        // Check if account exists
        if (bank.getAccount(fromAccNum) == null) {
            System.out.println("Source account (From account) not found!");
            return;
        }

        System.out.print("Enter destination account number (To account): ");
        String toAccNum = scanner.nextLine().trim();

        // Check if account exists
        if (bank.getAccount(toAccNum) == null) {
            System.out.println("Destination account (To account) not found!");
            return;
        }

        if (fromAccNum.equals(toAccNum)) {
            System.out.println("Cannot transfer to the same account!");
        }

        long amount = 0;
        boolean cont = true;

        do {
            try {
                System.out.print("Enter amount to transfer (VND): ");
                amount = Long.parseLong(scanner.nextLine().trim());
                if (amount < 0) {
                    System.out.println("This field number must be greater than 0.");
                } else if (amount > bank.getAccount(fromAccNum).getBalance()) {
                    System.out.println("Insufficient balance!");
                } else
                    cont = false;
            } catch (Exception e) {
                System.out.println("Please input this field again!!!");
            }
        } while (cont == true);

        try {
            bank.transfer(fromAccNum, toAccNum, amount);
            System.out.println("Transfer successfully! Amount: "
                    + String.format("%,d", amount) + " VND");
            bank.getAccount(fromAccNum).addTransaction(TransactionType.TRANSFER_OUT, amount, "Cash Transfer Out");
            bank.getAccount(toAccNum).addTransaction(TransactionType.TRANSFER_IN, amount, "Cash Transfer In");

        } catch (Exception e) {
            System.out.println("Transfer failed: " + e.getMessage());
        }
    }

    public void displayTransactionByAccount() {
        System.out.print("Enter account number: ");
        String accNum = scanner.nextLine().trim();
        if (accNum.equals("")) {
            System.out.println("Input field can not be empty.");
            return;
        }
        Account acc = bank.getAccount(accNum);

        // Check if account exists
        if (acc == null) {
            System.out.println("Account number not found!");
            return;
        }

        List<Transaction> transactions = acc.getTransactions();

        if (acc.getTransactions().isEmpty()) {
            System.out.println("No transaction history available!");
            return;
        }

        System.out.println("\n---- TRANSACTION HISTORY ----");

        for (Transaction transaction : transactions) {
            System.out.println(transaction);
            System.out.println("-----------------------------------------");
        }
    }

}
