import Beans.Coupon;
import DAO.CouponsDAO;
import DBDAO.CouponsDBDAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CouponExpirationDailyJob implements Runnable {
    private CouponsDAO couponsDAO = new CouponsDBDAO();
    private boolean quit;

    @Override
    public void run() {
        while (true){
            Calendar today = Calendar.getInstance();
            try {
                List<Coupon> allCoupons = couponsDAO.getAllCoupons();
                if (allCoupons.size() > 0) {
                    List<Coupon> couponsToDelete = allCoupons.stream()
                            .filter(coupon -> today.getTime().after(coupon.getEndDate()))
                            //.map(Coupon ::getId)
                            .collect(Collectors.toList());
                    //.forEach(item -> co);
                    for (Coupon coupon : couponsToDelete) {
                        couponsDAO.deleteCoupon(coupon);
                    }
                }
            }
            catch (SQLException e){System.out.println(e.getMessage());}
            System.out.println("Daily job Loop done.");
            try {
                Thread.sleep(24 * 60 * 60 * 1000);
                //Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
