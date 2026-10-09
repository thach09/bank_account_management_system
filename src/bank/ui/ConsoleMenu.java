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
        // TODO: menu loop (do-while):
        // 1. displayMenu()
        // 2. read user choice with try-catch (InputMismatchException / NumberFormatException)
        // 3. switch-case calling appropriate Bank operations
        // 4. print operation results (no balance calculation in UI)
    }

    public void displayMenu() {
        // TODO: display console menu options (1 - 8 + Exit)
    }
}
