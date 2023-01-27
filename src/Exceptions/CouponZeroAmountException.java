package Exceptions;

public class CouponZeroAmountException extends Exception{
    public CouponZeroAmountException() {
        super("Coupon amount is zero");
    }
}
