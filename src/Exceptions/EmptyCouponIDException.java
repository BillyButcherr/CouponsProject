package Exceptions;

public class EmptyCouponIDException extends CustomException{
    public EmptyCouponIDException() {
        super("Empty ID field inside coupon");
    }
}
