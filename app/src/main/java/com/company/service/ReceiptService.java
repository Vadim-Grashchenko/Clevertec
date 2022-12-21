package com.company.service;

public interface ReceiptService {
    int getId(String arg);
    int getQuantity(String arg);
    double accountForPosition(String arg);
    double calculateAccount(String[] args);
    StringBuilder printReceipt(String[] args);
    void writingToFile(StringBuilder result);
    String checkingDiscount(String[] args);
    int getValueDiscount(String discount);
    String[] getProducts(String[] args);
}
