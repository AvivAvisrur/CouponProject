package DBDao;

import Beans.Category;
import Beans.Coupon;
import Beans.Customer;
import DAO.CustomerDAO;
import SQL.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static SQL.DButils.runBetterQuery;
import static SQL.DButils.runQueryGetRS;

/**
 * This class implements CRUD SQL methods related to the database table of the customer
 * This class has 1 attribute from type Connection. that will connect our methods to database tables.
 */
public class CustomerDBDAO implements CustomerDAO {

    Connection connection;

    private static final String ADD_CUSTOMER = "INSERT INTO `GrooponSystem`.`Customers` (`FIRST_NAME`,`LAST_NAME`,`EMAIL`,`PASSWORD`) VALUES(?,?,?,?)";
    private static final String UPDATE_CUSTOMER = "UPDATE `GrooponSystem`.`CUSTOMERS` SET FIRST_NAME = ?,LAST_NAME = ?,EMAIL = ?,PASSWORD = ? WHERE ID = ?";
    private static final String DELETE_CUSTOMER = "DELETE FROM `GrooponSystem`.`CUSTOMERS` WHERE ID = ?";
    private static final String GET_ALL_CUSTOMERS = "SELECT*FROM `GrooponSystem`.`CUSTOMERS`";
    private static final String GET_ONE_CUSTOMER = "SELECT*FROM `GrooponSystem`.`CUSTOMERS` WHERE ID = ?";
    private static final String GET_VERIFIED_CUSTOMER_ID = "SELECT id FROM `GrooponSystem`.`CUSTOMERS` where email=? AND password=?";
    private static final String GET_All_PURCHASED_BY_CUSTOMER_ID = "SELECT b.* FROM grooponsystem.customers_vs_coupons LEFT join grooponsystem.coupons as b ON grooponsystem.customers_vs_coupons.coupon_id = b.id WHERE customer_id =?";
    private static final String GET_All_PURCHASED_BY_CATEGORY = "SELECT b.* FROM grooponsystem.customers_vs_coupons AS a LEFT join grooponsystem.coupons as b ON a.coupon_id = b.id WHERE a.customer_id =? AND b.category_id =?";
    private static final String GET_All_PURCHASED_BY_MAX_PRICE = "SELECT b.* FROM grooponsystem.customers_vs_coupons AS a LEFT join grooponsystem.coupons as b ON a.coupon_id = b.id WHERE a.customer_id =? AND b.price <= ?";


    /**
     * This method adds customer if he doesn't exists in the customer table in the database.
     * This method calls another method(runBetterQuery) from DButils class.
     * @param customer this is the customer we will try to add to our SQL database table(customer).
     *@throws SQLException throw SQL EXCEPTION
     */
    @Override
    public void addCustomer(Customer customer) throws SQLException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, customer.getFirstName());
        params.put(2, customer.getLastName());
        params.put(3, customer.getEmail());
        params.put(4, customer.getPassword());

        runBetterQuery(ADD_CUSTOMER, params);
    }

    /**
     * This method updates customer from the customer table in the database.
     * This method calls another method(runBetterQuery) from DButils class.
     * @param customer this is the customer we will update
     *@throws SQLException throw SQL EXCEPTION
     */
    @Override
    public void updateCustomer(Customer customer) throws SQLException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, customer.getFirstName());
        params.put(2, customer.getLastName());
        params.put(3, customer.getEmail());
        params.put(4, customer.getPassword());
        params.put(5, customer.getId());
        runBetterQuery(UPDATE_CUSTOMER, params);

    }

    /**
     * This method deletes customer from the customer table in the database.
     * This method calls another method(runBetterQuery) from DButils class.
     *
     * @param customerID this is the ID of the customer we will delete from the SQL database table(customer).
     */
    @Override
    public void deleteCustomer(int customerID) {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, customerID);
        try {
            runBetterQuery(DELETE_CUSTOMER, params);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    /**
     * This method creates ArrayList of all the customers from the database table(customer).
     *
     * @return ArrayList of all the customers from the database table(customer).
     */
    @Override
    public ArrayList<Customer> getAllCustomers() {
        ArrayList<Customer> customers = new ArrayList<>();
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_ALL_CUSTOMERS);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                customers.add(new Customer(rs.getInt("ID"),
                        rs.getString("FIRST_NAME"),
                        rs.getString("LAST_NAME"),
                        rs.getString("EMAIL"),
                        rs.getString("PASSWORD"),
                        getAllPurchasedByCustomerID((rs.getInt("id")))));
            }
        } catch (InterruptedException | SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                ConnectionPool.getInstance().returnConnection(connection);
            } catch (SQLException throwables) {
                System.out.println(throwables.getMessage());
            }
        }
        return customers;
    }

    /**
     * This method gets 1 customer who purchased coupons.
     *
     * @param customerID this is the ID of the customer we will return.
     * @return 1 customer with all the coupons he bought.
     */
    @Override
    public Customer getOneCustomer(int customerID) {
        Customer customer = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_ONE_CUSTOMER);
            statement.setInt(1, customerID);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                customer = new Customer(rs.getInt("ID"),
                        rs.getString("FIRST_NAME"),
                        rs.getString("LAST_NAME"),
                        rs.getString("EMAIL"),
                        rs.getString("PASSWORD"),
                        getAllPurchasedByCustomerID(rs.getInt(customerID)));
            }
        } catch (InterruptedException | SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                ConnectionPool.getInstance().returnConnection(connection);
            } catch (SQLException t) {
                System.out.println(t.getMessage());
            }
        }

        return customer;
    }

    /**
     * This method gets params of customer and returns id number of the customer.
     *
     * @param email    this is the email of the customer we will return his id number.
     * @param password this is the password of the customer we will return.
     * @return id number of 1 customer by email and password info from the user.
     */
    @Override
    public int getCustomerId(String email, String password) {
        int result = -1;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_VERIFIED_CUSTOMER_ID);
            statement.setString(1, email);
            statement.setString(2, password);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                result = rs.getInt("id");
            }
        } catch (InterruptedException | SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }


    /**
     * This method creates ArrayList of all the customers who purchased coupons from the table in the database by id of the customer.
     *
     * @param customerID this is the id number of the customer who has purchased coupons
     * @return ArrayList of all the coupons that the customer has purchased.
     */
    @Override
    public ArrayList<Coupon> getAllPurchasedByCustomerID(int customerID) {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, customerID);
        ArrayList<Coupon> allPurchCop = new ArrayList<>();

        try {
            ResultSet rs = runQueryGetRS(GET_All_PURCHASED_BY_CUSTOMER_ID, params);
            while (rs.next()) {
                allPurchCop.add(new Coupon(rs.getInt("id"), rs.getInt("company_id"), Category.values()[rs.getInt("category_id") - 1], rs.getString("title"), rs.getString("description"), rs.getDate("start_date"), rs.getDate("end_date"), rs.getInt("amount"), rs.getDouble("price"), rs.getString("image")));
            }

        } catch (SQLException throwables) {
            System.out.println(throwables.getMessage());
        }

        return allPurchCop;
    }

    /**
     * This method creates ArrayList of all the customers who purchased
     * coupons from the table in the database by id of the customer and category of the coupon.
     *
     * @param customerID this is the id number of the customer who has purchased coupons.
     * @param category   this is the category of the coupons that the customer bought.
     * @return ArrayList of all the coupons that the customer has purchased by specific category of coupon.
     */
    @Override
    public ArrayList<Coupon> getAllPurchasedByCategory(int customerID, Category category) {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, customerID);
        params.put(2, category.ordinal() + 1);
        ArrayList<Coupon> allPurchCop = new ArrayList<>();

        try {
            ResultSet rs = runQueryGetRS(GET_All_PURCHASED_BY_CATEGORY, params);
            while (rs.next()) {
                allPurchCop.add(new Coupon(rs.getInt("id"), rs.getInt("company_id"), Category.values()[rs.getInt("category_id") - 1], rs.getString("title"), rs.getString("description"), rs.getDate("start_date"), rs.getDate("end_date"), rs.getInt("amount"), rs.getDouble("price"), rs.getString("image")));
            }

        } catch (SQLException throwables) {
            System.out.println(throwables.getMessage());
        }
        return allPurchCop;
    }


    /**
     * This method creates ArrayList of all the customers who purchased
     * coupons from the table in the database by id of the customer and max price of the coupon.
     *
     * @param customerID this is the id number of the customer who has purchased coupons.
     * @param maxPrice   this is the max price of the coupons that the customer bought.
     * @return ArrayList of all the coupons that the customer has purchased filtered by max price.
     */
    @Override
    public ArrayList<Coupon> getAllPurchasedByMaxPrice(int customerID, double maxPrice) {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, customerID);
        params.put(2, maxPrice);
        ArrayList<Coupon> allPurchCop = new ArrayList<>();

        try {
            ResultSet rs = runQueryGetRS(GET_All_PURCHASED_BY_MAX_PRICE, params);
            while (rs.next()) {
                allPurchCop.add(new Coupon(rs.getInt("id"),
                        rs.getInt("company_id"),
                        Category.values()[rs.getInt("category_id") - 1],
                        rs.getString("title"), rs.getString("description"),
                        rs.getDate("start_date"), rs.getDate("end_date"),
                        rs.getInt("amount"), rs.getDouble("price"),
                        rs.getString("image")));
            }

        } catch (SQLException throwables) {
            System.out.println(throwables.getMessage());
        }
        return allPurchCop;
    }


}

