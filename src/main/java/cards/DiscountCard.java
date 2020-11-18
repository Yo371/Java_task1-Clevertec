package cards;

import exceptions.InvalidCardNumberException;

public enum DiscountCard {
    SIMPLECARD(3), GOLDCARD(6);

    int discount;
    String number;

    DiscountCard(int discount) {
        this.discount = discount;
    }

    public int getDiscount() {
        return discount;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) throws InvalidCardNumberException {
        if (!number.matches("\\d{12}")) throw new InvalidCardNumberException();
        this.number = number;
    }

    @Override
    public String toString() {
        return this.name() + "(" + getNumber() + ")";
    }
}
