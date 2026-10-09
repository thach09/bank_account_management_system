package bank.model;

import java.time.LocalDateTime;

public class Transaction {
    private String transactionId;
    private LocalDateTime timestamp;
    private TransactionType type;
    private double amount;
    private double balanceAfter;
    private String description;

    public Transaction(TransactionType type, double amount, double balanceAfter, String description) {
        // TODO: generate unique transactionId, set current timestamp, set fields
        this.type = type;
        this.amount = amount;
        this.balanceAfter = balanceAfter;
        this.description = description;
        this.timestamp = LocalDateTime.now();
    }

    public String getTransactionId() {
        return transactionId;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public TransactionType getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public double getBalanceAfter() {
        return balanceAfter;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        // TODO: format transaction details for console printing
        return "";
    }
}
