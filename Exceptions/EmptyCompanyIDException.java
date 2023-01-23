package Exceptions;

public class EmptyCompanyIDException extends Exception{
    public EmptyCompanyIDException() {
        super("Empty ID field inside company");
    }
}
