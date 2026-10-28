package bank.model;

public class SavingsAccount extends Account {
    private double interestRate;
    private long minBalance;

    public SavingsAccount(String accountNumber, Customer owner, long initialDeposit, double interestRate, long minBalance) {
        super(accountNumber, owner, initialDeposit);
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
}
