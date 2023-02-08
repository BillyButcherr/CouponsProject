package Exceptions;

public class EmptyCompanyIDException extends CustomException{
    public EmptyCompanyIDException() {
        super("Empty ID field inside company");
    }
}
