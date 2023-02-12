package Exceptions;

public class CompanyNameChangeNotAllowedException extends CustomException{
    public CompanyNameChangeNotAllowedException() {
        super("Companies are not allowed to change their name");
    }
}
