package Exceptions;

public class EmptyCouponIDException extends Exception{
    public EmptyCouponIDException() {
        super("Empty ID field inside coupon");
    }
}
