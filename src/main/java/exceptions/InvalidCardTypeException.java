package exceptions;

public class InvalidCardTypeException extends GroceryException {
    @Override
    public String getMessage() {
        return "Type of cards should be simplecard or goldcard";
    }
}
