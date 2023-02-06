import Beans.Coupon;
import DAO.CouponsDAO;
import DBDAO.CouponsDBDAO;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

public class CouponExpirationDailyJob implements Runnable {
    private final CouponsDAO couponsDAO = new CouponsDBDAO();
    private volatile boolean quit = false;

    /**
     *
     */
    @Override
    public void run() {
        while (!quit){
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
                        couponsDAO.deleteAllCouponPurchases(coupon);
                    }
                }
            }
            catch (SQLException ignored){}
            System.out.println("Daily coupons expiration job done.");
            try {
                //Thread.sleep(24 * 60 * 60 * 1000);
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {}
        }
    }

    /**
     * <p>Stops current running thread.</p>
     */
    public void stop(){
        quit = true;
    }
}
