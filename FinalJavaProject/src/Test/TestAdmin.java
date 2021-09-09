package Test;

import Beans.Company;
import Beans.Coupon;
import Beans.Customer;
import Exceptions.CustomUserException;
import Facade.AdminFacade;
import Login.LoginManager;
import Login.ClientType;
import Threads.CouponExpirationDailyJob;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class TestAdmin {
    public static AdminFacade af;

    public static void testAll() {
        //Admin operations
        // starting the Job thread
        TimerTask couponExpirationDaily = new CouponExpirationDailyJob();
        Timer time = new Timer();
        time.scheduleAtFixedRate(couponExpirationDaily,0,1000*30);
        //correct login parameters
        af = (AdminFacade) (LoginManager.getInstance().login("Admin@admin.com", "admin", ClientType.valueOf("ADMINISTRATOR")));
        // WRONG LOGIN
       // af = (AdminFacade) (LoginManager.getInstance().login("Aviv@gmail.com", "12344556", ClientType.ADMINISTRATOR));
//        //adding new company
        addCompany(new Company("hhhh company", "Avivar@gmail.com", "1234567"));
//        //updating company
        updateCompany(new Company("Aviv4588@gmail.com", "12345678"));
        //updateCompany(new Company(1, "Apple", "Lalin@gmail.com", "123455"));
//        //delete company
      //  deleteCompany(2);
//        //get All companies
        System.out.println("===================================");
        System.out.println(getAllCompanies());
//        //get one company from the list
        System.out.println("===================================");
        System.out.println(getOneCompanyById(1));
//        //add custome
        addCustomer(new Customer("Aviv", "Avisrur", "Aviv4588@gmail.com", "123456"));
        //addCustomer(new Customer("Ofer", "David", "Aviv4588@gmail.com", "1234556"));
//        //update customer
        updateCustomer(new Customer(11, "Aviv", "Avisrur", "Aviv4588@gmail.com", "123455555555555556"));
//        //delete customer(in the sql its cascade to the coupons so all coupons will be deleted to.
        deleteCustomer(1);
//        //get all customers in the system.
        System.out.println("===================================");
        System.out.println(getAllCustomers());
//        //get specific customer by an ID
        System.out.println("===================================");
        System.out.println(getOneCustomerById(1));
//        //get all coupons in the system
        System.out.println("===================================");
        System.out.println(getAllCoupons());
    }

    private static ArrayList<Coupon> getAllCoupons() {
        try {
           return af.getAllCoupons();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    private static Customer getOneCustomerById(int customerId) {
        try {
            return af.getOneCustomerById(customerId);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    private static ArrayList<Customer> getAllCustomers() {

        try {
            return af.getAllCustomers();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    private static void deleteCustomer(int customerId) {
        try {
            af.deleteCustomer(customerId);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static Company getOneCompanyById(int companyId) {
        try {
            return af.getOneCompanyById(companyId);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    private static void deleteCompany(int companyId) {
        try {
            af.deleteCompany(companyId);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static ArrayList<Company> getAllCompanies() {
        try {
            return af.getAllCompanies();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    static void addCompany(Company company) {
        try {
            TestAdmin.af.addCompany(company);
        } catch (CustomUserException ex) {
            System.out.println(ex.getMessage());
        }
    }

    static void updateCompany(Company company) {
        try {
            af.updateCompany(company);
        } catch (CustomUserException ex) {
            System.out.println(ex.getMessage());
        }
    }

    static void addCustomer(Customer customer) {
        try {
            af.addCustomer(customer);
        } catch (CustomUserException ex) {
            System.out.println(ex.getMessage());
        }
    }

    static void updateCustomer(Customer customer) {
        try {
            af.updateCustomer(customer);
        } catch (CustomUserException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void main(String[] args) {
        TestAdmin.testAll();
    }
}
