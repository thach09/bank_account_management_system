package bank.model;

public class FixedDepositAccount extends Account {
    private int termMonths;
    private int monthsElapsed;
    private double interestRate;

    public FixedDepositAccount(String accountNumber, double balance, Customer customer, int termMonths, double interestRate) {
        super(accountNumber, balance, customer);
        this.termMonths = termMonths;
        this.interestRate = interestRate;
        this.monthsElapsed = 0;
    }

    public boolean isMatured() {
        return monthsElapsed >= termMonths;
    }

    @Override
    public void withdraw(double amount) {
        // TODO: check isMatured()
        // If matured -> allow withdraw and deduct balance
        // If not matured -> enforce early withdrawal rule according to UML contract
    }

    @Override
    public void applyMonthlyAdjustment() {
        // TODO: increment monthsElapsed++
        // If matured -> calculate and apply interest according to UML contract
    }
}
