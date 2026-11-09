package bank.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Date;

public class Transaction implements Serializable {
    private static final long serialVersionUID = 1L;

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

    private void readObject(ObjectInputStream input) throws IOException, ClassNotFoundException {
        input.defaultReadObject();
        // Static fields are not saved. Continue after the largest loaded TXN number.
        int savedId;
        try {
            savedId = Integer.parseInt(transactionId.split("-")[1]);
        } catch (NullPointerException | IndexOutOfBoundsException | NumberFormatException e) {
            throw new IOException("Invalid saved transaction ID: " + transactionId, e);
        }
        nextId = Math.max(nextId, savedId + 1);
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
