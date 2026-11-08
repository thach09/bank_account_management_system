package bank.model;

import java.util.List;
import java.util.Scanner;

public class SavingsAccount extends Account {
    private double interestRate;
    private long minBalance;

    public SavingsAccount() {
    }

    public SavingsAccount(String accountNumber, String ownerID, long initialDeposit, double interestRate, long minBalance) {
        super(accountNumber, ownerID, initialDeposit);
        if (!Double.isFinite(interestRate) || interestRate < 0 || minBalance < 0) {
            throw new IllegalArgumentException("Invalid savings account parameters");
        }
        this.interestRate = interestRate;
        this.minBalance = minBalance;
    }

    // Trả về số dư tối thiểu bắt buộc trong tài khoản
    /*
    Example: Trong tài khoản của customer:
        Số dư hiện tại: 2.000.000đ
        Số dư tối thiểu: 50.000đ
        ==> Không được rút > 1.950.000đ
     */
    @Override
    public long getMinAllowedBalance() {
        return minBalance;
    }
    
    // Tính tổng lãi nhận được hàng tháng
    /*
    Công Thức:
    Lãi tháng = Math.round(balance * interestRate / 12)
    Example:
    Balance: 12.000.000đ
    interestRate = 0.06 (6% per year)
     */
    @Override
    public long calculateMonthlyAdjustment() {
        return Math.round(getBalance() * interestRate / 12);
    }
    
    // Trả về loại tài khoản
    /*
    Example: SavingsAccount
    ==> Return "Savings"
     */
    @Override
    public String getAccountType() {
        return "Savings";
    }

    @Override
    public void inputInfo(List<Account> accounts, String customerId, int maxSize) {
        super.inputInfo(accounts, customerId, maxSize);
        Scanner scanner = new Scanner(System.in);
        boolean cont = true;
        do {
            try {
                System.out.print("Input Interest Rate: ");
                interestRate = Long.parseLong(scanner.nextLine().trim());
                if (interestRate < 0) {
                    System.out.println("This field number must be greater than 0.");
                } else cont = false;
            } catch (Exception e) {
                System.out.println("Please input this field again!!!");
            }
        } while (cont);

        do {
            try {
                System.out.print("Input Minimum Balance: ");
                minBalance = Long.parseLong(scanner.nextLine().trim());
                if (minBalance < 0) {
                    System.out.println("This field number must be greater than 0.");
                } else cont = false;
            } catch (Exception e) {
                System.out.println("Please input this field again!!!");
            }
        } while (cont);
    }
}
