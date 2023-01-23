package DAO;

import Beans.Customer;

import java.util.ArrayList;

public interface CustomersDAO {
    long isCustomerExist(String email, String password);
    long addCustomer(Customer customer);
    void updateCustomer(Customer customer);
    void deleteCustomer(Customer customer);
    ArrayList<Customer> getAllCustomers();
    Customer getOneCustomer(long customerID);
}
