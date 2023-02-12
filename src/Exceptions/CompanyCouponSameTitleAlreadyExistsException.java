package Exceptions;

public class CompanyCouponSameTitleAlreadyExistsException extends CustomException{
    public CompanyCouponSameTitleAlreadyExistsException() {
        super("A coupon with the same title and companyID already exists.");
    }
}
