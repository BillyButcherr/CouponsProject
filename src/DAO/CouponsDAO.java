package DAO;

import Beans.Company;
import Beans.Coupon;
import Beans.Customer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface CouponsDAO {
    long addCoupon(Coupon coupon) throws SQLException;
    void updateCoupon(Coupon coupon) throws SQLException;
    void deleteCoupon(Coupon coupon) throws SQLException;
    ArrayList<Coupon> getAllCoupons() throws SQLException;
    Coupon getOneCoupon(long couponID) throws SQLException;
    void addCouponPurchase(long customerID, long couponID) throws SQLException;
    void deleteCouponPurchase(long customerID, long couponID) throws SQLException;
    ArrayList<Coupon> getAllCustomersCoupons(long customerID) throws SQLException;
    void deleteAllCompanyCoupons(Company company) throws SQLException;
    void deleteAllCompanyCouponsPurchases(Company company) throws SQLException;
    void deleteAllCustomerCouponsPurchases(Customer customer) throws SQLException;
    void deleteAllCouponsPurchases(Coupon coupon) throws SQLException;
}
