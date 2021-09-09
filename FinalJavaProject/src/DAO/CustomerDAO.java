package DAO;

import Beans.Category;
import Beans.Coupon;
import Beans.Customer;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CustomerDAO {

    void addCustomer(Customer customer) throws SQLException;

    void updateCustomer(Customer customer) throws SQLException;

    void deleteCustomer(int customerID);

    ArrayList<Customer> getAllCustomers();

    Customer getOneCustomer(int customerID);

    int getCustomerId(String email, String password);

    ArrayList<Coupon> getAllPurchasedByCustomerID(int customerID);

    ArrayList<Coupon> getAllPurchasedByCategory(int customerID, Category category);

    ArrayList<Coupon> getAllPurchasedByMaxPrice(int customerID, double maxPrice);

}
