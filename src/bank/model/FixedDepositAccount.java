package bank.model;

import java.util.Date;

public class FixedDepositAccount extends Account {
    private int termMonths;
    private double interestRate;
    private Date maturityDate;
    private double earlyWithdrawalPenaltyRate;

    public FixedDepositAccount(String accountNumber, Customer owner, long initialDeposit, int termMonths, double interestRate, double earlyWithdrawalPenaltyRate) {
        super(accountNumber, owner, initialDeposit);
        this.termMonths = termMonths;
        this.interestRate = interestRate;
        this.earlyWithdrawalPenaltyRate = earlyWithdrawalPenaltyRate;
        this.maturityDate = new Date();
    }

    @Override
    public void withdraw(long amount) {
        // TODO: check isMatured() and handle withdrawal / penalty logic
    }

    @Override
    public long getMinAllowedBalance() {
        // TODO: return minimum allowed balance
        return 0;
    }

    @Override
    public long calculateMonthlyAdjustment() {
        // TODO: calculate interest or adjustment for fixed deposit
        return 0;
    }

    @Override
    public String getAccountType() {
        // TODO: return account type name
        return "";
    }

    public boolean isMatured() {
        // TODO: check if current date has reached maturityDate
        return false;
    }
}
