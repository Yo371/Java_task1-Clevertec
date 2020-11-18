package exceptions;

public class InvalidCardNumberException extends GroceryException {
    @Override
    public String getMessage() {
        return "Number length must be 12";
    }
}
