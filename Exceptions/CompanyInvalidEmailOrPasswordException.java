package Exceptions;

public class CompanyInvalidEmailOrPasswordException extends Exception{
    public CompanyInvalidEmailOrPasswordException() {
        super("Company invalid email or password at login.");
    }
}
