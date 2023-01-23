package Exceptions;

public class EmptyCustomerIDException extends Exception{
    public EmptyCustomerIDException() {
        super("Empty ID field inside customer");
    }
}
