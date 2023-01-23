package Exceptions;

public class EmailAlreadyExistsException extends Exception{
    public EmailAlreadyExistsException(String type) {
        super("A " + type + " with that email already exists.");
    }
}
