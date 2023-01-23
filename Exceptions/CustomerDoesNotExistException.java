package Exceptions;

public class CustomerDoesNotExistException extends Exception{
    public CustomerDoesNotExistException() {
        super("A customer with that id doesn't exist.");
    }
}
