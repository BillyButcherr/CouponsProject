package Exceptions;

public class CompanyInvalidEmailOrPasswordException extends CustomException{
    public CompanyInvalidEmailOrPasswordException() {
        super("Company invalid email or password at login.");
    }
}
