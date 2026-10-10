package bank.model;

public class SavingsAccount extends Account {
    private double interestRate;
    private long minBalance;

    public SavingsAccount(String accountNumber, Customer owner, long initialDeposit, double interestRate, long minBalance) {
        super(accountNumber, owner, initialDeposit);
        this.interestRate = interestRate;
        this.minBalance = minBalance;
    }

    @Override
    public long getMinAllowedBalance() {
        // TODO: return minBalance
        return 0;
    }

    @Override
    public long calculateMonthlyAdjustment() {
        // TODO: calculate monthly interest based on interestRate
        return 0;
    }

    @Override
    public String getAccountType() {
        // TODO: return account type name
        return "";
    }
}
