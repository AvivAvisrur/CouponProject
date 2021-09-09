package Test;

import Beans.Category;
import Beans.Company;
import Beans.Coupon;
import DBDao.CompaniesDBDAO;
import Exceptions.DuplicateException;
import Exceptions.LengthException;
import Facade.CompanyFacade;
import Login.ClientType;
import Login.LoginManager;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class TestCompany {
    public static CompanyFacade cf;

    private static void TestCompany() {
        //login test
        cf = (CompanyFacade) LoginManager.getInstance().login("company@microsoft.com", "1234567", ClientType.COMPANY);
        //System.out.println(cf); //wrong credentials were entered, loginManager should return null
        //WRONG LOGIN
        //cf = (CompanyFacade) LoginManager.getInstance().login("Aviv4577@gmail.com", "123456", ClientType.COMPANY);

        //add coupon test
        System.out.println("=====================");
        Coupon coupon = new Coupon(1, Category.Electricity, "Fan", "excellent fan on sale",
                new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 3),
                5, 100D, "www.superfanzzzz.com/ExcellentFan.png");
        addCoupon(coupon);
        addCoupon(coupon); // adding a duplicate coupon to the database, should throw duplicate error

        //update coupon test
        System.out.println("=====================");
        updateCoupon(coupon);
        System.out.println("=====================");
        System.out.println(getAllCouponsByCompanyID()); //coupon and company id cannot be changed through update

        //delete coupon test
        System.out.println("=====================");
        System.out.println(getAllPurchasedByCouponIDTest()); //displaying the purchased coupon list
        System.out.println("=====================");
        deleteCoupon(3); // coupon id needs to be selected
        System.out.println("=====================");
        System.out.println(getAllPurchasedByCouponIDTest());// displaying the purchased coupon list after deleting the coupon from the company

        //returning all the company coupons
        System.out.println("=====================");
        System.out.println(getAllCouponsByCompanyID());
        //returning all the company coupons from a specific category
        System.out.println("=====================");
        System.out.println(getAllCouponsByCompanyID(Category.Electricity));
        //returning all company coupons up to a max price
        System.out.println("=====================");
        System.out.println(getAllCouponsByCompanyID(12000d));
        //returning all the company information
        System.out.println("=====================");
        System.out.println(getCompanyDetails());


    }

    static void addCoupon(Coupon coupon) {
        try {
            cf.addCoupon(coupon);
        } catch (DuplicateException | LengthException e) {
            System.out.println(e.getMessage());
        } catch (Exception err) {
            System.out.println(err.getMessage());
        }
    }

    static void updateCoupon(Coupon coupon) {
        try {
            cf.updateCoupon(coupon);
        } catch (DuplicateException | LengthException e) {
            System.out.println(e.getMessage());
        } catch (Exception err) {
            System.out.println(err.getMessage());
        }
    }

    static void deleteCoupon(int id) {
        try {
            cf.deleteCoupon(id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    static ArrayList<Coupon> getAllCouponsByCompanyID() {
        ArrayList<Coupon> couponArrayList = null;
        try {
            couponArrayList = cf.getAllCouponsByCompanyID();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return couponArrayList;
    }

    static ArrayList<Coupon> getAllCouponsByCompanyID(Category category) {
        ArrayList<Coupon> couponArrayList = null;
        try {
            couponArrayList = cf.getAllCouponsByCompanyID(category);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return couponArrayList;
    }

    static ArrayList<Coupon> getAllCouponsByCompanyID(double maxPrice) {
        ArrayList<Coupon> couponArrayList = null;
        try {
            couponArrayList = cf.getAllCouponsByCompanyID(maxPrice);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return couponArrayList;
    }

    static Company getCompanyDetails() {
        Company company = null;
        try {
            company = cf.getCompanyDetails();
        } catch (Exception err) {
            System.out.println(err.getMessage());
        }
        return company;
    }

    public static List<Coupon> getAllPurchasedByCouponIDTest() {

        CompaniesDBDAO companiesDBDAO = new CompaniesDBDAO();
        try {
            return companiesDBDAO.getAllPurchasedByCouponID(3);
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public static void main(String[] args) {
        TestCompany();
    }
}