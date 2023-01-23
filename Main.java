import Beans.Coupon;
import DAO.CouponsDAO;
import DBDAO.CouponsDBDAO;
import Facades.CustomerFacade;

public class Main {
    public static void main(String[] args){
        try {
            CouponsDAO couponsDAO = new CouponsDBDAO();
            CustomerFacade customerFacade = new CustomerFacade();
            customerFacade.login("cow@rules.com", "1234");
            //couponsDAO.updateCoupon(couponsDAO.getOneCoupon(10));
            Coupon coupon = couponsDAO.getOneCoupon(10);
            //Calendar today = Calendar.getInstance();
            //System.out.println(today.before(coupon.getEndDate()));
            customerFacade.purchaseCoupon(couponsDAO.getOneCoupon(10));
            //System.out.println(customerFacade.getCustomerCoupons());
            //System.out.println(customerFacade.getCustomerCoupons(Category.Vacation));
            //System.out.println(customerFacade.getCustomerCoupons(300));
            //System.out.println(customerFacade.getCustomerDetails());
            //AdminFacade adminFacade = new AdminFacade();
//            adminFacade.addCompany(new Company(
//                    "ShimmyTech",
//                    "Aye@tech.com",
//                    "12345",
//                    null
//            ));
//            adminFacade.updateCompany(new Company(
//                    8,
//                    "ShimmyTech",
//                    "Aye@Back.com",
//                    "123456",
//                    null
//            ));
            //System.out.println(adminFacade.getAllCompanies());
           // System.out.println(adminFacade.getOneCompany(1));
            //System.out.println(adminFacade.getOneCustomer(2));
//            adminFacade.deleteCustomer(new Customer(
//                    4,
//                    "boy",
//                    "not",
//                    "cool1@artists.com",
//                    "fair",
//                    null
//            ));

        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}