package Exceptions;

public class CouponCompanyChangeNotAllowedException extends CustomException{
    public CouponCompanyChangeNotAllowedException() {
        super("Coupons are not allowed to change their company ID");
    }
}
