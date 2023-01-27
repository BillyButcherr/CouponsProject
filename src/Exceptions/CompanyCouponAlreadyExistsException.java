package Exceptions;

public class CompanyCouponAlreadyExistsException extends Exception{
    public CompanyCouponAlreadyExistsException() {
        super("A coupon with the same title and companyID already exists.");
    }
}
