package com.company.model;

public class Discount {
    private int number;
    private int discountAmount;

    public Discount(int number, int discountAmount) {
        this.number = number;
        this.discountAmount = discountAmount;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(int discountAmount) {
        this.discountAmount = discountAmount;
    }
}
