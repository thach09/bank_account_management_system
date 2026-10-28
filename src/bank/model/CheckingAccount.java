package bank.model;

public class CheckingAccount extends Account {
    private long overdraftLimit;
    private long monthlyFee;

    public CheckingAccount(String accountNumber, Customer owner, long initialDeposit, long overdraftLimit, long monthlyFee) {
        super(accountNumber, owner, initialDeposit);
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
}
