package Exceptions;

public class CouponDateExpiredException extends CustomException{
    public CouponDateExpiredException() {
        super("Coupon date has expired.");
    }
}
