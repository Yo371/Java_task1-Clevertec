package products;

public class Candy extends Product {
    public Candy(int id, String name, double cost, boolean isPromotional) {
        super(id, name, cost, isPromotional);
    }

    @Override
    public String toString() {
        return "Candy id = " + getId() +
                " name = " + getName() +
                " cost = " + getCost();
    }
}
