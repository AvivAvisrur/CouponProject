package Test;

import Beans.Category;
import Beans.Coupon;
import DBDao.CouponsDBDAO;

import Exceptions.CustomUserException;

import Facade.CustomerFacade;
import Login.ClientType;
import Login.LoginManager;

import java.util.ArrayList;

public class TestCustomer {
    public static CustomerFacade cf;

    public static void main(String[] args) {
        //login to the system
        cf = (CustomerFacade) LoginManager.getInstance().login("Ayelets@gov.il", "hasamba123", ClientType.CUSTOMER);
        //purchase coupon
        purchaseCoupon(getTesterCoupon());
        System.out.println("=====================");
        //get all coupons
        System.out.println(getCustomerCoupons());
        System.out.println("=====================");
        //get all coupons in range of price
        System.out.println(getCustomerCoupon(500));
        System.out.println("=====================");
        //get all coupons in the same category
        System.out.println(getCustomerCoupon(Category.Electricity));
    }

    public static Coupon getTesterCoupon() {
        Coupon coupon = null;
        CouponsDBDAO cdao = new CouponsDBDAO();
        try {
            coupon = cdao.getOneCoupon(1);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return coupon;
    }
    public static void purchaseCoupon(Coupon coupon) {
        try {
            cf.purchaseCoupon(coupon);
        } catch (CustomUserException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static ArrayList<Coupon> getCustomerCoupons() {
        try {
            return cf.getCustomerCoupons();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static ArrayList<Coupon> getCustomerCoupon(double price) {
        try {
            return cf.getCustomerCoupon(price);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static ArrayList<Coupon> getCustomerCoupon(Category category) {
        try {
            return cf.getCustomerCoupon(category);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }


}
