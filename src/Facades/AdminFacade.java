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
    @Override
    public boolean login(String email, String password) {
        return email.equals("admin@admin.com") && password.equals("admin");
    }

    //Works
    public long addCompany(Company company) throws SQLException, CompanyAlreadyExistsException {
        Company companyExists = companiesDAO.getAllCompanies().stream()
                .filter(company1 -> company1.getName().equals(company.getName()) || company1.getEmail().equals(company.getEmail()))
                .findFirst()
                .orElse(null);
        if (companyExists != null) throw new CompanyAlreadyExistsException();
        return companiesDAO.addCompany(company);
    }
    //Works
    public void updateCompany(Company company) throws SQLException, CompanyNameChangeNotAllowedException, EmptyCompanyIDException {
        if (company.getId() == 0)
            throw new EmptyCompanyIDException();
        Company oldRecord = companiesDAO.getOneCompany(company.getId());
        System.out.println(oldRecord);
        if(!oldRecord.getName().equals(company.getName()))
            throw new CompanyNameChangeNotAllowedException();
        else companiesDAO.updateCompany(company);
    }


    public void deleteCompany(Company company) throws SQLException, EmptyCompanyIDException, CompanyDoesNotExistException {
        if(company.getId() == 0)
            throw new EmptyCompanyIDException();
        if(companiesDAO.getOneCompany(company.getId()) == null)
            throw new CompanyDoesNotExistException();
        couponsDAO.deleteAllCompanyCouponsPurchases(company);
        couponsDAO.deleteAllCompanyCoupons(company);
        companiesDAO.deleteCompany(company);
    }

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


    public void deleteCustomer(Customer customer) throws SQLException, EmptyCustomerIDException, CustomerDoesNotExistException {
        if (customer.getId() == 0)
            throw new EmptyCustomerIDException();
        if(customersDAO.getOneCustomer(customer.getId()) == null)
            throw new CustomerDoesNotExistException();
        couponsDAO.deleteAllCustomerCouponsPurchases(customer);
        customersDAO.deleteCustomer(customer);
    }
    //Works
    public ArrayList<Customer> getAllCustomers() throws SQLException{
        ArrayList<Customer> allCustomers = customersDAO.getAllCustomers();
        for (Customer customer:allCustomers) {
            customer.setCoupons(couponsDAO.getAllCustomersCoupons(customer.getId()));
        }
        return allCustomers;
    }
    //Works
    public Customer getOneCustomer(long customerID) throws SQLException, EmptyCustomerIDException, CustomerDoesNotExistException {
        if (customerID == 0)
            throw new EmptyCustomerIDException();
        Customer customer = customersDAO.getOneCustomer(customerID);
        if(customer == null)
            throw new CustomerDoesNotExistException();
        customer.setCoupons(couponsDAO.getAllCustomersCoupons(customerID));
        return customer;
    }
}