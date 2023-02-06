package Exceptions;

public class NameAlreadyExistsException extends CustomException {
    public NameAlreadyExistsException(String type) {
        super("A " + type + " with that name already exists.");
    }
}
