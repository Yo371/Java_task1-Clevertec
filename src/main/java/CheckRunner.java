
import exceptions.InvalidCardNumberException;
import exceptions.InvalidCardTypeException;
import exceptions.NoSuchProductException;
import exceptions.OrderAreNotCreatedException;
import market.GroceryStore;
import products.Bread;
import products.Candy;
import products.Milk;
import products.Product;

import java.io.File;
import java.util.*;

public class CheckRunner {
    public static void main(String[] args) {
        //создаем ассортимент продуктов
        Set<Product> productTreeSet = new TreeSet<>();
        productTreeSet.add(new Bread(2, "Bread", 1.50, true));
        productTreeSet.add(new Milk(4, "Milk 2.5%", 1.30, false));
        productTreeSet.add(new Milk(1, "Milk 3.2%", 1.80, false));
        productTreeSet.add(new Candy(3, "KitKat", 0.80, true));
        productTreeSet.add(new Candy(5, "Snickers", 2, true));
        //продукты отсортированы по id

        //магазин берет в конструктор ассортимент продуктов
        GroceryStore groceryStore = new GroceryStore("Green Store",
                "+375(29)937-99-92", productTreeSet);


        File file = new File(args[0].trim());

        //если в аргументы передан путь к data файлу, берем данные и печатаем чек в файл
        //иначе берем данные из аргументов и печатаем чек в консоль
        try {

            if (file.isFile())
                groceryStore.addOrderFromFile(file)
                        .processOrder()
                        .printCheckToFile(new File("src/main/resources/receipt.txt"));
            else
                groceryStore.addOrder(args)
                        .processOrder()
                        .printCheckToConsole();

        } catch (NoSuchProductException | InvalidCardTypeException
                | InvalidCardNumberException | OrderAreNotCreatedException e) {
            e.printStackTrace();
        }

        /*groceryStore.addOrder(new String[]{"1-2", "2-3", "3-4", "4-1", "goldcard-123456864111"})
                .processOrder().printCheckToConsole();*/

        /*groceryStore.addOrderFromFile(new File("src/main/resources/data.txt")).processOrder()
                .printCheckToFile(new File("src/main/resources/receipt.txt"));*/
    }
}
