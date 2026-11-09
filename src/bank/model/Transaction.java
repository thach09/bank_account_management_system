package bank.model;

import java.util.Date;

public class Transaction {
    private String transactionId;
    private static int nextId = 1;
    private TransactionType type;
    private long amount;
    private Date timestamp;
    private long balanceAfter;
    private String note;

    public Transaction(TransactionType type, long amount, long balanceAfter, String note) {
        this.transactionId = String.format("TXN-%03d-%s", nextId++, type);
        this.type = type;
        this.amount = amount;
        this.balanceAfter = balanceAfter;
        this.note = note;
        this.timestamp = new Date();
    }

    public String getTransactionId() {
        return transactionId;
    }

    public TransactionType getType() {
        return type;
    }

    public long getAmount() {
        return amount;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public long getBalanceAfter() {
        return balanceAfter;
    }

    public String getNote() {
        return note;
    }

    @Override
    public String toString() {
        return String.format(
                "Transaction ID: %s%n" +
                "Transaction Type: %s%n" +
                "Amount: %,d VND%n" +
                "Timestamp: %s%n" +
                "Balance After: %,d VND%n" +
                "Note: %s",
                transactionId,
                type,
                amount,
                timestamp,
                balanceAfter,
                note);
    }
}
