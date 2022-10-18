package exceptions;

public class ValueDoesNotMatch extends AssertionError{
    public ValueDoesNotMatch(String message) {
        super(message);
    }
}
