package DAO;

import Beans.Category;
import Beans.Coupon;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

public interface CouponsDAO {
    void addCoupon(Coupon coupon) throws SQLException;
    void updateCoupon(Coupon coupon) throws SQLException;
    void deleteCoupon(int couponID);
    ArrayList<Coupon> getAllCoupons();
    Coupon getOneCoupon(int couponID);
    void addCouponPurchase(int customerID,int couponID) throws SQLException;
    ArrayList<Coupon> getCompanyCouponsById(int companyID);
    ArrayList<Coupon> getCompanyCouponsByIdAndCat(int companyID, Category category);
    ArrayList<Coupon> getCompanyCouponsByIdAndMax(int companyID,double maxPrice);
    boolean isInStock (Coupon coupon);
    void reduceOne(Coupon coupon) throws SQLException;
    void deleteExpiredCoupons(Date date);
    boolean isCouponExists(int couponID);
}

