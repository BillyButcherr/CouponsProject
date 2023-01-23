package DBDAO;

import Beans.Customer;
import DAO.CustomersDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomersDBDAO implements CustomersDAO {
    private ConnectionPool connectionPool = ConnectionPool.getInstance();

    @Override
    public long isCustomerExist(String email, String password) {
        Connection connection = connectionPool.getConnection();

        String sql = "select id from customers where email=? and password=? limit 1";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) return resultSet.getLong(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            connectionPool.restoreConnection(connection);
        }
        return -1;
    }

    @Override
    public long addCustomer(Customer customer) {
        Connection connection = connectionPool.getConnection();

        String sql = "insert into customers(first_name, last_name, email, password) values(?,?,?,?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
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
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            connectionPool.restoreConnection(connection);
        }
    }

    @Override
    public void updateCustomer(Customer customer) {
        Connection connection = connectionPool.getConnection();

        String sql = "update customers set first_name=?, last_name=?, email=?, password=? where id=?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, customer.getFirstName());
            preparedStatement.setString(2, customer.getLastName());
            preparedStatement.setString(3, customer.getEmail());
            preparedStatement.setString(4, customer.getPassword());
            preparedStatement.setString(5, String.valueOf(customer.getId()));

            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            connectionPool.restoreConnection(connection);
        }
    }

    @Override
    public void deleteCustomer(Customer customer) {
        Connection connection = connectionPool.getConnection();

        String sql = "delete from customers where id=?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, String.valueOf(customer.getId()));

            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            connectionPool.restoreConnection(connection);
        }
    }

    @Override
    public ArrayList<Customer> getAllCustomers() {
        Connection connection = connectionPool.getConnection();
        ArrayList<Customer> customersList = new ArrayList<>();

        String sql = "select * from customers";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                String id = resultSet.getString(1);
                String firstName = resultSet.getString(2);
                String lastName = resultSet.getString(3);
                String email = resultSet.getString(4);
                String password = resultSet.getString(5);
                customersList.add(new Customer(Integer.parseInt(id), firstName, lastName, email, password, null));
            }
        } catch (SQLException e) {
            System.out.println("Error getting all customers" + e.getMessage());
        }
        finally {
            connectionPool.restoreConnection(connection);
        }
        return customersList;
    }

    @Override
    public Customer getOneCustomer(long customerID) {
        Connection connection = connectionPool.getConnection();

        String sql = "select * from customers where id=?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, String.valueOf(customerID));
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                String id = resultSet.getString(1);
                String firstName = resultSet.getString(2);
                String lastName = resultSet.getString(3);
                String email = resultSet.getString(4);
                String password = resultSet.getString(5);
                return new Customer(Integer.parseInt(id), firstName, lastName, email, password, null);
            }
        } catch (SQLException e) {
            System.out.println("Error getting one customer" + e.getMessage());
        }
        finally {
            connectionPool.restoreConnection(connection);
        }
        return null;
    }
}
