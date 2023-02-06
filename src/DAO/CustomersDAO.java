package DAO;

import Beans.Customer;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CustomersDAO {
    /**
     *
     * @param email The customer email.
     * @param password The customer password.
     * @return If successful the found customer ID, when login fails returns -1.
     * @throws SQLException
     */
    long isCustomerExist(String email, String password) throws SQLException;
    /**
     * Adds a given customer to DB.
     * @param customer The customer to be added.
     * @return The auto-generated ID for the new customer.
     * @throws SQLException
     */
    long addCustomer(Customer customer) throws SQLException;
    /**
     * Updates a given customer information at DB.
     * @param customer The customer to be updated.
     * @throws SQLException
     */
    void updateCustomer(Customer customer) throws SQLException;
    /**
     * Deletes a given customer from DB.
     * @param customer The customer to be deleted.
     * @throws SQLException
     */
    void deleteCustomer(Customer customer) throws SQLException;
    /**
     * Retrieves a list of all customers from DB.
     * @return a list of all customers.
     * @throws SQLException
     */
    ArrayList<Customer> getAllCustomers() throws SQLException;
    /**
     * Retrieves one customer from DB.
     * @param customerID The ID of the customer to be fetched.
     * @return The fetched customer information, when fails returns null.
     * @throws SQLException
     */
    Customer getOneCustomer(long customerID) throws SQLException;
}
