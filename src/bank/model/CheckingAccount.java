package bank.model;

import java.util.List;
import java.util.Scanner;

public class CheckingAccount extends Account {
    private long overdraftLimit;
    private long monthlyFee;

    public CheckingAccount() {
    }

    public CheckingAccount(String accountNumber, String ownerID, long initialDeposit, long overdraftLimit, long monthlyFee) {
        super(accountNumber, ownerID, initialDeposit);
        if (overdraftLimit < 0 || monthlyFee < 0) {
            throw new IllegalArgumentException("Invalid checking account parameters");
        }
        this.overdraftLimit = overdraftLimit;
        this.monthlyFee = monthlyFee;
    }

    // Trả về số dư tối thiểu
    /*
    Example: 
    overdraftLimit: 50.000đ
    ==> Trả về -50.000đ
     */
    @Override
    public long getMinAllowedBalance() {   
        return -overdraftLimit;
    }

    // Tính phí hàng tháng
    /*
    Example: 
    monthlyFee: 50.000đ
    ==> Trả về -50.000đ
     */
    @Override
    public long calculateMonthlyAdjustment() {
        return -monthlyFee;
    }

    // Trả về loại tài khoản
    /*
    Example: CheckingAccount
    ==> Return "Checking"
     */
    @Override
    public String getAccountType() {
        return "Checking";
    }

    @Override
    public void inputInfo(List<Account> accounts, String customerId, int maxSize) {
        super.inputInfo(accounts, customerId, maxSize);
        Scanner scanner = new Scanner(System.in);
        boolean cont = true;
        do {
            try {
                System.out.print("Input Overdraft limit: ");
                overdraftLimit = Long.parseLong(scanner.nextLine().trim());
                if (overdraftLimit < 0) {
                    System.out.println("This field number must be greater than 0.");
                } else cont = false;
            } catch (Exception e) {
                System.out.println("Please input this field again!!!");
            }
        } while (cont);

        do {
            try {
                System.out.print("Input Monthly Fee: ");
                monthlyFee = Long.parseLong(scanner.nextLine().trim());
                if (monthlyFee < 0) {
                    System.out.println("This field number must be greater than 0.");
                } else cont = false;
            } catch (Exception e) {
                System.out.println("Please input this field again!!!");
            }
        } while (cont);
    }
}
