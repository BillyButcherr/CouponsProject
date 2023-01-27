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
            Thread t1 = new Thread(new CouponExpirationDailyJob());
            LoginManager loginManager = LoginManager.getInstance();
            CompaniesDBDAO companiesDAO = new CompaniesDBDAO();
            companiesDAO.deleteAllRecordsBeforeTest();

            AdminFacade adminFacade = (AdminFacade)loginManager.Login("admin@admin.com", "admin", ClientType.Administrator);
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
                    company3ID,
                    "Mark",
                    "Swims@best.com",
                    "pa$$word",
                    null
            ));
            System.out.println(adminFacade.getOneCompany(company1ID));
            adminFacade.deleteCompany(adminFacade.getOneCompany(company3ID));


            CompanyFacade companyFacade = (CompanyFacade)loginManager.Login("gal@galcorp.com", "1234Pass", ClientType.Company);
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
                    "2023-01-15",
                    "2023-02-25",
                    30,
                    200,
                    "url"
            );

            long coupon1ID = companyFacade.addCoupon(coupon1);
            long coupon2ID = companyFacade.addCoupon(coupon2);
            long coupon3ID = companyFacade.addCoupon(coupon3);
            long coupon4ID = companyFacade.addCoupon(coupon4);
            coupon1.setId(coupon1ID);
            coupon2.setId(coupon2ID);
            coupon3.setId(coupon3ID);
            coupon4.setId(coupon4ID);


            t1.start();


            System.out.println(companyFacade.getAllCompanyCoupons());

            long customer1ID = adminFacade.addCustomer(new Customer(
                    "mark",
                    "shark",
                    "cow@rules.com",
                    "1234",
                    null
            ));
            long customer2ID = adminFacade.addCustomer(new Customer(
                    "folk",
                    "music",
                    "cool@artists.com",
                    "bestPass",
                    null
            ));long customer3ID = adminFacade.addCustomer(new Customer(
                    "free-folk",
                    "train",
                    "boob@marleys.org",
                    "$tron9Pa$$",
                    null
            ));
            System.out.println(adminFacade.getAllCustomers());
            adminFacade.updateCustomer(new Customer(
                    customer2ID,
                    "rock",
                    "music",
                    "Rolling@stones.com",
                    "bestPass",
                    null
            ));
            System.out.println(adminFacade.getOneCustomer(customer2ID));
            adminFacade.deleteCustomer(adminFacade.getOneCustomer(customer3ID));
            System.out.println(adminFacade.getAllCustomers());

            CustomerFacade customerFacade = (CustomerFacade)loginManager.Login("cow@rules.com", "1234", ClientType.Customer);
            customerFacade.purchaseCoupon(coupon1);
            customerFacade.purchaseCoupon(coupon2);
            customerFacade.purchaseCoupon(coupon3);
            customerFacade.purchaseCoupon(coupon4);

        }catch (Exception e){
            System.out.println(e.getClass());
            System.out.println(e.getMessage());
        }
    }
}
