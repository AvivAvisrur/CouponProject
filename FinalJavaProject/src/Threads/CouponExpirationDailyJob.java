package Threads;


import DAO.CouponsDAO;
import DBDao.CouponsDBDAO;



import java.util.TimerTask;

/**
 * this is thread class that execute an operate behind the scene for the project.
 * using TimerTask for cron job to make it work in specific day and time.
 * this class has 2 attributes of Arraylist and couponsDao because we operate on the
 * coupons and delete all the coupons that their dates has passed.
 */
public class CouponExpirationDailyJob extends TimerTask {
    CouponsDAO cdao;

    /**
     * This is constructor with no arguments. gives us instance of coupons dbdao for using methods.
     * and insert all the coupons from the sql into the list .
     */
    public CouponExpirationDailyJob() {
        cdao = new CouponsDBDAO();
    }

    /**
     * this method run is for running the thread behind the no arguments.
     * inside the method we have while loop that run while the term is true
     * and checking the date of the coupons and if the date passed the method delete it from the sql.
     */
    @Override
    public void run() {
        System.out.println("Thread Running");
        cdao.deleteExpiredCoupons(new java.sql.Date(System.currentTimeMillis()));

    }
}

