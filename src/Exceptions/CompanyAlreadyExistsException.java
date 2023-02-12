package Exceptions;

public class CompanyAlreadyExistsException extends CustomException{
    public CompanyAlreadyExistsException() {
        super("A company with the same name or email already exists.");
    }
}
