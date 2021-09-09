package SQL;

import Beans.Category;
import Beans.Company;
import Beans.Coupon;
import Beans.Customer;
import DAO.CompaniesDAO;
import DAO.CouponsDAO;
import DAO.CustomerDAO;
import DBDao.CompaniesDBDAO;
import DBDao.CouponsDBDAO;
import DBDao.CustomerDBDAO;

import java.sql.Date;
import java.sql.SQLException;

public class MockData {
    public static void CreateMockData() {
        CompaniesDAO companiesDAO = new CompaniesDBDAO();
        CouponsDAO couponsDAO = new CouponsDBDAO();
        CustomerDAO customerDAO = new CustomerDBDAO();

        DataBaseManager.createDataBase();
        DataBaseManager.createTables();
        DataBaseManager.initializeCategories();
        try {
            companiesDAO.addCompany(new Company("Microsoft", "company@microsoft.com", "1234567"));
            companiesDAO.addCompany(new Company("McDonald's", "company@mcdonalds.com", "7654321"));
            customerDAO.addCustomer(new Customer("Dudu","Awat","dudu@gmail.com","king"));
            customerDAO.addCustomer(new Customer("Ayelet","Shaked","Ayelets@gov.il","hasamba123"));
            couponsDAO.addCoupon(new Coupon(1, Category.Electricity,"Keyboard","best microsoft keyboard", Date.valueOf("2021-06-28"),Date.valueOf("2021-07-28"),200,250.3d,"keyboard.jpg"));
            couponsDAO.addCoupon(new Coupon(1, Category.Electricity,"Mouse X20Z","Okay mouse", Date.valueOf("2021-06-28"),Date.valueOf("2021-07-29"),3,99.90d,"mouse.jpg"));
            couponsDAO.addCoupon(new Coupon(2, Category.Food,"Happy Meal","Discounted happy meal", Date.valueOf("2021-06-25"),Date.valueOf("2022-07-28"),1000,15.5d,"happymeal.jpg"));
            couponsDAO.addCoupon(new Coupon(2, Category.Food,"Children meal","fish and chips", Date.valueOf("2020-06-28"),Date.valueOf("2221-07-28"),500,20.5d,"chipsmeal.jpg"));
            couponsDAO.addCoupon(new Coupon(1, Category.Food,"Expired coupon","Expired coupon", Date.valueOf("2020-06-28"),Date.valueOf("2021-06-15"),500,20.5d,"chipsmeal.jpg"));
            couponsDAO.addCouponPurchase(1, 1);
            couponsDAO.addCouponPurchase(1, 2);
            couponsDAO.addCouponPurchase(1, 3);
            couponsDAO.addCouponPurchase(2, 3);
            couponsDAO.addCouponPurchase(2, 4);


        } catch (SQLException throwables) {
            System.out.println(throwables.getMessage());
        }


    }
}
