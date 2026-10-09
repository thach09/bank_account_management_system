package bank.model;

public class CheckingAccount extends Account {
    private double overdraftLimit;
    private double monthlyFee;

    public CheckingAccount(String accountNumber, double balance, Customer customer, double overdraftLimit, double monthlyFee) {
        super(accountNumber, balance, customer);
        this.overdraftLimit = overdraftLimit;
        this.monthlyFee = monthlyFee;
    }

    public double getOverdraftLimit() {
        return overdraftLimit;
    }

    public double getMonthlyFee() {
        return monthlyFee;
    }

    @Override
    public void withdraw(double amount) {
        // TODO: validate amount > 0 and balance - amount >= -overdraftLimit
        // -> deduct balance -> record transaction
    }

    @Override
    public void applyMonthlyAdjustment() {
        // TODO: deduct monthlyFee from balance -> record transaction
    }
}
