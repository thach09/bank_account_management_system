package bank.model;

public class SavingsAccount extends Account {
    private double interestRate;

    public SavingsAccount(String accountNumber, double balance, Customer customer, double interestRate) {
        super(accountNumber, balance, customer);
        this.interestRate = interestRate;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    @Override
    public void withdraw(double amount) {
        // TODO: validate amount > 0 and balance - amount >= minBalance (or >= 0)
        // -> deduct balance -> record transaction
    }

    @Override
    public void applyMonthlyAdjustment() {
        // TODO: calculate monthly interest (balance * interestRate / 12)
        // -> add to balance -> record transaction
    }
}
