package bank.ui;

import bank.service.Bank;

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
                System.out.println("Input your choice: ");
                choice = Integer.parseInt(scanner.nextLine());
                if (choice < 0 || choice > 8) {
                    System.out.println("Choice must be in range [1-8]");
                    System.out.println("------------------------------");
                }
                con = false;
                switch (choice) {
                    case 1:
                        System.out.println("---- Open a new account ----");
                        bank.addCustomer(null);
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
                        //View transactions history
                        break;
                    case 7:
                        System.out.println("---- Apply a monthly interest / fee adjustment for each account category ----");
                        bank.applyMonthlyAdjustments();
                        break;
                    case 8:
                        System.out.println("Program Exit.");
                        break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Please input a number in this field.");
            }
        } while (con == true || choice != 8);

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
        System.out.println("8. Exit");
    }
}
