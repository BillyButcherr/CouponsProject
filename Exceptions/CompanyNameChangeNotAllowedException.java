package Exceptions;

public class CompanyNameChangeNotAllowedException extends Exception{
    public CompanyNameChangeNotAllowedException() {
        super("Companies are not allowed to change their name");
    }
}
