package bank.model;

import java.util.Calendar;
import java.util.Date;

public class FixedDepositAccount extends Account {
    private int termMonths;
    private double interestRate;
    private Date maturityDate;
    private double earlyWithdrawalPenaltyRate;

    public FixedDepositAccount(String accountNumber, Customer owner, long initialDeposit, int termMonths, double interestRate, double earlyWithdrawalPenaltyRate) {
        super(accountNumber, owner, initialDeposit);
        if (termMonths <= 0 || !Double.isFinite(interestRate) || interestRate < 0
                || !Double.isFinite(earlyWithdrawalPenaltyRate) || earlyWithdrawalPenaltyRate < 0) {
            throw new IllegalArgumentException("Invalid fixed deposit parameters");
        }
        this.termMonths = termMonths;
        this.interestRate = interestRate;
        this.earlyWithdrawalPenaltyRate = earlyWithdrawalPenaltyRate;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(super.getOpenDate());
        calendar.add(Calendar.MONTH, termMonths);
        this.maturityDate = calendar.getTime();
    }

    @Override
    public void withdraw(long amount, TransactionType type, String note) {
        validateAmount(amount);
        long currentBalance = getBalance();
        if (amount > currentBalance) {
            throw new IllegalArgumentException("Insufficient funds");
        }

        long penalty = 0;
        if (!isMatured()) {
            double rawPenalty = amount * earlyWithdrawalPenaltyRate;
            if (!Double.isFinite(rawPenalty) || rawPenalty >= Long.MAX_VALUE) {
                throw new IllegalArgumentException("Penalty exceeds supported amount");
            }
            penalty = Math.round(rawPenalty);
        }

        // Validate withdrawal AND penalty before changing balance.
        if (penalty > currentBalance - amount) {
            throw new IllegalArgumentException("Insufficient funds for early withdrawal penalty");
        }
        super.withdraw(amount, type, note);
        if (penalty > 0) {
            super.withdraw(penalty, TransactionType.FEE, "Early withdrawal penalty");
        }
    }

    @Override
    public long getMinAllowedBalance() {
        return 0;
    }

    @Override
    public long calculateMonthlyAdjustment() {
        // Quy tac F7 cho demo: chi tinh lai thang khi da dao han.
        if (!isMatured()) {
            return 0;
        }
        return Math.round(getBalance() * interestRate / 12);
    }

    @Override
    public String getAccountType() {
        return "Fixed Deposit";
    }

    public boolean isMatured() {
        return !new Date().before(maturityDate);
    }
}
