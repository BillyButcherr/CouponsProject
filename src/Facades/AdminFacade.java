package Facades;

import Beans.Company;
import Beans.Coupon;
import Beans.Customer;
import Exceptions.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AdminFacade extends ClientFacade{
    /**
     * Authenticate admin login credentials.
     * @param email The admin email.
     * @param password The admin password.
     * @return true when successful, when login fails returns false.
     */
    @Override
    public boolean login(String email, String password) {
        return email.equals("admin@admin.com") && password.equals("admin");
    }

    /**
     * Adds a new company.
     * @param company The company to be added.
     * @return The auto-generated ID for the new company.
     * @throws SQLException
     * @throws CompanyAlreadyExistsException A Company with the same name or email already exists.
     */
    //Works
    public long addCompany(Company company) throws SQLException, CompanyAlreadyExistsException {
        Company companyExists = companiesDAO.getAllCompanies().stream()
                .filter(company1 -> company1.getName().equals(company.getName()) || company1.getEmail().equals(company.getEmail()))
                .findFirst()
                .orElse(null);
        if (companyExists != null) throw new CompanyAlreadyExistsException();
        return companiesDAO.addCompany(company);
    }

    /**
     * Updates information of an existing company.
     * @param company The company to be updated.
     * @throws SQLException
     * @throws CompanyNameChangeNotAllowedException Illegal attempt to change existing company name.
     * @throws EmptyCompanyIDException The company ID field isn't initialized.
     */
    //Works
    public void updateCompany(Company company) throws SQLException, CompanyNameChangeNotAllowedException, EmptyCompanyIDException {
        if (company.getId() == 0)
            throw new EmptyCompanyIDException();
        Company oldRecord = companiesDAO.getOneCompany(company.getId());
        if(!oldRecord.getName().equals(company.getName()))
            throw new CompanyNameChangeNotAllowedException();
        else companiesDAO.updateCompany(company);
    }

    /**
     * Deletes a given company.
     * @param companyID The ID of a company to be deleted.
     * @throws SQLException SQL error
     * @throws EmptyCompanyIDException The company ID field isn't initialized.
     * @throws CompanyDoesNotExistException A company with the provided ID does not exist.
     */
    public void deleteCompany(long companyID) throws SQLException, EmptyCompanyIDException, CompanyDoesNotExistException {
        if(companyID == 0)
            throw new EmptyCompanyIDException();
        if(companiesDAO.getOneCompany(companyID) == null)
            throw new CompanyDoesNotExistException();
        couponsDAO.deleteAllCompanyCouponsPurchases(companyID);
        couponsDAO.deleteAllCompanyCoupons(companyID);
        companiesDAO.deleteCompany(companyID);
    }

    /**
     * <p>
     *     finds and associates every coupon and their parent company.
     *     <ul>
     *         <li>finds all companies</li>
     *         <li>finds all coupons</li>
     *         <li>associates each coupon to their parent company</li>
     *     </ul>
     * </p>
     *
     * @return A list of all companies with their coupons list.
     * @throws SQLException
     */
    //Works
    public ArrayList<Company> getAllCompanies() throws SQLException {
        List<Coupon> allCoupons = couponsDAO.getAllCoupons();
        ArrayList<Company> allCompanies = companiesDAO.getAllCompanies();
        for (Company company:allCompanies) {
            List<Coupon> companyCoupons = allCoupons.stream()
                    .filter(coupon -> coupon.getCompanyID() == company.getId())
                    .collect(Collectors.toList());
            company.setCoupons(companyCoupons);
        }
        return allCompanies;
    }

    /**
     * gets a single company with its coupons list.
     * @param companyID The ID of the requested company.
     * @return The company information.
     * @throws SQLException
     * @throws EmptyCompanyIDException The company ID field isn't initialized.
     * @throws CompanyDoesNotExistException A company with the given ID does not exist.
     */
    //Works
    public Company getOneCompany(long companyID) throws SQLException, EmptyCompanyIDException, CompanyDoesNotExistException {
        if(companyID == 0)
            throw new EmptyCompanyIDException();
        Company company = companiesDAO.getOneCompany(companyID);
        if(company == null)
            throw new CompanyDoesNotExistException();
        List<Coupon> companyCoupons = couponsDAO.getAllCoupons().stream()
                .filter(coupon -> coupon.getCompanyID() == companyID)
                .collect(Collectors.toList());
        company.setCoupons(companyCoupons);
        return company;
    }

    /**
     * Adds a new customer.
     * @param customer The customer to be added.
     * @return The auto-generated ID of the added customer.
     * @throws SQLException
     * @throws CustomerAlreadyExistsException A customer with the same email already exists.
     */
    //Works
    public long addCustomer(Customer customer) throws SQLException, CustomerAlreadyExistsException {
        Customer customerExists = customersDAO.getAllCustomers().stream()
                .filter(customer1 -> customer1.getEmail().equals(customer.getEmail()))
                .findFirst()
                .orElse(null);
        if(customerExists != null)
            throw new CustomerAlreadyExistsException();
        return customersDAO.addCustomer(customer);
    }

    /**
     * Updates a given customer information.
     * @param customer The customer to be updated.
     * @throws SQLException
     * @throws EmptyCustomerIDException The customer ID field isn't initialized.
     * @throws CustomerDoesNotExistException A customer with that ID does not exist.
     * @throws CustomerAlreadyExistsException A customer with the same email already exists.
     */
    //Works
    public void updateCustomer(Customer customer) throws SQLException, EmptyCustomerIDException, CustomerDoesNotExistException, CustomerAlreadyExistsException {
        if (customer.getId() == 0)
            throw new EmptyCustomerIDException();
        if(customersDAO.getOneCustomer(customer.getId()) == null)
            throw new CustomerDoesNotExistException();
        Customer otherCustomerWithSameEmailExists = customersDAO.getAllCustomers().stream()
                .filter(otherCustomer -> otherCustomer.getId() != customer.getId() && otherCustomer.getEmail().equals(customer.getEmail()))
                .findFirst()
                .orElse(null);
        if(otherCustomerWithSameEmailExists != null)
            throw new CustomerAlreadyExistsException();
        customersDAO.updateCustomer(customer);
    }

    /**
     * Deletes a given customer.
     * @param customerID The ID of the customer to be deleted.
     * @throws SQLException
     * @throws EmptyCustomerIDException The customer ID field isn't initialized.
     * @throws CustomerDoesNotExistException A customer with that ID does not exist.
     */
    public void deleteCustomer(long customerID) throws SQLException, EmptyCustomerIDException, CustomerDoesNotExistException {
        if (customerID == 0)
            throw new EmptyCustomerIDException();
        if(customersDAO.getOneCustomer(customerID) == null)
            throw new CustomerDoesNotExistException();
        couponsDAO.deleteAllCustomerCouponsPurchases(customerID);
        customersDAO.deleteCustomer(customerID);
    }

    /**
     *
     * @return A list of all customers.
     * @throws SQLException
     */
    //Works
    public ArrayList<Customer> getAllCustomers() throws SQLException{
        ArrayList<Customer> allCustomers = customersDAO.getAllCustomers();
        for (Customer customer:allCustomers) {
            customer.setCoupons(couponsDAO.getAllCustomerCoupons(customer.getId()));
        }
        return allCustomers;
    }

    /**
     *
     * @param customerID
     * @return One customer with his coupons list.
     * @throws SQLException
     * @throws EmptyCustomerIDException The customer ID field isn't initialized.
     * @throws CustomerDoesNotExistException A customer with that ID does not exist.
     */
    //Works
    public Customer getOneCustomer(long customerID) throws SQLException, EmptyCustomerIDException, CustomerDoesNotExistException {
        if (customerID == 0)
            throw new EmptyCustomerIDException();
        Customer customer = customersDAO.getOneCustomer(customerID);
        if(customer == null)
            throw new CustomerDoesNotExistException();
        customer.setCoupons(couponsDAO.getAllCustomerCoupons(customerID));
        return customer;
    }
}