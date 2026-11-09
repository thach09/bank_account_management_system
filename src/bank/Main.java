
package bank;

import bank.model.Account;
import bank.service.Bank;
import bank.ui.ConsoleMenu;
import java.io.*;
import java.util.Locale;

public class Main {
    public static void main(String[] args) {

        File file = new File("bank_data.txt");

        // 1. Read previous saved data from TXT
        if (file.exists()) {
            System.out.println("---- Previous Saved Accounts ----");

            try (BufferedReader reader =
                    new BufferedReader(new FileReader(file))) {

                String line;

                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }

            } catch (IOException e) {
                System.out.println("Read file failed: " + e.getMessage());
            }

            System.out.println("--------------------------------");
        }

        // 2. Start bank program
        Bank bank = new Bank();
        ConsoleMenu menu = new ConsoleMenu(bank);
        menu.start();

        // 3. Save final balances after exiting menu
        if (bank.getAccounts().isEmpty()) {
            System.out.println("No accounts to save. Old file unchanged.");
            return;
        }

        try (BufferedWriter writer =
                new BufferedWriter(new FileWriter(file))) {

            for (Account account : bank.getAccounts().values()) {

                String result = String.format(
                        Locale.US,
                        "Account No: %s | Type: %s | Balance: %,d VND",
                        account.getAccountNumber(),
                        account.getAccountType(),
                        account.getBalance()
                );

                writer.write(result);
                writer.newLine();
            }

            System.out.println("Account data saved successfully!");
            System.out.println("File: " + file.getAbsolutePath());

        } catch (IOException e) {
            System.out.println("Save file failed: " + e.getMessage());
        }
    }
}
