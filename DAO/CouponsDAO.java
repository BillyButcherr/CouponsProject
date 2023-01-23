package DAO;

import Beans.Company;
import Beans.Coupon;
import Beans.Customer;

import java.util.ArrayList;
import java.util.List;

public interface CouponsDAO {
    long addCoupon(Coupon coupon);
    void updateCoupon(Coupon coupon);
    void deleteCoupon(Coupon coupon);
    ArrayList<Coupon> getAllCoupons();
    Coupon getOneCoupon(long couponID);
    void addCouponPurchase(long customerID, long couponID);
    void deleteCouponPurchase(long customerID, long couponID);
    ArrayList<Coupon> getAllCustomersCoupons(long customerID);
    void deleteAllCompanyCoupons(Company company);
    void deleteAllCompanyCouponsPurchases(Company company);
    void deleteAllCustomerCouponsPurchases(Customer customer);
    void deleteAllCouponsPurchases(Coupon coupon);

    public void deleteAllCouponsFromList(List<Long> couponsToDeleteIDsList);
}
