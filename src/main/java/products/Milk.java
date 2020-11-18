package products;

public class Milk extends Product {
    public Milk(int id, String name, double cost, boolean isPromotional) {
        super(id, name, cost, isPromotional);
    }

    @Override
    public String toString() {
        return "Milk id = " + getId() +
                " name = " + getName() +
                " cost = " + getCost();
    }
}
