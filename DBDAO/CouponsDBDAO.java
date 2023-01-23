package DBDAO;

import Beans.Company;
import Beans.Customer;
import Exceptions.EmptyCouponIDException;
import enums.Category;
import Beans.Coupon;
import DAO.CouponsDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CouponsDBDAO implements CouponsDAO {
    private ConnectionPool connectionPool = ConnectionPool.getInstance();

    @Override
    public long addCoupon(Coupon coupon) {
        Connection connection = connectionPool.getConnection();

        String sql = "insert into coupons values(?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
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
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            connectionPool.restoreConnection(connection);
        }
    }

    @Override
    public void updateCoupon(Coupon coupon) {
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
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            connectionPool.restoreConnection(connection);
        }
    }

    @Override
    public void deleteCoupon(Coupon coupon) {
        Connection connection = connectionPool.getConnection();

        String sql = "delete from coupons where id=?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, String.valueOf(coupon.getId()));

            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            connectionPool.restoreConnection(connection);
        }
    }

    @Override
    public ArrayList<Coupon> getAllCoupons() {
        Connection connection = connectionPool.getConnection();
        ArrayList<Coupon> couponsList = new ArrayList<>();

        String sql = "select * from coupons";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                int id = Integer.parseInt(resultSet.getString(1));
                int companyID = Integer.parseInt(resultSet.getString(2));
                int categoryID = Integer.parseInt(resultSet.getString(3));
                String title = resultSet.getString(4);
                String description = resultSet.getString(5);
                String startDate = resultSet.getString(6);
                String endDate = resultSet.getString(7);
                int amount = Integer.parseInt(resultSet.getString(8));
                double price = Double.parseDouble(resultSet.getString(9));
                String image = resultSet.getString(10);

                couponsList.add(new Coupon(id, companyID, Category.values()[categoryID], title, description,
                        startDate, endDate, amount, price, image));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            connectionPool.restoreConnection(connection);
        }
        return couponsList;
    }

    @Override
    public Coupon getOneCoupon(long couponID) {
        Connection connection = connectionPool.getConnection();

        String sql = "select * from coupons where id=?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, String.valueOf(couponID));
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                int id = Integer.parseInt(resultSet.getString(1));
                int companyID = Integer.parseInt(resultSet.getString(2));
                int categoryID = Integer.parseInt(resultSet.getString(3));
                String title = resultSet.getString(4);
                String description = resultSet.getString(5);
                String startDate = resultSet.getString(6);
                String endDate = resultSet.getString(7);
                int amount = Integer.parseInt(resultSet.getString(8));
                double price = Double.parseDouble(resultSet.getString(9));
                String image = resultSet.getString(10);
                return new Coupon(id, companyID, Category.values()[categoryID], title, description,
                        startDate, endDate, amount, price, image);
            }
        } catch (SQLException e) {
            System.out.println("Error getting one coupon" + e.getMessage());
        }
        finally {
            connectionPool.restoreConnection(connection);
        }
        return null;
    }

    @Override
    public void addCouponPurchase(long customerID, long couponID) {
        Connection connection = connectionPool.getConnection();

        String sql = "insert into customers_vs_coupons values(?,?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, customerID);
            preparedStatement.setLong(2, couponID);

            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            connectionPool.restoreConnection(connection);
        }
    }

    @Override
    public void deleteCouponPurchase(long customerID, long couponID) {
        Connection connection = connectionPool.getConnection();

        String sql = "delete from customers_vs_coupons where customer_id=? and coupon_id=? limit 1";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, String.valueOf(customerID));
            preparedStatement.setString(2, String.valueOf(couponID));

            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            connectionPool.restoreConnection(connection);
        }
    }

    @Override
    public ArrayList<Coupon> getAllCustomersCoupons(long customerID) {
        Connection connection = connectionPool.getConnection();

        ArrayList<Coupon> couponsList = new ArrayList<>();

        String sql = "select * from coupons inner join customers_vs_coupons on id=coupon_id and customer_id=?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, String.valueOf(customerID));

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                int id = Integer.parseInt(resultSet.getString(1));
                int companyID = Integer.parseInt(resultSet.getString(2));
                int categoryID = Integer.parseInt(resultSet.getString(3));
                String title = resultSet.getString(4);
                String description = resultSet.getString(5);
                String startDate = resultSet.getString(6);
                String endDate = resultSet.getString(7);
                int amount = Integer.parseInt(resultSet.getString(8));
                double price = Double.parseDouble(resultSet.getString(9));
                String image = resultSet.getString(10);

                couponsList.add(new Coupon(id, companyID, Category.values()[categoryID], title, description,
                        startDate, endDate, amount, price, image));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            connectionPool.restoreConnection(connection);
        }
        return couponsList;
    }

    @Override
    public void deleteAllCompanyCoupons(Company company) {
        Connection connection = connectionPool.getConnection();

        String sql = "delete from coupons where company_id=?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, company.getId());

            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            connectionPool.restoreConnection(connection);
        }
    }

    @Override
    public void deleteAllCompanyCouponsPurchases(Company company) {
        Connection connection = connectionPool.getConnection();

        String sql = "delete from customers_vs_coupons inner join coupons on coupon_id=id and company_id=?";
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

    @Override
    public void deleteAllCustomerCouponsPurchases(Customer customer) {
        Connection connection = connectionPool.getConnection();

        String sql = "delete from customers_vs_coupons where customer_id=?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, customer.getId());

            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            connectionPool.restoreConnection(connection);
        }
    }
    @Override
    public void deleteAllCouponsPurchases(Coupon coupon) {

    }

    @Override
    public void deleteAllCouponsFromList(List<Long> couponsToDeleteIDsList) {
        //couponsToDeleteIDsList.stream().forEach(item -> deleteCoupon());
    }
}
