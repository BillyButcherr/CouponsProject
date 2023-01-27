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

    public CustomerFacade() {}

    @Override
    public boolean login(String email, String password) throws SQLException{
        long id = customersDAO.isCustomerExist(email, password);
        if(id == -1) return false;
        customerID = id;
        return true;
    }
    //Works
    public void purchaseCoupon(Coupon coupon) throws SQLException, EmptyCouponIDException, CouponZeroAmountException, CustomerPurchasedCouponAlreadyException,
            CouponDateExpiredException, CouponDateAheadOfTimeException {
        if (coupon.getId() == 0)
            throw new EmptyCouponIDException();
        if(coupon.getAmount() <= 0)
            throw new CouponZeroAmountException();
        Coupon existingCoupon = couponsDAO.getAllCustomersCoupons(customerID).stream()
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
    //Works
    public ArrayList<Coupon> getCustomerCoupons() throws SQLException{
        return couponsDAO.getAllCustomersCoupons(customerID);
    }
    //Works
    public ArrayList<Coupon> getCustomerCoupons(Category category) throws SQLException{
        return (ArrayList<Coupon>)couponsDAO.getAllCustomersCoupons(customerID).stream()
                .filter(coupon -> coupon.getCategory().equals(category))
                .collect(Collectors.toList());
    }
    //Works
    public ArrayList<Coupon> getCustomerCoupons(double maxPrice) throws SQLException{
        return (ArrayList<Coupon>)couponsDAO.getAllCustomersCoupons(customerID).stream()
                .filter(coupon -> coupon.getPrice() <= maxPrice)
                .collect(Collectors.toList());
    }
    //Works
    public Customer getCustomerDetails() throws SQLException, EmptyCustomerIDException, CustomerDoesNotExistException {
        if (customerID == 0)
            throw new EmptyCustomerIDException();
        Customer customer = customersDAO.getOneCustomer(customerID);
        if(customer == null)
            throw new CustomerDoesNotExistException();
        customer.setCoupons(couponsDAO.getAllCustomersCoupons(customerID));
        return customer;
    }
}
