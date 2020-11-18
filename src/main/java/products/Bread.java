package products;

public class Bread extends Product {
    public Bread(int id, String name, double cost, boolean isPromotional) {
        super(id, name, cost, isPromotional);
    }

    @Override
    public String toString() {
        return "Bread id = " + getId() +
                " name = " + getName() +
                " cost = " + getCost();
    }
}
