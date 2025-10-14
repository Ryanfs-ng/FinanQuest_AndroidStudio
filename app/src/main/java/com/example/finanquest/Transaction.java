package com.example.finanquest;

public class Transaction {
    private String title;
    private String type;
    private String amount;
    private String date;

    public Transaction(String title, String type, String amount, String date) {
        this.title = title;
        this.type = type;
        this.amount = amount;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public String getType() {
        return type;
    }

    public String getAmount() {
        return amount;
    }

    public String getDate() {
        return date;
    }
}
