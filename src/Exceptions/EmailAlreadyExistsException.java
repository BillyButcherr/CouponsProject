package Exceptions;

public class EmailAlreadyExistsException extends CustomException{
    public EmailAlreadyExistsException(String type) {
        super("A " + type + " with that email already exists.");
    }
}
