package Exceptions;

public class CouponDateAheadOfTimeException extends Exception{
    public CouponDateAheadOfTimeException() {
        super("Coupon start date is ahead of time.");
    }
}
