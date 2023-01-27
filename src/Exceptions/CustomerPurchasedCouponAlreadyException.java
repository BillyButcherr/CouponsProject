package Exceptions;

public class CustomerPurchasedCouponAlreadyException extends Exception{
    public CustomerPurchasedCouponAlreadyException() {
        super("the same coupon was purchased by this customer already.");
    }
}
