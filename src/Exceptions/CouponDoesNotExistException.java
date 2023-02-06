package Exceptions;

public class CouponDoesNotExistException extends CustomException{
    public CouponDoesNotExistException() {
        super("A coupon with that ID doesn't exist.");
    }
}
