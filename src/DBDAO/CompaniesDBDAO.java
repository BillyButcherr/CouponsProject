package DBDAO;

import Beans.Company;
import Beans.Coupon;
import DAO.CompaniesDAO;
import Exceptions.CouponDateAheadOfTimeException;
import Exceptions.EmptyCompanyIDException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CompaniesDBDAO implements CompaniesDAO {
    private ConnectionPool connectionPool = ConnectionPool.getInstance();

    /**
     * Checks if a company given email and password are correct - used for login.
     * @param email The company email.
     * @param password The company password.
     * @return If login successful returns the company ID, else returns -1;
     * @throws SQLException
     */
    @Override//Works
    public long isCompanyExist(String email, String password) throws SQLException{
        Connection connection = connectionPool.getConnection();

        String sql = "select id from companies where email=? and password=? limit 1";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) return resultSet.getLong(1);
        }
        finally {
            connectionPool.restoreConnection(connection);
        }
        return -1;
    }

    /**
     * Adds a new company to the DB.
     * @param company The company to be added - no ID field required.
     * @return The auto-generated ID for the added company.
     * @throws SQLException
     */
    @Override //Works
    public long addCompany(Company company) throws SQLException {
        Connection connection = connectionPool.getConnection();

        String sql = "insert into companies(name, email, password) values(?,?,?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, company.getName());
            preparedStatement.setString(2, company.getEmail());
            preparedStatement.setString(3, company.getPassword());

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
     * Updates a given company information.
     * @param company The company to be updated.
     * @throws SQLException
     * @throws EmptyCompanyIDException The ID field inside company isn't initialized.
     */
    @Override //Works
    public void updateCompany(Company company) throws SQLException, EmptyCompanyIDException{
        Connection connection = connectionPool.getConnection();
        if (company.getId() == 0)
            throw new EmptyCompanyIDException();
        String sql = "update companies set name=?, email=?, password=? where id=?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, company.getName());
            preparedStatement.setString(2, company.getEmail());
            preparedStatement.setString(3, company.getPassword());
            preparedStatement.setLong(4, company.getId());

            preparedStatement.execute();
        } finally {
            connectionPool.restoreConnection(connection);
        }
    }

    /**
     * Deletes a given company from the DB.
     * @param company The company to be deleted.
     * @throws SQLException
     */
    @Override//Works
    public void deleteCompany(Company company) throws SQLException{
        Connection connection = connectionPool.getConnection();

        String sql = "delete from companies where id=?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, String.valueOf(company.getId()));

            preparedStatement.execute();
        } finally {
            connectionPool.restoreConnection(connection);
        }
    }

    /**
     *
     * @return List of all companies from DB.
     * @throws SQLException
     */

    @Override//Works
    public ArrayList<Company> getAllCompanies() throws SQLException{
        Connection connection = connectionPool.getConnection();
        ArrayList<Company> companyList = new ArrayList<>();

        String sql = "select * from companies";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                String id = resultSet.getString(1);
                String name = resultSet.getString(2);
                String email = resultSet.getString(3);
                String password = resultSet.getString(4);
                companyList.add(new Company(Integer.parseInt(id), name, email, password, null));
            }
        } finally {
            connectionPool.restoreConnection(connection);
        }
        return companyList;
    }

    /**
     *
     * @param companyID The ID of the company to be fetched.
     * @return The fetched company.
     * @throws SQLException
     */
    @Override//Works
    public Company getOneCompany(long companyID) throws SQLException{
        Connection connection = connectionPool.getConnection();
        
        String sql = "select * from companies where id=?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, companyID);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                String name = resultSet.getString(2);
                String email = resultSet.getString(3);
                String password = resultSet.getString(4);
                return new Company(companyID, name, email, password, null);
            }
        } finally {
            connectionPool.restoreConnection(connection);
        }
        return null;
    }

    /**
     * Deletes all previous records from DB, before the test begins.
     * @throws SQLException
     */
    public void deleteAllRecordsBeforeTest() throws SQLException{
        Connection connection = connectionPool.getConnection();

        try {
            String sql = "delete from companies";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.execute();

            sql = "delete from coupons";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.execute();

            sql = "delete from customers";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.execute();

            sql = "delete from customers_vs_coupons";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.execute();

        } finally {
            connectionPool.restoreConnection(connection);
        }
    }
}
