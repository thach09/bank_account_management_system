package bank.model;

import java.util.Date;

public class Transaction {
    private String transactionId;
    private static int nextId;
    private TransactionType type;
    private long amount;
    private Date timestamp;
    private long balanceAfter;
    private String note;

    public Transaction(TransactionType type, long amount, long balanceAfter, String note) {
        // TODO: generate transactionId using nextId
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
        // TODO: format transaction details
        return "";
    }
}
