package market;

import cards.DiscountCard;
import exceptions.*;
import products.Product;
import utils.Loader;
import utils.Printer;

import java.io.*;
import java.util.*;

public class GroceryStore {
    private final String name;
    private final String phoneNumber;
    private Set<Product> assortmentSet;
    private Map<Product, Integer> orderMap;
    private DiscountCard card;
    private StringBuilder infoForReceiptFormedFromOrder;
    private double totalCost;

    public GroceryStore(String name, String phoneNumber, Set<Product> assortmentSet) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.assortmentSet = assortmentSet;
        totalCost = 0;
    }

    public Map<Product, Integer> getOrderMap() {
        return orderMap;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public Set<Product> getAssortmentSet() {
        return assortmentSet;
    }

    public DiscountCard getCard() {
        return card;
    }

    public void setAssortmentSet(Set<Product> assortmentSet) {
        this.assortmentSet = assortmentSet;
    }

    public void setCard(DiscountCard card) {
        this.card = card;
    }

    public GroceryStore addOrder(String[] args) throws NoSuchProductException,
            InvalidCardNumberException, InvalidCardTypeException {
        orderMap = new HashMap<>();
        String[] splittedArgs;
        Product foundProduct;

        //добовляем в map продукты и их кол-во, key - product, value - кол-во
        for (String arg : args) {
            splittedArgs = arg.split("-");
            if (!arg.toLowerCase().contains("card")) {
                foundProduct = findProductInAssortmentById(Integer.parseInt(splittedArgs[0]));
                int amountOfProduct = Integer.parseInt(splittedArgs[1]);
                orderMap.put(foundProduct,
                        orderMap.containsKey(foundProduct) ? orderMap.get(foundProduct) + amountOfProduct
                                : amountOfProduct);
            } else {
                if (splittedArgs[0].equalsIgnoreCase("simplecard")
                        || splittedArgs[0].equalsIgnoreCase("goldcard")) {
                    this.card = DiscountCard.valueOf(splittedArgs[0].toUpperCase());
                    this.card.setNumber(splittedArgs[1].trim());
                } else throw new InvalidCardTypeException();
            }
        }
        return this;
    }

    public GroceryStore addOrderFromFile(File file) throws NoSuchProductException, InvalidCardNumberException, InvalidCardTypeException {
        this.addOrder(Loader.loadStringsFromFile(file));
        return this;
    }


    //обработка заказа (подсчет цены, учитывание скидок и формирование информации для чека)
    public GroceryStore processOrder() throws OrderAreNotCreatedException {
        if (orderMap == null)
            throw new OrderAreNotCreatedException();

        infoForReceiptFormedFromOrder = new StringBuilder();
        double totalCostOfPromotionalProduct;
        Product product;
        int amount;

        for (Map.Entry<Product, Integer> entry : orderMap.entrySet()) {

            product = entry.getKey();
            amount = entry.getValue();

            for (int i = 0; i < amount; i++)
                infoForReceiptFormedFromOrder.append(String.format("%-5s %-29s %-10s\n",
                        product.getId(), product.getName(), product.getCost()));

            //если товар акционный и его кол-во 5 и выше
            if (product.isPromotional() && amount > 4) {
                infoForReceiptFormedFromOrder.append(String.format(
                        "%-5s %-23s\n", "*", "The item " +
                                product.getName() + " is promotional"
                )).append(String.format("%-5s %-23s", "*", "Discount 10% has been provided\n"));

                totalCostOfPromotionalProduct = product.getCost() * amount;
                totalCostOfPromotionalProduct -= totalCostOfPromotionalProduct / 100 * 10;
                infoForReceiptFormedFromOrder.append(String.format(
                        "%-5s %-23s %-10s\n", "*", "Total cost in position " + product.getName(),
                        totalCostOfPromotionalProduct
                ));
                totalCost += totalCostOfPromotionalProduct;
            } else {
                totalCost += product.getCost() * amount;
            }
        }

        if (card != null) {
            infoForReceiptFormedFromOrder.append("========================================\n");
            infoForReceiptFormedFromOrder.append("*\t  ").append(card)
                    .append("\n*\t  has been provided\n")
                    .append("*\t  Included " + card.getDiscount() + "% discount\n");
            totalCost -= totalCost / 100 * card.getDiscount();
        }
        infoForReceiptFormedFromOrder.append("========================================\n");
        infoForReceiptFormedFromOrder.append(String.format("%-5s %-28s %.2f", "***", "Total cost:", totalCost));
        return this;
    }

    private Product findProductInAssortmentById(int id) throws NoSuchProductException {
        return Optional.of(assortmentSet.stream()
                .filter(e -> e.getId() == id).findAny()
                .orElseThrow(() -> new NoSuchProductException())).get();
    }

    public void printCheckToConsole() {
        Printer.printReceiptToConsole(this, infoForReceiptFormedFromOrder);
    }

    public void printCheckToFile(File file) {
        Printer.printReceiptToFile(file, this, infoForReceiptFormedFromOrder);
    }

    @Override
    public String toString() {
        return "GroceryStore{" +
                "name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", assortmentSet=" + assortmentSet +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GroceryStore that = (GroceryStore) o;
        return
                Objects.equals(name, that.name) &&
                        Objects.equals(phoneNumber, that.phoneNumber) &&
                        Objects.equals(assortmentSet, that.assortmentSet);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, phoneNumber, assortmentSet, orderMap, card, infoForReceiptFormedFromOrder, totalCost);
    }
}
