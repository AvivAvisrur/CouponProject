package Facade;

import Beans.Category;
import Beans.Coupon;
import DBDao.CompaniesDBDAO;
import DBDao.CouponsDBDAO;
import DBDao.CustomerDBDAO;
import Exceptions.CouponException;
import Exceptions.DuplicateException;


import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;


/**
 * This class extends another abstract class. it has constructor that creates instance of it.
 * This class has methods that unable customer to login the system of purchasing coupons from few different types,
 * buying coupons, and getting coupons.
 * <p>
 * This class has 1 attribute from type int. which represents the id number of the customer who can buy coupon
 */
public class CustomerFacade extends ClientFacade {
    private int customerID;


    /**
     * This is constructor with no arguments. gives us instance of customerFaced type.
     */
    public CustomerFacade() {
        this.companiesDAO = new CompaniesDBDAO();
        this.customerDAO = new CustomerDBDAO();
        this.couponsDAO = new CouponsDBDAO();
    }


    /**
     * This method enable login of customer to the system.
     *
     * @param email    of the customer who tries to login.
     * @param password of the customer who tries to login.
     * @return boolean value. true if the customer succeed to login and false if he could not.
     */
    @Override
    public boolean login(String email, String password) {
        customerID = customerDAO.getCustomerId(email, password);
        return customerID != -1;
    }


    /**
     * This method unable the customer to buy coupon unless he bought the same coupon already.
     *
     * @param coupon of the customer.
     * @throws CouponException Exceptions relating to the validity of the coupon fields or purchase restrictions
     * @throws DuplicateException Throw duplicate exception
     */
    public void purchaseCoupon(Coupon coupon) throws CouponException, DuplicateException {
        try {
            if (!couponsDAO.isInStock(coupon)) {
                throw new CouponException("Sorry, this coupon is out of stock");
            }
            if (couponsDAO.getOneCoupon(coupon.getId()).getEndDate().before(new Date(Calendar.getInstance().getTimeInMillis()))) {
                throw new CouponException("Sorry, this coupon has expired");
            }
            couponsDAO.reduceOne(coupon);
            couponsDAO.addCouponPurchase(customerID, coupon.getId());
        } catch (SQLException e) {
            if(e.getErrorCode()==1062){
                throw new DuplicateException(e.getMessage(),"Coupon");
            }else{
                System.out.println(e.getMessage());
            }
        }
    }


    /**
     * This is a getter method by customer id.
     *
     * @return ArrayList of all the coupons that 1 customer bought by customer id number.
     */
    public ArrayList<Coupon> getCustomerCoupons() {
        return customerDAO.getAllPurchasedByCustomerID(customerID);
    }


    /**
     * This is a getter method by customer id and coupons category.
     *
     * @return ArrayList of all the coupons that 1 customer bought by customer id number and category of the coupon.
     */
    public ArrayList<Coupon> getCustomerCoupon(Category category) {
        return customerDAO.getAllPurchasedByCategory(customerID, category);
    }


    /**
     * This is a getter method by customer id and coupons max price.
     *
     * @return ArrayList of all the coupons that 1 customer bought by customer id number and max price of the coupon.
     */
    public ArrayList<Coupon> getCustomerCoupon(double maxPrice) {
        return customerDAO.getAllPurchasedByMaxPrice(customerID, maxPrice);
    }


}
