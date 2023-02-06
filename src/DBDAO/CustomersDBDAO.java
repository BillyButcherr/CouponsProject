package DBDAO;

import Beans.Coupon;
import Beans.Customer;
import DAO.CustomersDAO;
import enums.Category;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomersDBDAO implements CustomersDAO {
    private ConnectionPool connectionPool = ConnectionPool.getInstance();

    /**
     *
     * @param email The customer email.
     * @param password The customer password.
     * @return If successful the found customer ID, when login fails returns -1.
     * @throws SQLException
     */
    @Override
    public long isCustomerExist(String email, String password) throws SQLException {
        Connection connection = connectionPool.getConnection();

        String sql = "select id from customers where email=? and password=? limit 1";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) return resultSet.getLong(1);
        } finally {
            connectionPool.restoreConnection(connection);
        }
        return -1;
    }

    /**
     * Adds a given customer to DB.
     * @param customer The customer to be added.
     * @return The auto-generated ID for the new customer.
     * @throws SQLException
     */
    @Override
    public long addCustomer(Customer customer) throws SQLException {
        Connection connection = connectionPool.getConnection();

        String sql = "insert into customers(first_name, last_name, email, password) values(?,?,?,?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, customer.getFirstName());
            preparedStatement.setString(2, customer.getLastName());
            preparedStatement.setString(3, customer.getEmail());
            preparedStatement.setString(4, customer.getPassword());

            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            long generatedID = -1;
            if(resultSet.next())
                generatedID = resultSet.getLong(1);
            return generatedID;
        } finally {
            connectionPool.restoreConnection(connection);
        }
    }

    /**
     * Updates a given customer information at DB.
     * @param customer The customer to be updated.
     * @throws SQLException
     */
    @Override
    public void updateCustomer(Customer customer) throws SQLException {
        Connection connection = connectionPool.getConnection();

        String sql = "update customers set first_name=?, last_name=?, email=?, password=? where id=?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, customer.getFirstName());
            preparedStatement.setString(2, customer.getLastName());
            preparedStatement.setString(3, customer.getEmail());
            preparedStatement.setString(4, customer.getPassword());
            preparedStatement.setLong(5, customer.getId());

            preparedStatement.execute();
        } finally {
            connectionPool.restoreConnection(connection);
        }
    }

    /**
     * Deletes a given customer from DB.
     * @param customer The customer to be deleted.
     * @throws SQLException
     */
    @Override
    public void deleteCustomer(Customer customer) throws SQLException{
        Connection connection = connectionPool.getConnection();

        String sql = "delete from customers where id=?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, customer.getId());

            preparedStatement.execute();
        } finally {
            connectionPool.restoreConnection(connection);
        }
    }

    /**
     * Retrieves a list of all customers from DB.
     * @return a list of all customers.
     * @throws SQLException
     */
    @Override
    public ArrayList<Customer> getAllCustomers() throws SQLException{
        Connection connection = connectionPool.getConnection();
        ArrayList<Customer> customersList = new ArrayList<>();

        String sql = "select * from customers";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                long id = resultSet.getLong(1);
                String firstName = resultSet.getString(2);
                String lastName = resultSet.getString(3);
                String email = resultSet.getString(4);
                String password = resultSet.getString(5);
                customersList.add(new Customer(id, firstName, lastName, email, password, null));
            }
        } finally {
            connectionPool.restoreConnection(connection);
        }
        return customersList;
    }

    /**
     * Retrieves one customer from DB.
     * @param customerID The ID of the customer to be fetched.
     * @return The fetched customer information, when fails returns null.
     * @throws SQLException
     */
    @Override
    public Customer getOneCustomer(long customerID) throws SQLException{
        Connection connection = connectionPool.getConnection();

        String sql = "select * from customers where id=?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, customerID);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                long id = resultSet.getLong(1);
                String firstName = resultSet.getString(2);
                String lastName = resultSet.getString(3);
                String email = resultSet.getString(4);
                String password = resultSet.getString(5);
                return new Customer(id, firstName, lastName, email, password, null);
            }
        } finally {
            connectionPool.restoreConnection(connection);
        }
        return null;
    }
}
