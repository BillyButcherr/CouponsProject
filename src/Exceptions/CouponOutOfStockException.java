package Exceptions;

public class CouponOutOfStockException extends CustomException{
    public CouponOutOfStockException() {
        super("Coupon is out of stock.");
    }
}
