package Exceptions;

public class CouponDateExpiredException extends Exception{
    public CouponDateExpiredException() {
        super("Coupon date has expired.");
    }
}
