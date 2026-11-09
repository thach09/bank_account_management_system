package bank;

import bank.model.Account;
import bank.model.Customer;
import bank.service.Bank;
import bank.ui.ConsoleMenu;
import java.io.*;
import java.util.Locale;

public class Main {
    public static void main(String[] args) {

        File accountFile = new File("bank_data.txt");
        File customerFile = new File("customers.txt");
        File stateFile = new File("bank_state.dat");

        Bank bank;
        if (stateFile.exists()) {
            try (ObjectInputStream input =
                    new ObjectInputStream(new FileInputStream(stateFile))) {
                Object savedState = input.readObject();
                if (!(savedState instanceof Bank)) {
                    throw new IOException("The state file does not contain bank data.");
                }
                bank = (Bank) savedState;
                System.out.println("Bank data loaded successfully!");
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Load bank data failed: " + e.getMessage());
                System.out.println("Program stopped. Existing data files have not been overwritten.");
                return;
            }
        } else {
            bank = new Bank();
            System.out.println("No saved bank data found. A new bank was created.");
        }

        // READ PREVIOUS CUSTOMER DATA
        if (customerFile.exists()) {
            System.out.println("---- Previous Saved Customers ----");

            try (BufferedReader reader =
                    new BufferedReader(new FileReader(customerFile))) {

                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }

            } catch (IOException e) {
                System.out.println("Read customers failed: " + e.getMessage());
            }

            System.out.println("--------------------------------");
        }

        // READ PREVIOUS ACCOUNT DATA
        if (accountFile.exists()) {
            System.out.println("---- Previous Saved Accounts ----");

            try (BufferedReader reader =
                    new BufferedReader(new FileReader(accountFile))) {

                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }

            } catch (IOException e) {
                System.out.println("Read accounts failed: " + e.getMessage());
            }

            System.out.println("--------------------------------");
        }

        // START BANK PROGRAM
        ConsoleMenu menu = new ConsoleMenu(bank);
        menu.start();

        // SAVE THE COMPLETE BANK AFTER 0. EXIT
        try (ObjectOutputStream output =
                new ObjectOutputStream(new FileOutputStream(stateFile))) {
            output.writeObject(bank);
            System.out.println("Bank data saved successfully!");
        } catch (IOException e) {
            System.out.println("Save bank data failed: " + e.getMessage());
        }

        // SAVE CUSTOMER INFORMATION
        if (!bank.getCustomers().isEmpty()) {
            try (BufferedWriter writer =
                    new BufferedWriter(new FileWriter(customerFile))) {

                for (Customer customer : bank.getCustomers().values()) {
                    String result =
                            "Customer ID: " + customer.getCustomerId()
                            + " | Name: " + customer.getName()
                            + " | Phone: " + customer.getPhone()
                            + " | Email: " + customer.getEmail();

                    writer.write(result);
                    writer.newLine();
                }

                System.out.println("Customer data saved successfully!");

            } catch (IOException e) {
                System.out.println("Save customers failed: " + e.getMessage());
            }
        }

        // SAVE FINAL ACCOUNT BALANCES
        if (!bank.getAccounts().isEmpty()) {
            try (BufferedWriter writer =
                    new BufferedWriter(new FileWriter(accountFile))) {

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

            } catch (IOException e) {
                System.out.println("Save accounts failed: " + e.getMessage());
            }
        }
    }
}
