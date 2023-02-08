package Exceptions;

public class EmptyCustomerIDException extends CustomException{
    public EmptyCustomerIDException() {
        super("Empty ID field inside customer");
    }
}
