package Exceptions;

public class NameAlreadyExistsException extends Exception {
    public NameAlreadyExistsException(String type) {
        super("A " + type + " with that name already exists.");
    }
}
