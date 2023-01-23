package DBDAO;

import Beans.Company;
import DAO.CompaniesDAO;
import Exceptions.EmptyCompanyIDException;

import java.sql.*;
import java.util.ArrayList;

public class CompaniesDBDAO implements CompaniesDAO {
    private ConnectionPool connectionPool = ConnectionPool.getInstance();

    @Override//Works
    public long isCompanyExist(String email, String password) {
        Connection connection = connectionPool.getConnection();

        String sql = "select id from companies where email=? and password=? limit 1";
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

    @Override //Works
    public long addCompany(Company company) {
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
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            connectionPool.restoreConnection(connection);
        }
    }

    @Override //Works
    public void updateCompany(Company company) throws EmptyCompanyIDException{
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
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            connectionPool.restoreConnection(connection);
        }
    }

    @Override//Works
    public void deleteCompany(Company company) {
        Connection connection = connectionPool.getConnection();

        String sql = "delete from companies where id=?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, String.valueOf(company.getId()));

            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            connectionPool.restoreConnection(connection);
        }
    }

    @Override//Works
    public ArrayList<Company> getAllCompanies() {
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
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            connectionPool.restoreConnection(connection);
        }
        return companyList;
    }

    @Override//Works
    public Company getOneCompany(long companyID) {
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
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            connectionPool.restoreConnection(connection);
        }
        return null;
    }
}
