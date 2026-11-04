package bank.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class Account {
    private String accountNumber;
    private long balance;
    private Date openDate;
    private Customer owner;
    private List<Transaction> transactions;

    public Account(String accountNumber, Customer owner, long initialDeposit) {
        if (initialDeposit < 0) {
            throw new IllegalArgumentException("Initial deposit cannot be negative");
        }
        this.accountNumber = accountNumber;
        this.owner = owner;
        this.balance = initialDeposit;
        this.openDate = new Date();
        this.transactions = new ArrayList<Transaction>();
    }

    // Nạp tiền vào tài khoản
    /*
    Example: Số dư ban đầu: 1.000.000đ
    Nạp 500.000đ
    Số dư cuối: 1.500.000đ
     */
    public void deposit(long amount, TransactionType type, String note) {
        validateAmount(amount);
        long newBalance;
        try {
            newBalance = Math.addExact(balance, amount);
        } catch (ArithmeticException e) {
            throw new IllegalArgumentException("Balance overflow");
        }
        balance = newBalance;
        addTransaction(type, amount, note);
    }

    // Rút tiền khỏi tài khoản
    /*
    Example: Số dư ban đầu: 1.000.000đ
    Rút 500.000đ
    Số dư cuối: 500.000đ
    Tài khoản sau khi rút phải còn tối thiểu 50.000đ
    */
    public void withdraw(long amount, TransactionType type, String note) {
        validateAmount(amount);
        long newBalance;
        try {
            newBalance = Math.subtractExact(balance, amount);
        } catch (ArithmeticException e) {
            throw new IllegalArgumentException("Balance overflow");
        }
        if (newBalance < getMinAllowedBalance()) {
            throw new IllegalArgumentException("Insufficient funds");
        }
        balance = newBalance;
        addTransaction(type, amount, note);
    }

    public abstract long getMinAllowedBalance();

    public abstract long calculateMonthlyAdjustment();

    public abstract String getAccountType();

    // Cộng lãi hoặc trừ phí vào số dư tài khoản
    public void applyMonthlyAdjustment() {
        long adjustment = calculateMonthlyAdjustment();
        if (adjustment == 0) {
            return;
        }
        long newBalance;
        try {
            newBalance = Math.addExact(balance, adjustment);
        } catch (ArithmeticException e) {
            throw new IllegalArgumentException("Balance overflow");
        }
        if (newBalance < getMinAllowedBalance()) {
            throw new IllegalArgumentException("Monthly fee exceeds allowed balance");
        }
        balance = newBalance;
        if (adjustment > 0) {
            addTransaction(TransactionType.INTEREST, adjustment, "Monthly interest");
        } else {
            addTransaction(TransactionType.FEE, -adjustment, "Monthly fee");
        }
    }

    // Check số tiền giao dịch có hợp lệ hay không
    // Neu amount <= 0, method nem IllegalArgumentException; khong tra ve true/false.
    protected void validateAmount(long amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Invalid amount");
        }
    }

    // Ghi nhận lịch sử giao dịch
    /*
    Example: Khách có 1.000.000đ và nạp thêm 500.000đ
    Sau giao dịch, hệ thống sẽ lưu
    Type: DEPOSIT
    Amount: 500.000d
    balanceAfter: 1.500.000đ
    Note: "Cash deposit"
    */
    protected void addTransaction(TransactionType type, long amount, String note) {
        Transaction transaction = new Transaction(type, amount, getBalance(), note);
        transactions.add(transaction);
    }



    public long getBalance() {
        return balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public Customer getOwner() {
        return owner;
    }

    public Date getOpenDate() {
        return openDate;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }
}
