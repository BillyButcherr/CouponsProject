package Exceptions;

public class CouponDoesNotExistException extends Exception{
    public CouponDoesNotExistException() {
        super("A coupon with that id doesn't exist.");
    }
}
