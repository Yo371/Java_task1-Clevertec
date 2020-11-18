package exceptions;

public class NoSuchProductException extends GroceryException {
    @Override
    public String getMessage() {
        return "There is no product with provided ID";
    }
}
