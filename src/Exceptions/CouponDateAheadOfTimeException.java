package Exceptions;

public class CouponDateAheadOfTimeException extends CustomException{
    public CouponDateAheadOfTimeException() {
        super("Coupon start date is ahead of time.");
    }
}
