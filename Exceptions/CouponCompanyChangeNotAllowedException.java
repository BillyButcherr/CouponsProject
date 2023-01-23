package Exceptions;

public class CouponCompanyChangeNotAllowedException extends Exception{
    public CouponCompanyChangeNotAllowedException() {
        super("Coupons are not allowed to change their company id");
    }
}
