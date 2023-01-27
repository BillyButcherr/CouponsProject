package Exceptions;

public class CompanyAlreadyExistsException extends Exception{
    public CompanyAlreadyExistsException() {
        super("A company with the same name or email already exists.");
    }
}
