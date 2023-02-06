package DBDAO;

import Beans.Company;
import Beans.Customer;
import enums.Category;
import Beans.Coupon;
import DAO.CouponsDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CouponsDBDAO implements CouponsDAO {
    private ConnectionPool connectionPool = ConnectionPool.getInstance();

    /**
     * Adds a given coupon to the DB.
     * @param coupon The coupon to be added.
     * @return The auto-generated ID for the new coupon.
     * @throws SQLException
     */
    @Override
    public long addCoupon(Coupon coupon) throws SQLException{
        Connection connection = connectionPool.getConnection();

        String sql = "insert into coupons(company_id, category_id, title, description, start_date, end_date," +
                "amount, price, image) values(?,?,?,?,?,?,?,?,?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, coupon.getCompanyID());
            preparedStatement.setInt(2, coupon.getCategory().ordinal());
            preparedStatement.setString(3, coupon.getTitle());
            preparedStatement.setString(4, coupon.getDescription());
            preparedStatement.setString(5, coupon.getSimpleStartDate());
            preparedStatement.setString(6, coupon.getSimpleEndDate());
            preparedStatement.setInt(7, coupon.getAmount());
            preparedStatement.setDouble(8, coupon.getPrice());
            preparedStatement.setString(9, coupon.getImage());

            preparedStatement.execute();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            long generatedID = -1;
            if(resultSet.next())
                generatedID = resultSet.getLong(1);
            return generatedID;
        }
        finally {
            connectionPool.restoreConnection(connection);
        }
    }

    /**
     * Updates a given coupon at the DB.
     * @param coupon The coupon to be updated.
     * @throws SQLException
     */
    @Override
    public void updateCoupon(Coupon coupon) throws SQLException {
        Connection connection = connectionPool.getConnection();
        String sql = "update coupons set company_id=?, category_id=?, title=?, description=?, start_date=?," +
                "end_date=?, amount=?, price=?, image=? where id=?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, coupon.getCompanyID());
            preparedStatement.setString(2, String.valueOf(coupon.getCategory().ordinal()));
            preparedStatement.setString(3, coupon.getTitle());
            preparedStatement.setString(4, coupon.getDescription());
            preparedStatement.setString(5, coupon.getSimpleStartDate());
            preparedStatement.setString(6, coupon.getSimpleEndDate());
            preparedStatement.setInt(7, coupon.getAmount());
            preparedStatement.setDouble(8, coupon.getPrice());
            preparedStatement.setString(9, coupon.getImage());

            preparedStatement.setLong(10, coupon.getId());

            preparedStatement.execute();
        } finally {
            connectionPool.restoreConnection(connection);
        }
    }

    /**
     * Deletes a given coupon from DB.
     * @param coupon The coupon to be deleted.
     * @throws SQLException
     */
    @Override
    public void deleteCoupon(Coupon coupon) throws SQLException {
        Connection connection = connectionPool.getConnection();

        String sql = "delete from coupons where id=?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, coupon.getId());

            preparedStatement.execute();
        } finally {
            connectionPool.restoreConnection(connection);
        }
    }

    /**
     * Gets all Coupons from DB.
     * @return An ArrayList of all fetched coupons.
     * @throws SQLException
     */
    @Override
    public ArrayList<Coupon> getAllCoupons() throws SQLException{
        Connection connection = connectionPool.getConnection();
        ArrayList<Coupon> couponsList = new ArrayList<>();

        String sql = "select * from coupons";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                long id = resultSet.getLong(1);
                long companyID = resultSet.getLong(2);
                int categoryID = resultSet.getInt(3);
                String title = resultSet.getString(4);
                String description = resultSet.getString(5);
                String startDate = resultSet.getString(6);
                String endDate = resultSet.getString(7);
                int amount = resultSet.getInt(8);
                double price = resultSet.getDouble(9);
                String image = resultSet.getString(10);

                couponsList.add(new Coupon(id, companyID, Category.values()[categoryID], title, description,
                        startDate, endDate, amount, price, image));
            }
        } finally {
            connectionPool.restoreConnection(connection);
        }
        return couponsList;
    }

    /**
     *
     * @param couponID The requested Coupon ID.
     * @return The requested coupon information.
     * @throws SQLException
     */
    @Override
    public Coupon getOneCoupon(long couponID) throws SQLException{
        Connection connection = connectionPool.getConnection();

        String sql = "select * from coupons where id=?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, couponID);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                long id = resultSet.getLong(1);
                long companyID = resultSet.getLong(2);
                int categoryID = resultSet.getInt(3);
                String title = resultSet.getString(4);
                String description = resultSet.getString(5);
                String startDate = resultSet.getString(6);
                String endDate = resultSet.getString(7);
                int amount = resultSet.getInt(8);
                double price = resultSet.getDouble(9);
                String image = resultSet.getString(10);
                return new Coupon(id, companyID, Category.values()[categoryID], title, description,
                        startDate, endDate, amount, price, image);
            }
        } finally {
            connectionPool.restoreConnection(connection);
        }
        return null;
    }

    /**
     * Adds a coupon purchase to the DB.
     * @param customerID The ID of the customer who purchased the coupon.
     * @param couponID The ID of the coupon that was purchase by the customer.
     * @throws SQLException
     */
    @Override
    public void addCouponPurchase(long customerID, long couponID) throws SQLException {
        Connection connection = connectionPool.getConnection();

        String sql = "insert into customers_vs_coupons values(?,?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, customerID);
            preparedStatement.setLong(2, couponID);

            preparedStatement.execute();
        } finally {
            connectionPool.restoreConnection(connection);
        }
    }

    /**
     * Deletes A coupon purchase from DB.
     * @param customerID The ID of the customer who purchased the coupon.
     * @param couponID The ID of the coupon that was purchase by the customer.
     * @throws SQLException
     */
    @Override
    public void deleteCouponPurchase(long customerID, long couponID) throws SQLException{
        Connection connection = connectionPool.getConnection();

        String sql = "delete from customers_vs_coupons where customer_id=? and coupon_id=? limit 1";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, customerID);
            preparedStatement.setLong(2, couponID);

            preparedStatement.execute();
        } finally {
            connectionPool.restoreConnection(connection);
        }
    }

    /**
     * Gets all bought coupons of a given customer from DB.
     * @param customerID The ID of the customer who bought the coupons.
     * @return A list of all fetched coupons that belong to the customer.
     * @throws SQLException
     */
    @Override
    public ArrayList<Coupon> getAllCustomerCoupons(long customerID) throws SQLException {
        Connection connection = connectionPool.getConnection();

        ArrayList<Coupon> couponsList = new ArrayList<>();

        String sql = "select * from coupons inner join customers_vs_coupons on id=coupon_id and customer_id=?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, String.valueOf(customerID));

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                int id = resultSet.getInt(1);
                int companyID = resultSet.getInt(2);
                int categoryID = resultSet.getInt(3);
                String title = resultSet.getString(4);
                String description = resultSet.getString(5);
                String startDate = resultSet.getString(6);
                String endDate = resultSet.getString(7);
                int amount = resultSet.getInt(8);
                double price = resultSet.getDouble(9);
                String image = resultSet.getString(10);

                couponsList.add(new Coupon(id, companyID, Category.values()[categoryID], title, description,
                        startDate, endDate, amount, price, image));
            }
        } finally {
            connectionPool.restoreConnection(connection);
        }
        return couponsList;
    }


    /**
     * Deletes all coupons of a given company from DB.
     * @param company The company of the coupons.
     * @throws SQLException
     */
    @Override
    public void deleteAllCompanyCoupons(Company company) throws SQLException {
        Connection connection = connectionPool.getConnection();

        String sql = "delete from coupons where company_id=?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, company.getId());

            preparedStatement.execute();
        } finally {
            connectionPool.restoreConnection(connection);
        }
    }

    /**
     *
     * @param companyID The ID of the company the coupons belong to.
     * @return A list of all the fetched coupons.
     * @throws SQLException
     */
    public ArrayList<Coupon> getAllCompanyCoupons(long companyID) throws SQLException {
        Connection connection = connectionPool.getConnection();

        String sql = "select * from coupons where company_id=?";
        ArrayList<Coupon> couponsList = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, companyID);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                long id = resultSet.getLong(1);
                //companyID = resultSet.getLong(2);
                int categoryID = resultSet.getInt(3);
                String title = resultSet.getString(4);
                String description = resultSet.getString(5);
                String startDate = resultSet.getString(6);
                String endDate = resultSet.getString(7);
                int amount = resultSet.getInt(8);
                double price = resultSet.getDouble(9);
                String image = resultSet.getString(10);

                couponsList.add(new Coupon(id, companyID, Category.values()[categoryID], title, description,
                        startDate, endDate, amount, price, image));
            }
        } finally {
            connectionPool.restoreConnection(connection);
        }
        return couponsList;
    }

    /**
     * Deletes all coupons purchases of a given company from DB.
     * @param companyID The ID of the company.
     * @throws SQLException
     */
    @Override
    public void deleteAllCompanyCouponsPurchases(long companyID) throws SQLException {
        Connection connection = connectionPool.getConnection();

        String sql = "delete customers_vs_coupons from customers_vs_coupons inner join coupons on coupon_id=id where company_id=?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, companyID);

            preparedStatement.execute();
        } finally {
            connectionPool.restoreConnection(connection);
        }
    }

    /**
     * Deletes all coupons purchases of a given customer from DB.
     * @param customer The Customer.
     * @throws SQLException
     */
    @Override
    public void deleteAllCustomerCouponsPurchases(Customer customer) throws SQLException {
        Connection connection = connectionPool.getConnection();

        String sql = "delete from customers_vs_coupons where customer_id=?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, customer.getId());

            preparedStatement.execute();
        } finally {
            connectionPool.restoreConnection(connection);
        }
    }

    public String getCategoryNameByID(int categoryID) throws SQLException{
        Connection connection = connectionPool.getConnection();

        String sql = "select name from categories where id=?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, categoryID);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next())
                return resultSet.getString(1);
        } finally {
            connectionPool.restoreConnection(connection);
        }
        return null;
    }
    private ResultSet getAllCategoriesResultSet() throws SQLException{
        Connection connection = connectionPool.getConnection();
        Map<Integer, String> categoriesToNamesMap = new HashMap<>();

        String sql = "select * from categories";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet;
        } finally {
            connectionPool.restoreConnection(connection);
        }
    }
    public Map<Integer, String> getAllCategoriesMappedByID() throws SQLException{
        Map<Integer, String> categoriesToNamesMap = new HashMap<>();
        ResultSet resultSet = getAllCategoriesResultSet();
        while(resultSet.next()){
            categoriesToNamesMap.put(resultSet.getInt(1), resultSet.getString(2));
        }
        return categoriesToNamesMap;
    }
    public Map<String, Integer> getAllCategoriesMappedByName() throws SQLException{
        Map<String, Integer> categoriesToNamesMap = new HashMap<>();
        ResultSet resultSet = getAllCategoriesResultSet();
        while(resultSet.next()){
            categoriesToNamesMap.put(resultSet.getString(2), resultSet.getInt(1));
        }
        return categoriesToNamesMap;
    }
    /**
     * Deletes all purchases of a given coupon from DB.
     * @param coupon The coupon whose purchases are to be deleted.
     * @throws SQLException
     */
    @Override
    public void deleteAllCouponPurchases(Coupon coupon) throws SQLException{
        Connection connection = connectionPool.getConnection();

        String sql = "delete from customers_vs_coupons where coupon_id=?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, coupon.getId());

            preparedStatement.execute();
        } finally { connectionPool.restoreConnection(connection); }
    }

}
