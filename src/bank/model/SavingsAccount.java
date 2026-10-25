package bank.model;

public class SavingsAccount extends Account {
    private double interestRate;
    private long minBalance;

    public SavingsAccount(String accountNumber, Customer owner, long initialDeposit, double interestRate, long minBalance) {
        super(accountNumber, owner, initialDeposit);
        this.interestRate = interestRate;
        this.minBalance = minBalance;
    }

    /*
    Example: Trong tai khoan khach hang
    So du hien tai: 2.000.000đ
    So du toi thieu: 50.000đ
     */
    @Override
    public long getMinAllowedBalance() {
        // TODO: return minBalance
        return minBalance;
    }

    /*
    Cong Thuc:
    Lãi tháng = Math.round(balance * interestRate / 12)
    Example:
    Balance: 12.000.000đ
    interestRate = 0.06 (6% per year)
     */
    @Override
    public long calculateMonthlyAdjustment() {
        // TODO: calculate monthly interest based on interestRate
        return Math.round(balance * interestRate / 12);
    }

    @Override
    public String getAccountType() {
        // TODO: return account type name
        return "";
    }
}
