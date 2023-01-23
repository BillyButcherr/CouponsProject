package Facades;

import Beans.Company;
import Beans.Coupon;
import Beans.Customer;
import DAO.CouponsDAO;
import Exceptions.*;
import enums.Category;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CompanyFacade extends ClientFacade{
    private long companyID;

    @Override
    public boolean login(String email, String password){
        long id = companiesDAO.isCompanyExist(email, password);
        if(id == -1) return false;
        companyID = id;
        return true;
    }


    public long addCoupon(Coupon coupon) throws CompanyCouponAlreadyExistsException {
        Coupon couponWithSameTitle = couponsDAO.getAllCoupons().stream()
                .filter(otherCoupon -> otherCoupon.getCompanyID() == coupon.getCompanyID() &&
                        otherCoupon.getTitle().equals(coupon.getTitle()))
                .findFirst()
                .orElse(null);
        if(couponWithSameTitle != null)
            throw new CompanyCouponAlreadyExistsException();
        return couponsDAO.addCoupon(coupon);
    }

    public void updateCoupon(Coupon coupon) throws EmptyCouponIDException, CouponDoesNotExistException, CouponCompanyChangeNotAllowedException {
        if(coupon.getId() == 0)
            throw new EmptyCouponIDException();
        Coupon oldRecord = couponsDAO.getOneCoupon(coupon.getId());
        if(oldRecord == null)
            throw new CouponDoesNotExistException();
        if(coupon.getCompanyID() != oldRecord.getCompanyID())
            throw new CouponCompanyChangeNotAllowedException();
        couponsDAO.updateCoupon(coupon);
    }

    public void deleteCoupon(Coupon coupon) throws EmptyCouponIDException, CouponDoesNotExistException {
        if(coupon.getId() == 0)
            throw new EmptyCouponIDException();
        Coupon oldRecord = couponsDAO.getOneCoupon(coupon.getId());
        if(oldRecord == null)
            throw new CouponDoesNotExistException();
        couponsDAO.deleteAllCouponsPurchases(coupon);
    }

    public ArrayList<Coupon> getAllCompanyCoupons() {
        return (ArrayList<Coupon>) couponsDAO.getAllCoupons().stream()
                .filter(coupon -> coupon.getCompanyID() == this.companyID)
                .collect(Collectors.toList());
    }
    public ArrayList<Coupon> getAllCompanyCoupons(Category category) {
        return (ArrayList<Coupon>) this.getAllCompanyCoupons().stream()
                .filter(coupon -> coupon.getCategory() == category)
                .collect(Collectors.toList());
    }
    public ArrayList<Coupon> getAllCompanyCoupons(double maxPrice) {
        return (ArrayList<Coupon>) this.getAllCompanyCoupons().stream()
                .filter(coupon -> coupon.getPrice() <= maxPrice)
                .collect(Collectors.toList());
    }
    public Company getCompanyDetails(){
        return companiesDAO.getOneCompany(this.companyID);
    }
}
