package Exceptions;

public class CustomerAlreadyExistsException extends CustomException{
    public CustomerAlreadyExistsException() {
        super("A customer with the same email already exists.");
    }
}
