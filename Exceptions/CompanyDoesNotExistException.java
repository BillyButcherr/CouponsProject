package Exceptions;

public class CompanyDoesNotExistException extends Exception{
    public CompanyDoesNotExistException() {
        super("A company with that id doesn't exist.");
    }
}
