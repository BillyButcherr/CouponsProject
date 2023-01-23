package Exceptions;

public class CustomerAlreadyExistsException extends Exception{
    public CustomerAlreadyExistsException() {
        super("A customer with the same email already exists.");
    }
}
