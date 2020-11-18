package utils;

import market.GroceryStore;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class Printer {
    public static void printReceiptToConsole(GroceryStore groceryStore, StringBuilder infoFromOrderSb) {
        System.out.println(getReadyReceipt(groceryStore, infoFromOrderSb));
    }

    public static void printReceiptToFile(File file, GroceryStore groceryStore, StringBuilder infoSb) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))) {

            bufferedWriter.write(getReadyReceipt(groceryStore, infoSb));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getReadyReceipt(GroceryStore groceryStore, StringBuilder infoFromOrderSb) {
        StringBuilder readyReceipt = new StringBuilder();
        readyReceipt.append("========================================\n\n");
        readyReceipt.append(String.format("%25s", groceryStore.getName())).append("\n");
        readyReceipt.append(String.format("%28s", groceryStore.getPhoneNumber())).append("\n\n");
        readyReceipt.append(getCurrentTimeAsString()).append("\n");
        readyReceipt.append("========================================\n");
        readyReceipt.append(String.format("%-5s %-23s %10s\n", "ID", "DESCRIPTION", "PRICE"));
        readyReceipt.append(infoFromOrderSb);

        return readyReceipt.toString();
    }

    private static String getCurrentTimeAsString() {
        DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("uuuu-MM-dd");
        DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("HH:mm:ss");
        return "TIME  " + ZonedDateTime.now().format(formatterTime)
                + "\t\tDATE  " + ZonedDateTime.now().format(formatterDate);
    }
}
