package Exceptions;

public class CustomerDoesNotExistException extends CustomException{
    public CustomerDoesNotExistException() {
        super("A customer with that id doesn't exist.");
    }
}
