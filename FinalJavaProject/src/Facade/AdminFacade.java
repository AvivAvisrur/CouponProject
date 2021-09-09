package Facade;

import Beans.Company;
import Beans.Coupon;
import Beans.Customer;
import DBDao.CompaniesDBDAO;
import DBDao.CouponsDBDAO;
import DBDao.CustomerDBDAO;
import Exceptions.DuplicateException;
import Exceptions.LengthException;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * This class extends another abstract class. it has constructor that creates instance of it.
 * This class has methods that allow admin to login the system of purchasing coupons from few different types,
 * adding company, add customer , update, and getting all companies,customer.
 * <p>
 * This class has no attribute.
 */
public class AdminFacade extends ClientFacade {
    /**
     * This is constructor with no arguments. gives us instance of adminFacade type.
     */
    public AdminFacade() {
        this.companiesDAO = new CompaniesDBDAO();
        this.customerDAO = new CustomerDBDAO();
        this.couponsDAO = new CouponsDBDAO();
    }

    /**
     * This method enable the company to login to the system.
     *
     * @param email    the email of the admin who try to log in.
     * @param password the password of the admin account.
     * @return true if the email and password is ok and false if not.
     */
    @Override
    public boolean login(String email, String password) {
        return email.toLowerCase().equals("admin@admin.com") && password.toLowerCase().equals("admin");
    }

    /**
     * This method enable the admin to add company  to the system.
     *
     * @param company the company we adding.
     * @throws DuplicateException Throw duplicate exception
     * @throws LengthException throw length exception
     */
    public void addCompany(Company company) throws DuplicateException, LengthException {
        try {
            companiesDAO.addCompany(company);
        } catch (SQLException ex) {
            switch (ex.getErrorCode()) {
                case 1062:
                    throw new DuplicateException(ex.getMessage(), "Company");
                case 1406:
                    throw new LengthException(company.getName(), "Company");
                default:
                    System.out.println(ex.getMessage());
            }
        }
    }

    /**
     * This method allow the admin to update company  to the system.
     *
     * @param company the company we updating.
     * @throws DuplicateException Throw duplicate exception
     * @throws LengthException throw length exception
     */
    public void updateCompany(Company company) throws DuplicateException, LengthException {
        try {
            companiesDAO.updateCompany(company);
        } catch (SQLException ex) {
            switch (ex.getErrorCode()) {
                case 1062:
                    throw new DuplicateException(ex.getMessage(), "Company");
                case 1406:
                    throw new LengthException(ex.getMessage(), "Company");
                default:
                    System.out.println(ex.getMessage());
            }
        }
    }

    /**
     * This method allow the admin to delete company  from the system.
     *
     * @param companyID the id of the specific company we want to delete.
     */
    public void deleteCompany(int companyID) {
        companiesDAO.deleteCompany(companyID);
    }

    /**
     * This method allow the admin to get all the  companies  from the system.
     *
     * @return all the companies from the DB
     */
    public ArrayList<Company> getAllCompanies() {
        return companiesDAO.getAllCompanies();

    }

    /**
     * This method allow the admin to get one  company  from the system.
     *
     * @param companyID the id of the specific company we want to get.
     * @return the company that the admin asked for.
     */
    public Company getOneCompanyById(int companyID) {
        return companiesDAO.getOneCompany(companyID);

    }

    /**
     * This method allow the admin to add customer  to the system.
     *
     * @param customer the customer we adding.
     * @throws DuplicateException Throw duplicate exception
     * @throws LengthException throw length exceptionn
     */
    public void addCustomer(Customer customer) throws DuplicateException, LengthException {
        try {
            customerDAO.addCustomer(customer);
        } catch (SQLException ex) {
            switch (ex.getErrorCode()) {
                case 1062:
                    throw new DuplicateException(ex.getMessage(), "Customer");
                case 1406:
                    throw new LengthException(ex.getMessage(), "Customer");
                default:
                    System.out.println(ex.getMessage());
            }
        }
    }

    /**
     * This method allow the admin to update customer  to the system.
     *
     * @param customer the customer we updating.
     * @throws DuplicateException Throw duplicate exception
     * @throws LengthException throw length exception
     */
    public void updateCustomer(Customer customer) throws DuplicateException, LengthException {
        try {
            customerDAO.updateCustomer(customer);
        } catch (SQLException ex) {
            switch (ex.getErrorCode()) {
                case 1062:
                    throw new DuplicateException(ex.getMessage(), "Customer");
                case 1406:
                    throw new LengthException(ex.getMessage(), "Customer");
                default:
                    System.out.println(ex.getMessage());
            }
        }
    }

    /**
     * This method allow the admin to delete customer  from the system.
     *
     * @param customerID the id of the specific customer we want to delete.
     */
    public void deleteCustomer(int customerID) {
        customerDAO.deleteCustomer(customerID);
    }

    /**
     * This method allow the admin to get all the customers from the system.
     *
     * @return all the customers in the system.
     */
    public ArrayList<Customer> getAllCustomers() {
        return customerDAO.getAllCustomers();
    }

    /**
     * This method allow the admin to get one customer by his id from the system.
     *
     * @param customerID the id of the specific company we want to update.
     * @return the customer the admin asked for.
     */
    public Customer getOneCustomerById(int customerID) {
        return customerDAO.getOneCustomer(customerID);
    }

    /**
     * This method allow the admin to get all coupons from the system.
     *
     * @return a list of all coupons.
     */
    public ArrayList<Coupon> getAllCoupons(){
        return couponsDAO.getAllCoupons();
    }

}
