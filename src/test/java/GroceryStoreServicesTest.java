import exceptions.GroceryException;
import exceptions.InvalidCardNumberException;
import exceptions.NoSuchProductException;
import exceptions.OrderAreNotCreatedException;
import market.GroceryStore;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import products.Bread;
import products.Candy;
import products.Milk;
import products.Product;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class GroceryStoreServicesTest {

    private GroceryStore groceryStore;

    @Rule
    public ExpectedException thrownEx = ExpectedException.none();

    @Before
    public void setUp() {
        Set<Product> productTreeSet = new TreeSet<>();
        productTreeSet.add(new Bread(2, "Bread", 1.50, true));
        productTreeSet.add(new Milk(4, "Milk 2.5%", 1.30, false));
        productTreeSet.add(new Milk(1, "Milk 3.2%", 1.80, false));
        productTreeSet.add(new Candy(3, "KitKat", 0.80, true));
        productTreeSet.add(new Candy(5, "Snickers", 2, true));

        groceryStore = new GroceryStore("Green Store",
                "+375(29)937-99-92", productTreeSet);
    }

    @Test
    public void whenPassedInvalidIdOfProduct() throws Exception {
        thrownEx.expect(NoSuchProductException.class);
        groceryStore.addOrder(new String[]{"6-1"});
    }

    @Test
    public void whenPassedCardNumberWithInvalidLength() throws Exception {
        thrownEx.expect(InvalidCardNumberException.class);
        groceryStore.addOrder(new String[]{"2-1", "3-3", "goldcard-123456"});
    }

    @Test
    public void whenOrderProcessingWithoutCreation() throws Exception {
        thrownEx.expect(OrderAreNotCreatedException.class);
        groceryStore.processOrder();
    }

    @Test
    public void assertionOrdersMapCost() throws GroceryException {
        groceryStore.addOrder(new String[]{"1-2", "2-3", "3-4", "4-1", "goldcard-123456864111"})
                .processOrder();

        Map<Product, Integer> orderMap = new HashMap<>();
        orderMap.put(new Milk(1, "Milk 3.2%", 1.80, false), 2);
        orderMap.put(new Bread(2, "Bread", 1.50, true), 3);
        orderMap.put(new Candy(3, "KitKat", 0.80, true), 4);
        orderMap.put(new Milk(4, "Milk 2.5%", 1.30, false), 1);

        Assert.assertEquals(orderMap, groceryStore.getOrderMap());
    }

    @Test
    public void assertionNameOfGroceryStore() {
        Assert.assertEquals(groceryStore.getName(), "Green Store");
    }

    @Test
    public void assertionNumberOfGroceryStore() {
        Assert.assertEquals(groceryStore.getPhoneNumber(), "+375(29)937-99-92");
    }

    @Test
    public void assertionTotalCost() throws GroceryException {
        groceryStore.addOrder(new String[]{"1-2", "2-3", "3-4", "4-1", "goldcard-123456864111"})
                .processOrder();
        Assert.assertEquals("11,84", String.format("%.2f", groceryStore.getTotalCost()));
    }

}
