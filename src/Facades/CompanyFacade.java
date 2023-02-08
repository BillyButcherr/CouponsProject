package Facades;

import Beans.Company;
import Beans.Coupon;
import Exceptions.*;
import enums.Category;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CompanyFacade extends ClientFacade{
    private long companyID;

    /**
     * Authenticate company login credentials.
     * @param email q company email address.
     * @param password q company password.
     * @return true when login successful, else false.
     * @throws SQLException
     */
    @Override
    public boolean login(String email, String password) throws SQLException {
        companyID = companiesDAO.isCompanyExist(email, password);
       // if(id == -1) return false;
        return companyID != -1;
    }

    /**
     * Adds a new coupon to the logged company.
     * @param coupon a coupon to be added.
     * @return The added coupon auto-generated ID.
     * @throws SQLException SQL error
     * @throws CompanyCouponSameTitleAlreadyExistsException a company coupon with the same title already exists.
     */
    public long addCoupon(Coupon coupon) throws SQLException, CompanyCouponSameTitleAlreadyExistsException {
        List<Coupon> allCoupons = couponsDAO.getAllCoupons();
        Coupon couponWithSameTitle = null;
        if(allCoupons.size() > 0){
            couponWithSameTitle = allCoupons.stream()
                    .filter(otherCoupon -> otherCoupon.getCompanyID() == coupon.getCompanyID() &&
                            otherCoupon.getTitle().equals(coupon.getTitle()))
                    .findFirst()
                    .orElse(null);
        }

        if(couponWithSameTitle == null)
            return couponsDAO.addCoupon(coupon);
        else
            throw new CompanyCouponSameTitleAlreadyExistsException();
    }

    /**
     * Updates a given coupon information.
     * @param coupon a coupon to be updated.
     * @throws SQLException
     * @throws EmptyCouponIDException ID field inside coupon hasn't been initialized yet.
     * @throws CouponDoesNotExistException A coupon with that ID doesn't exist.
     * @throws CouponCompanyChangeNotAllowedException Illegal attempt to change company ID of the coupon.
     */
    public void updateCoupon(Coupon coupon) throws SQLException, EmptyCouponIDException, CouponDoesNotExistException,
            CouponCompanyChangeNotAllowedException {
        if(coupon.getId() == 0)
            throw new EmptyCouponIDException();
        Coupon oldRecord = couponsDAO.getOneCoupon(coupon.getId());
        if(oldRecord == null)
            throw new CouponDoesNotExistException();
        if(coupon.getCompanyID() != oldRecord.getCompanyID())
            throw new CouponCompanyChangeNotAllowedException();
        couponsDAO.updateCoupon(coupon);
    }

    /**
     *
     * @param couponID The ID of a coupon to be deleted.
     * @throws SQLException
     * @throws EmptyCouponIDException The coupon ID field isn't initialized.
     * @throws CouponDoesNotExistException A coupon with the provided ID does not exist.
     */
    public void deleteCoupon(long couponID) throws SQLException, EmptyCouponIDException, CouponDoesNotExistException {
        if(couponID == 0)
            throw new EmptyCouponIDException();
        Coupon oldRecord = couponsDAO.getOneCoupon(couponID);
        if(oldRecord == null)
            throw new CouponDoesNotExistException();
        couponsDAO.deleteAllCouponPurchases(couponID);
        couponsDAO.deleteCoupon(couponID);
    }

    /**
     *
     * @return
     * @throws SQLException
     */
    public ArrayList<Coupon> getAllCompanyCoupons() throws SQLException{
        return couponsDAO.getAllCompanyCoupons(companyID);
    }
//    public ArrayList<Coupon> getAllCompanyCoupons() throws SQLException{
//        return (ArrayList<Coupon>) couponsDAO.getAllCoupons().stream()
//                .filter(coupon -> coupon.getCompanyID() == this.companyID)
//                .collect(Collectors.toList());
//    }

    /**
     * Gets all coupons of logged company and a given category.
     * @param category The category to filter by.
     * @return a list of all fetched coupons.
     * @throws SQLException
     */
    public ArrayList<Coupon> getAllCompanyCoupons(Category category) throws SQLException {
        return (ArrayList<Coupon>) this.getAllCompanyCoupons().stream()
                .filter(coupon -> coupon.getCategory().equals(category))
                .collect(Collectors.toList());
    }

    /**
     * Gets all coupons of logged company and at a given max price .
     * @param maxPrice the maximum price to filter by.
     * @return a list of all fetched coupons.
     * @throws SQLException
     */
    public ArrayList<Coupon> getAllCompanyCoupons(double maxPrice) throws SQLException {
        return (ArrayList<Coupon>) this.getAllCompanyCoupons().stream()
                .filter(coupon -> coupon.getPrice() <= maxPrice)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves the logged company information.
     * @return Company information.
     * @throws SQLException
     */
    public Company getCompanyDetails() throws SQLException{
        return companiesDAO.getOneCompany(this.companyID);
    }
}
