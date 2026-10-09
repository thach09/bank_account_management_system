package bank;

import bank.service.Bank;
import bank.ui.ConsoleMenu;

public class Main {
    public static void main(String[] args) {
        // TODO: initialize Bank
        Bank bank = new Bank();

        // TODO: seed minimal demo data if needed (customers, accounts)

        // TODO: initialize and start ConsoleMenu
        ConsoleMenu menu = new ConsoleMenu(bank);
        menu.start();
    }
}
