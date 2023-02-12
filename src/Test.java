import Beans.Company;
import Beans.Coupon;
import Beans.Customer;
import DBDAO.CompaniesDBDAO;
import DBDAO.ConnectionPool;
import Exceptions.CustomException;
import Facades.AdminFacade;
import Facades.CompanyFacade;
import Facades.CustomerFacade;
import enums.Category;
import enums.ClientType;

public class Test {
    /**
     * Checks all CRUD operations for each <code>bean</code>.
     */
    public static void testAll() {
        CouponExpirationDailyJob couponExpirationDailyJob = new CouponExpirationDailyJob();
        Thread t1 = new Thread(couponExpirationDailyJob);
        try {
            LoginManager loginManager = LoginManager.getInstance();
            CompaniesDBDAO companiesDAO = new CompaniesDBDAO();
            companiesDAO.deleteAllRecordsBeforeTest(); // Only for my testing, to clear tables before each run.
            // ----- Admin Facade, companies CRUD ------
            System.out.println("             ----- Admin Facade, companies CRUD ------\n");
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
            System.out.println("All companies : ");
            adminFacade.getAllCompanies().forEach(System.out::println);
            adminFacade.updateCompany(new Company(
                    company3ID,
                    "Mark",
                    "Swims@best.com",
                    "pa$$word",
                    null
            ));
            System.out.println("The updated company : ");
            System.out.println(adminFacade.getOneCompany(company3ID));
            System.out.println("Deleting company " + company3ID);
            adminFacade.deleteCompany(company3ID);

            System.out.println("All companies : ");
            adminFacade.getAllCompanies().forEach(System.out::println);
            // ----- Admin Facade, customers CRUD ------
            System.out.println("            ----- Admin Facade, customers CRUD ------\n");
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
            System.out.println("All customers :");
            adminFacade.getAllCustomers().forEach(System.out::println);
            adminFacade.updateCustomer(new Customer(
                    customer2ID,
                    "rock",
                    "music",
                    "Rolling@stones.com",
                    "bestPass",
                    null
            ));
            System.out.println("The updated Customer : ");
            System.out.println(adminFacade.getOneCustomer(customer2ID));
            System.out.println("Deleting customer " + customer3ID);
            adminFacade.deleteCustomer(customer3ID);
            System.out.println("All customers :");
            adminFacade.getAllCustomers().forEach(System.out::println);
            //System.out.println(adminFacade.getAllCustomers());

            // ------ Company Facade, coupons CRUD -------
            System.out.println("             ------ Company Facade, coupons CRUD -------\n");
            CompanyFacade companyFacade = (CompanyFacade)loginManager.Login("gal@galcorp.com", "1234Pass", ClientType.Company);
            System.out.println("Company information : ");
            System.out.println(companyFacade.getCompanyDetails());
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
                    Category.Food,
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
            Coupon coupon5 = new Coupon(
                    company1ID,
                    Category.Electricity,
                    "Electric oven",
                    "best oven",
                    "2023-01-15",
                    "2023-02-25",
                    30,
                    1200,
                    "url"
            );Coupon coupon6 = new Coupon(
                    company1ID,
                    Category.Electricity,
                    "50' TV Screen",
                    "best TV",
                    "2023-01-15",
                    "2023-02-25",
                    40,
                    3000,
                    "url"
            );

            long coupon1ID = companyFacade.addCoupon(coupon1);
            long coupon2ID = companyFacade.addCoupon(coupon2);
            long coupon3ID = companyFacade.addCoupon(coupon3);
            long coupon4ID = companyFacade.addCoupon(coupon4);
            long coupon5ID = companyFacade.addCoupon(coupon5);
            long coupon6ID = companyFacade.addCoupon(coupon6);
            coupon1.setId(coupon1ID);
            coupon2.setId(coupon2ID);
            coupon3.setId(coupon3ID);
            coupon4.setId(coupon4ID);
            coupon5.setId(coupon5ID);
            coupon6.setId(coupon6ID);

            companyFacade.getAllCompanyCoupons().forEach(System.out::println);
            t1.start(); // start now after coupons added

            companyFacade.updateCoupon(new Coupon(
                    coupon2ID,
                    company1ID,
                    Category.Restaurant,
                    "Italian bar - updated bar",
                    "best updated pasta",
                    "2023-01-15",
                    "2023-02-25",
                    6565,
                    990,
                    "url"
            ));
            System.out.println("Deleting coupon " + coupon1ID);
            companyFacade.deleteCoupon(coupon1ID);
            companyFacade.getAllCompanyCoupons().forEach(System.out::println);
            System.out.println("Company coupons filtered by category(Electricity) : ");
            companyFacade.getAllCompanyCoupons(Category.Electricity).forEach(System.out::println);
            System.out.println("Company coupons filtered by price(1000) : ");
            companyFacade.getAllCompanyCoupons(1000).forEach(System.out::println);



            // ------ Customer Facade, coupons CRUD -------
            System.out.println("             ------ Customer Facade, coupons CRUD -------\n");

            CustomerFacade customerFacade = (CustomerFacade)loginManager.Login("cow@rules.com", "1234", ClientType.Customer);
            System.out.println("Customer information : ");
            System.out.println(customerFacade.getCustomerDetails());
            //customerFacade.purchaseCoupon(coupon1);// Attempt to buy a deleted coupon
            customerFacade.purchaseCoupon(coupon2);
            customerFacade.purchaseCoupon(coupon3);
            customerFacade.purchaseCoupon(coupon4);

            System.out.println("All customer coupons : ");
            customerFacade.getCustomerCoupons().forEach(System.out::println);
            System.out.println("All customer coupons by category(Vacation) :");
            customerFacade.getCustomerCoupons(Category.Vacation).forEach(System.out::println);
            System.out.println("All customer coupons by max price(300) :");
            customerFacade.getCustomerCoupons(300).forEach(System.out::println);


        }catch (CustomException e){
            System.out.println(e.getMessage());
        }
        catch (Exception ignored){
            System.out.println(ignored.toString());
        }
        finally {
            couponExpirationDailyJob.stop();
            t1.interrupt();

            ConnectionPool.getInstance().closeAllConnections();
        }
    }
}
