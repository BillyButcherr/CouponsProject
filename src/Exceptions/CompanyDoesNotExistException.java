package Exceptions;

public class CompanyDoesNotExistException extends CustomException{
    public CompanyDoesNotExistException() {
        super("A company with that id doesn't exist.");
    }
}
