package com.company.service;

import com.company.model.Discount;
import com.company.model.Product;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import static com.company.CheckRunner.discounts;
import static com.company.CheckRunner.products;

public class ReceiptServiceImpl implements ReceiptService {

    @Override
    public int getId(String args) {
        String[] idParameter = args.split("-");
        return Integer.parseInt(idParameter[0]);
    }

    @Override
    public int getQuantity(String args) {
        String[] quantityParameter = args.split("-");
        return Integer.parseInt(quantityParameter[1]);
    }

    @Override
    public double calculateAccount(String[] args) {
        double account = 0;
        int valueDiscount = 0;
        if(checkingDiscount(args) != null){
            valueDiscount = getValueDiscount(checkingDiscount(args));
        }
        args = getProducts(args);
        for (String arg : args) {
            account += accountForPosition(arg);
        }
        return account - account * valueDiscount / 100;
    }

    @Override
    public double accountForPosition(String arg) {
        Product productId = null;
        double accountForPosition = 0;
        try {
            productId = products.stream().
                    filter(i -> i.getId() == getId(arg)).
                    findFirst().get();
            accountForPosition = productId.getPrice() * getQuantity(arg);
        } catch(NoSuchElementException e) {
            System.out.println("The product with does not exist");
        }
        return accountForPosition;
    }

    @Override
    public StringBuilder printReceipt(String[] args){
        StringBuilder positions = new StringBuilder();
        String[] newArgs = getProducts(args);
        for (String arg : newArgs) {
            try {
                positions.append(products.get(getId(arg)).getName()).append(" * ").append(getQuantity(arg)).append(" = ").
                        append(String.format("%.2f", accountForPosition(arg))).append('\n');
            } catch (IndexOutOfBoundsException e) {
                System.out.println(" ");
            }
        }
        System.out.println();

        return positions.append("ACCOUNT      ").append(String.format("%.2f",calculateAccount(args))).append("\n\n").
                append("DISCOUNT      ").append(getValueDiscount(checkingDiscount(args))).append("%");
    }

    @Override
    public void writingToFile(StringBuilder result) {
        try(FileWriter writer = new FileWriter("receipts.txt", true))
        {
            writer.write(String.valueOf(result) + "\n\n");
            writer.flush();
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public String checkingDiscount(String[] args) {
        String discount = "";
        List<String> checkingDiscount = Arrays.asList(args);
        
        for (int i = 0; i < checkingDiscount.size(); i++) {
            if(checkingDiscount.get(i).contains("card")) {
                discount = checkingDiscount.get(i);
            } else {
                discount = null;
            }
        }
        return discount;
    }

    @Override
    public int getValueDiscount(String discount) {
        String[] numberDiscount = discount.split("-");
        int valueDiscount = 0;
        try {
            Discount discountFinded = discounts.stream().
                    filter(i -> i.getNumber() == Integer.parseInt(numberDiscount[1])).
                    findFirst().get();
            valueDiscount = discountFinded.getDiscountAmount();
        } catch (NoSuchElementException e) {
            System.out.println("The card is invalid");
        }
        return valueDiscount;
    }

    @Override
    public String[] getProducts(String[] args) {
        if(checkingDiscount(args) != null) {
            return Arrays.copyOf(args, args.length - 1);
        } else {
            return args;
        }
    }
}
