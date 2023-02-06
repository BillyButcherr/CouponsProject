package DAO;

import Beans.Company;
import Beans.Coupon;
import Beans.Customer;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CouponsDAO {
    /**
     * Adds a given coupon to the DB.
     * @param coupon The coupon to be added.
     * @return The auto-generated ID for the new coupon.
     * @throws SQLException
     */
    long addCoupon(Coupon coupon) throws SQLException;
    /**
     * Updates a given coupon at the DB.
     * @param coupon The coupon to be updated.
     * @throws SQLException
     */
    void updateCoupon(Coupon coupon) throws SQLException;
    /**
     * Deletes a given coupon from DB.
     * @param coupon The coupon to be deleted.
     * @throws SQLException
     */
    void deleteCoupon(Coupon coupon) throws SQLException;
    /**
     * Gets all Coupons from DB.
     * @return An ArrayList of all fetched coupons.
     * @throws SQLException
     */
    ArrayList<Coupon> getAllCoupons() throws SQLException;
    /**
     *
     * @param couponID The requested Coupon ID.
     * @return The requested coupon information.
     * @throws SQLException
     */
    Coupon getOneCoupon(long couponID) throws SQLException;
    /**
     * Adds a coupon purchase to the DB.
     * @param customerID The ID of the customer who purchased the coupon.
     * @param couponID The ID of the coupon that was purchase by the customer.
     * @throws SQLException
     */
    void addCouponPurchase(long customerID, long couponID) throws SQLException;
    /**
     * Deletes A coupon purchase from DB.
     * @param customerID The ID of the customer who purchased the coupon.
     * @param couponID The ID of the coupon that was purchase by the customer.
     * @throws SQLException
     */
    void deleteCouponPurchase(long customerID, long couponID) throws SQLException;
    /**
     * Gets all bought coupons of a given customer from DB.
     * @param customerID The ID of the customer who bought the coupons.
     * @return A list of all fetched coupons that belong to the customer.
     * @throws SQLException
     */
    ArrayList<Coupon> getAllCustomerCoupons(long customerID) throws SQLException;
    /**
     * Deletes all coupons of a given company from DB.
     * @param company The company of the coupons.
     * @throws SQLException
     */
    void deleteAllCompanyCoupons(Company company) throws SQLException;
    /**
     *
     * @param companyID The ID of the company the coupons belong to.
     * @return A list of all the fetched coupons.
     * @throws SQLException
     */
    ArrayList<Coupon> getAllCompanyCoupons(long companyID) throws SQLException;
    /**
     * Deletes all coupons purchases of a given company from DB.
     * @param companyID The ID of the company.
     * @throws SQLException
     */
    void deleteAllCompanyCouponsPurchases(long companyID) throws SQLException;
    /**
     * Deletes all coupons purchases of a given customer.
     * @param customer The Customer.
     * @throws SQLException
     */
    void deleteAllCustomerCouponsPurchases(Customer customer) throws SQLException;
    /**
     * Deletes all purchases of a given coupon from DB.
     * @param coupon The coupon whose purchases are to be deleted.
     * @throws SQLException
     */
    void deleteAllCouponPurchases(Coupon coupon) throws SQLException;
}
