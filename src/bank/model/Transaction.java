package bank.model;

import java.util.Date;

public class Transaction {
    private String transactionId;
    private TransactionType type;
    private int amount;
    private Date timestamp;
    private int balanceAfter;
    private String note;

    public Transaction(TransactionType type, int amount, int balanceAfter, String note) {
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

    public int getAmount() {
        return amount;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public int getBalanceAfter() {
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
