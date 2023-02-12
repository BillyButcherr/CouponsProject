package Facades;

import Exceptions.*;
import enums.Category;
import Beans.Coupon;
import Beans.Customer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.stream.Collectors;

public class CustomerFacade extends ClientFacade{
    private long customerID;

    /**
     * Authenticate customer login credentials.
     * @param email The customer email address.
     * @param password The customer password.
     * @return true upon successful login, else false.
     * @throws SQLException
     */
    @Override
    public boolean login(String email, String password) throws SQLException{
        customerID = customersDAO.isCustomerExist(email, password);
        //if(id == -1)
        //    return false;
        return customerID != -1;
    }

    /**
     * Purchase a given coupon.
     * @param coupon The coupon to be purchased.
     * @throws SQLException SQL error
     * @throws EmptyCouponIDException The coupon ID isn't initialized.
     * @throws CouponOutOfStockException The coupon is out of stock.
     * @throws CustomerPurchasedCouponAlreadyException The provided coupon was already purchased by logged customer.
     * @throws CouponDateExpiredException The coupon date expired.
     * @throws CouponDateAheadOfTimeException The coupon date is ahead of time.
     * @throws CouponDoesNotExistException The coupon does not exist.
     */
    //Works
    public void purchaseCoupon(Coupon coupon) throws SQLException, EmptyCouponIDException, CouponOutOfStockException, CustomerPurchasedCouponAlreadyException,
            CouponDateExpiredException, CouponDateAheadOfTimeException, CouponDoesNotExistException {
        if (coupon.getId() == 0)
            throw new EmptyCouponIDException();
        if(couponsDAO.getOneCoupon(coupon.getId()) == null)
            throw new CouponDoesNotExistException();
        if(coupon.getAmount() <= 0)
            throw new CouponOutOfStockException();
        Coupon existingCoupon = couponsDAO.getAllCustomerCoupons(customerID).stream()
                .filter(couponItem -> couponItem.getId() == coupon.getId())
                .findFirst()
                .orElse(null);
        if(existingCoupon != null)
            throw new CustomerPurchasedCouponAlreadyException();
        Calendar today = Calendar.getInstance(), startDate = Calendar.getInstance(), endDate = Calendar.getInstance();
        startDate.setTime(coupon.getStartDate());
        endDate.setTime(coupon.getEndDate());

        if(today.after(endDate))
            throw new CouponDateExpiredException();
        if(today.before(startDate))
            throw new CouponDateAheadOfTimeException();
        couponsDAO.addCouponPurchase(customerID, coupon.getId());
        coupon.setAmount(coupon.getAmount() - 1);
        couponsDAO.updateCoupon(coupon);
    }

    /**
     *
     * @return A list of all purchased Coupons of logged Customer.
     * @throws SQLException
     */
    //Works
    public ArrayList<Coupon> getCustomerCoupons() throws SQLException{
        return couponsDAO.getAllCustomerCoupons(customerID);
    }

    /**
     *
     * @param category The category to filter by.
     * @return A list of all <code>Customer</code> purchased coupons in a given category.
     * @throws SQLException
     */
    //Works
    public ArrayList<Coupon> getCustomerCoupons(Category category) throws SQLException{
        return (ArrayList<Coupon>)couponsDAO.getAllCustomerCoupons(customerID).stream()
                .filter(coupon -> coupon.getCategory().equals(category))
                .collect(Collectors.toList());
    }

    /**
     *
     * @param maxPrice The price to filter by.
     * @return A list of all customer purchased coupons in a given price max.
     * @throws SQLException
     */
    //Works
    public ArrayList<Coupon> getCustomerCoupons(double maxPrice) throws SQLException{
        return (ArrayList<Coupon>)couponsDAO.getAllCustomerCoupons(customerID).stream()
                .filter(coupon -> coupon.getPrice() <= maxPrice)
                .collect(Collectors.toList());
    }

    /**
     *
     * @return The logged customer information.
     * @throws SQLException
     * @throws EmptyCustomerIDException The customer ID field isn't initialized.
     * @throws CustomerDoesNotExistException A customer with given ID doesn't exist - Cant really happen since the customer already logged???
     */
    //Works
    public Customer getCustomerDetails() throws SQLException, EmptyCustomerIDException, CustomerDoesNotExistException {
        if (customerID == 0)
            throw new EmptyCustomerIDException();
        Customer customer = customersDAO.getOneCustomer(customerID);
        if(customer == null)
            throw new CustomerDoesNotExistException();
        customer.setCoupons(couponsDAO.getAllCustomerCoupons(customerID));
        return customer;
    }
}
