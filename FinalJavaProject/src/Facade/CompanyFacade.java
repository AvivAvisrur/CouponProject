package Facade;

import Beans.Category;
import Beans.Company;
import Beans.Coupon;
import DBDao.CompaniesDBDAO;
import DBDao.CouponsDBDAO;
import DBDao.CustomerDBDAO;
import Exceptions.DuplicateException;
import Exceptions.LengthException;


import java.sql.SQLException;
import java.util.ArrayList;

/**
 * This class extends another abstract class. it has constructor that creates instance of it.
 * This class has methods that unable company to login the system of purchasing coupons from few different types,
 * adding coupons, delete , update, and getting coupons.
 * <p>
 * This class has 1 attribute from type int. which represents the id number of the company who can sell coupon
 */
public class CompanyFacade extends ClientFacade {
    private int companyID;

    /**
     * This is constructor with no arguments. gives us instance of company Faced type.
     */
    public CompanyFacade() {
        this.companiesDAO = new CompaniesDBDAO();
        this.customerDAO = new CustomerDBDAO();
        this.couponsDAO = new CouponsDBDAO();
    }

    /**
     * This method enable login of company to the system.
     *
     * @param email    of the company who tries to login.
     * @param password of the company who tries to login.
     * @return boolean value. true if the company succeed to login and false if he could not.
     */
    @Override
    public boolean login(String email, String password) {
        companyID = companiesDAO.getCompanyId(email, password);
        return companyID != -1;
    }

    /**
     * This method enable the company to add coupon  to the system.
     *
     * @param coupon of the company.
     * @throws DuplicateException
     * @throws LengthException
     */
    public void addCoupon(Coupon coupon) throws DuplicateException, LengthException {
        try {
            couponsDAO.addCoupon(coupon);
            System.out.println("coupon added to the company");
        } catch (SQLException ex) {
            switch (ex.getErrorCode()) {
                case 1062:
                    throw new DuplicateException(ex.getMessage(), "Coupon");
                case 1406:
                    throw new LengthException(ex.getMessage(), "Coupon");
                default:
                    System.out.println(ex.getMessage());
            }
        }
    }

    /**
     * This method allow the company to update coupon from their list.
     *
     * @param coupon of the company.
     * @throws DuplicateException Throw duplicate exception
     * @throws LengthException throw length exception
     */
    public void updateCoupon(Coupon coupon) throws DuplicateException, LengthException {
        try {
            couponsDAO.updateCoupon(coupon);
            System.out.println("coupon updated");
        } catch (SQLException ex) {
            switch (ex.getErrorCode()) {
                case 1062:
                    throw new DuplicateException(ex.getMessage(), "Coupon");
                case 1406:
                    throw new LengthException(ex.getMessage(), "Coupon");
                default:
                    System.out.println(ex.getMessage());
            }
        }
    }

    /**
     * This method allow the company to delete coupon  from the system.
     * @param couponID of the coupon.
     * @throws SQLException throw sql exception
     */
    public void deleteCoupon(int couponID) throws SQLException {
        if(couponsDAO.isCouponExists(couponID)) {
            couponsDAO.deleteCoupon(couponID);
            System.out.printf("Coupon ID : %d  deleted successfully",couponID);
        }else{
            System.out.println("Coupon does not exists. was not deleted");
        }

    }

    /**
     * This method allow the company to see all the  coupons that regiestered on their id.
     *
     * @return all the coupons of the company we asked by the id
     */
    public ArrayList<Coupon> getAllCouponsByCompanyID() {
        return couponsDAO.getCompanyCouponsById(companyID);
    }

    /**
     * This method allow the company to see all the  coupons by their categories that regiestered on their id.
     *
     * @param category the id of the category of the coupons belongs to the company.
     * @return all the coupons in the same category
     */
    public ArrayList<Coupon> getAllCouponsByCompanyID(Category category) {
        return couponsDAO.getCompanyCouponsByIdAndCat(companyID, category);
    }

    /**
     * This method allow the company to see all the  coupons by their max price that registered on their id.
     *
     * @param maxPrice the   max price u looking for to show the coupons between the range of price.
     * @return all the coupons within the price
     */
    public ArrayList<Coupon> getAllCouponsByCompanyID(double maxPrice) {
        return couponsDAO.getCompanyCouponsByIdAndMax(companyID, maxPrice);
    }

    /**
     * This method gives all the details of the company.
     *
     * @return the company by the id
     */
    public Company getCompanyDetails() {
        return companiesDAO.getOneCompany(companyID);
    }


}
