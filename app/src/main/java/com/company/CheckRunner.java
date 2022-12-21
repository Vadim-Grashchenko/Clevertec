package com.company;

import com.company.model.Discount;
import com.company.model.Product;
import com.company.service.ReceiptServiceImpl;

import java.util.*;

public class CheckRunner {

    public final static List<Product> products = new ArrayList<>(Arrays.asList(
            new Product(0, "water", 2),
            new Product(1, "milk", 2.5),
            new Product(2, "meat", 10.3),
            new Product(3, "cheese", 3),
            new Product(4, "bollocks", 3)
    ));

    public final static List<Discount> discounts = new ArrayList<>(Arrays.asList(
            new Discount(1111, 10),
            new Discount(1112, 20),
            new Discount(1113, 30),
            new Discount(1114, 40)
    ));
    
    public static void main(String[] args) {

        ReceiptServiceImpl receiptService = new ReceiptServiceImpl();
        System.out.println(receiptService.printReceipt(args));
        receiptService.writingToFile(receiptService.printReceipt(args));

    }
}
