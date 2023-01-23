import Beans.Coupon;
import DAO.CouponsDAO;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CouponExpirationDailyJob implements Runnable {
    private CouponsDAO couponsDAO;
    private boolean quit;

    @Override
    public void run() {
        Calendar today = Calendar.getInstance();
        List<Long> couponsToDelete = couponsDAO.getAllCoupons().stream()
                .filter(coupon -> today.getTime().after(coupon.getEndDate()))
                .map(Coupon ::getId)
                .collect(Collectors.toList());
        couponsDAO.deleteAllCouponsFromList(couponsToDelete);
        try {
            wait(24 * 60 * 60 * 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    public void stop(){

    }
}
