import Beans.Company;
import Beans.Coupon;
import Beans.Customer;
import DAO.CompaniesDAO;
import DBDAO.CompaniesDBDAO;
import Facades.AdminFacade;
import Facades.CompanyFacade;
import Facades.CustomerFacade;
import enums.Category;
import enums.ClientType;

public class Test {
    public static void testAll() {
        try {
            //Thread t1 = new Thread(new CouponExpirationDailyJob());
            //t1.start();
            LoginManager loginManager = LoginManager.getInstance();
            CompaniesDAO companiesDAO = new CompaniesDBDAO();
            AdminFacade adminFacade = (AdminFacade)loginManager.Login("admin@admin.com", "admin", ClientType.Administrator);
            CompanyFacade companyFacade = (CompanyFacade)loginManager.Login("gal@galcorp.com", "1234Pass", ClientType.Company);
            long company1ID = adminFacade.addCompany(new Company(
                    "Gal-Corp",
                    "gal@galcorp.com",
                    "1234Pass",
                    null
            ));
            long company2ID = adminFacade.addCompany(new Company(
                    "JohnTech",
                    "John@tech.com",
                    "12345",
                    null
            ));
            long company3ID = adminFacade.addCompany(new Company(
                    "Mark",
                    "Mark@suits.com",
                    "1234",
                    null
            ));
            System.out.println(adminFacade.getAllCompanies());
            adminFacade.updateCompany(new Company(
                    company1ID,
                    "Swims",
                    "Swims@best.com",
                    "pa$$word",
                    null
            ));
            Coupon coupon1 = new Coupon(
                    company1ID,
                    Category.Vacation,
                    "Miami - South Beach",
                    "10 Days of fun",
                    "2023-01-14",
                    "2023-02-14",
                    15,
                    1500,
                    "url"
            );
            Coupon coupon2 = new Coupon(
                    company1ID,
                    Category.Vacation,
                    "Spain - Palma de Mallorca",
                    "5 Days of sun",
                    "2023-01-05",
                    "2023-02-28",
                    5,
                    800,
                    "url"
            );
            Coupon coupon3 = new Coupon(
                    company2ID,
                    Category.Restaurant,
                    "Mexican bar",
                    "best tacos",
                    "2023-01-10",
                    "2023-02-22",
                    30,
                    200,
                    "url"
            );
            Coupon coupon4 = new Coupon(
                    company2ID,
                    Category.Restaurant,
                    "Italian bar",
                    "best pasta",
                    "2023-01-155",
                    "2023-02-25",
                    30,
                    200,
                    "url"
            );

            companyFacade.addCoupon(coupon1);
            companyFacade.addCoupon(coupon2);
            companyFacade.addCoupon(coupon3);
            companyFacade.addCoupon(coupon4);

            long coupon1ID = companyFacade.addCoupon(coupon1);
            long coupon2ID = companyFacade.addCoupon(coupon2);
            long coupon3ID = companyFacade.addCoupon(coupon3);
            long coupon4ID = companyFacade.addCoupon(coupon4);

            System.out.println(adminFacade.getOneCompany(1));
            adminFacade.deleteCompany(adminFacade.getOneCompany(1));

            adminFacade.addCustomer(new Customer(
                    "dark",
                    "mark",
                    "sark@mail.com",
                    "bestPass",
                    null
            ));
            adminFacade.addCustomer(new Customer(
                    "folk",
                    "music",
                    "cool@artists.com",
                    "bestPass",
                    null
            ));
            System.out.println(adminFacade.getAllCustomers());
            adminFacade.updateCustomer(new Customer(
                    1,
                    "rock",
                    "music",
                    "Rolling@stones.com",
                    "bestPass",
                    null
            ));
            System.out.println(adminFacade.getOneCustomer(1));
            adminFacade.deleteCustomer(adminFacade.getOneCustomer(1));


            companyFacade.addCoupon(new Coupon(
                    1,
                    Category.Vacation,
                    "Best vacation",
                    "bestPass",
                    "2023-01-21",
                    "2023-01-28",
                    10,
                    189,
                    "url"
            ));
            companyFacade.addCoupon(new Coupon(
                    2,
                    Category.Restaurant,
                    "Best restaurant",
                    "bestPass",
                    "2023-01-21",
                    "2023-01-28",
                    15,
                    90.5,
                    "url"
            ));
            //companyFacade.getAllCustomersCoupons(1);
            companyFacade.updateCoupon(
                    new Coupon(
                            2,
                            2,
                            Category.Restaurant,
                            "average at best restaurant",
                            "little Italy",
                            "2023-01-20",
                            "2023-01-25",
                            10,
                            88,
                            "url"
            ));
            //System.out.println(companyFacade.getAllCoupons());
            //System.out.println(companyFacade.getOneCoupon(1));
            CustomerFacade customerFacade = (CustomerFacade)loginManager.Login("cow@rules.com", "1234", ClientType.Customer);

        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }
}
