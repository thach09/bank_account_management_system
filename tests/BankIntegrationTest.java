import bank.model.*;
import bank.service.Bank;
import bank.ui.ConsoleMenu;
import java.io.*;
import java.util.List;

public class BankIntegrationTest {
    private static int passed = 0;

    private static void check(boolean condition, String label) {
        if (!condition) throw new AssertionError("FAIL: " + label);
        passed++;
        System.out.println("PASS: " + label);
    }

    private static String capture(Runnable action) {
        PrintStream old = System.out;
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        try {
            System.setOut(new PrintStream(bytes));
            action.run();
            return bytes.toString();
        } finally {
            System.setOut(old);
        }
    }

    public static void main(String[] args) throws Exception {
        Bank bank = new Bank();
        Customer c1 = new Customer("C001", "Demo One", "0000000000", "one@example.com");
        Customer c2 = new Customer("C002", "Demo Two", "0000000001", "two@example.com");
        bank.addCustomer(c1);
        bank.addCustomer(c2);
        check(bank.getCustomers().size() == 2, "Case 1: create customers");

        CheckingAccount a = new CheckingAccount("ACC001", "C001", 500000L, 100000L, 20000L);
        SavingsAccount b = new SavingsAccount("ACC002", "C002", 12000000L, 0.06, 50000L);
        FixedDepositAccount f = new FixedDepositAccount("ACC003", "C001", 1000000L, 12, 0.06, 0.02);
        bank.openAccount("C001", a);
        bank.openAccount("C002", b);
        bank.openAccount("C001", f);
        check(bank.getAccounts().size() == 3, "Case 1: open three account types");
        check(bank.getAccountsByCustomer("C001").size() == 2, "Case 5: customer accounts");

        bank.deposit("ACC001", 200000L);
        check(a.getBalance() == 700000L && a.getTransactions().size() == 1, "Case 2: deposit with single transaction");

        bank.withdraw("ACC001", 100000L);
        check(a.getBalance() == 600000L && a.getTransactions().size() == 2, "Case 3: withdraw with single transaction");

        bank.transfer("ACC001", "ACC002", 300000L);
        check(a.getBalance() == 300000L && b.getBalance() == 12300000L, "Case 4: transfer both balances");
        check(a.getTransactions().size() == 3 && b.getTransactions().size() == 1,
                "Case 4: transfer creates one history entry per account");

        bank.transfer("ACC001", "ACC001", 10000L);
        check(a.getBalance() == 300000L && a.getTransactions().size() == 3,
                "Case 4: self-transfer rejected");

        boolean rejected = false;
        try { bank.withdraw("ACC001", 500000L); }
        catch (IllegalArgumentException ex) { rejected = true; }
        check(rejected && a.getBalance() == 300000L, "Case 3: checking overdraft limit");

        String history = capture(new Runnable() {
            @Override public void run() {
                InputStream oldIn = System.in;
                try {
                    System.setIn(new ByteArrayInputStream("6\nACC001\n0\n".getBytes()));
                    new ConsoleMenu(bank).start();
                } finally {
                    System.setIn(oldIn);
                }
            }
        });
        check(history.contains("TRANSACTION HISTORY") && history.contains("TRANSFER_OUT")
                && history.contains("Transaction ID"), "Case 6: history wired into menu");

        check(b.calculateMonthlyAdjustment() == 61500L, "Case 7: savings monthly interest");
        long prevB = b.getBalance();
        bank.applyMonthlyAdjustments();
        check(a.getBalance() == 280000L && b.getBalance() == prevB + 61500L,
                "Case 7: checking fee and savings interest");

        long beforeF = f.getBalance();
        f.withdraw(100000L, TransactionType.WITHDRAW, "Early exit");
        check(f.getBalance() == beforeF - 102000L && f.getTransactions().size() == 2,
                "Fixed deposit: early withdrawal penalty");

        String sortedOutput = capture(new Runnable() {
            @Override public void run() { bank.getAccountsSortedByBalance(); }
        });
        int i2 = sortedOutput.indexOf("ACC002");
        int i3 = sortedOutput.indexOf("ACC003");
        int i1 = sortedOutput.indexOf("ACC001");
        check(i2 >= 0 && i3 > i2 && i1 > i3, "Case 8: printed accounts sorted by balance");

        List<Transaction> transactions = a.getTransactions();
        check(!transactions.get(0).getTransactionId().equals(transactions.get(1).getTransactionId()),
                "Transaction IDs are unique");
        check(transactions.get(0).toString().contains("Balance After"),
                "Transaction details are printable");
        System.out.println("TOTAL PASS: " + passed);
    }
}
