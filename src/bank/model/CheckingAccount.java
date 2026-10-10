package bank.model;

public class CheckingAccount extends Account {
    private long overdraftLimit;
    private long monthlyFee;

    public CheckingAccount(String accountNumber, Customer owner, long initialDeposit, long overdraftLimit, long monthlyFee) {
        super(accountNumber, owner, initialDeposit);
        this.overdraftLimit = overdraftLimit;
        this.monthlyFee = monthlyFee;
    }

    @Override
    public long getMinAllowedBalance() {
        // TODO: return -overdraftLimit
        return 0;
    }

    @Override
    public long calculateMonthlyAdjustment() {
        // TODO: return monthly fee adjustment
        return 0;
    }

    @Override
    public String getAccountType() {
        // TODO: return account type name
        return "";
    }
}
