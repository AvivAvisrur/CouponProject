package DBDao;

import Beans.Company;
import Beans.Coupon;
import DAO.CompaniesDAO;
import Beans.Category;
import SQL.ConnectionPool;
import SQL.DButils;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static SQL.DButils.runQueryGetRS;

/**
 * This class implements CRUD SQL methods related to the database table of the company
 * This class has 1 attribute from type Connection. that will connect our methods to database tables.
 */
public class CompaniesDBDAO implements CompaniesDAO {

    Connection connection;
    private static final String ADD_COMPANY = "INSERT INTO`GrooponSystem`.`Companies` (`NAME`,`EMAIL`,`PASSWORD`) VALUES(?,?,?)";
    private static final String UPDATE_COMPANY = "UPDATE `GrooponSystem`.`COMPANIES` SET EMAIL = ?,PASSWORD = ? WHERE ID = ?";
    private static final String REMOVE_COMPANY = "DELETE FROM `GrooponSystem`.`COMPANIES` WHERE ID = ?";
    private static final String GET_ALL_COMPANIES = "SELECT*FROM `GrooponSystem`.`COMPANIES`";
    private static final String GET_ALL_COUPONS_BY_COMPANY_ID = "SELECT * FROM `GrooponSystem`.`COUPONS` WHERE COMPANY_ID = ?";
    private static final String GET_ONE_COMPANY = "SELECT*FROM `GrooponSystem`.`COMPANIES` WHERE ID = ?";
    private static final String GET_VERIFIED_COMPANY_ID = "SELECT id FROM `GrooponSystem`.`COMPANIES` where email=? AND password=?";
    private static final String GET_All_PURCHASED_BY_COUPON_ID = "SELECT b.* FROM grooponsystem.customers_vs_coupons LEFT join grooponsystem.coupons as b ON grooponsystem.customers_vs_coupons.coupon_id = b.id WHERE coupon_id =?";


    /**
     * This method adds company if it doesn't exists in the companies table in the database.
     * This method calls another method(runBetterQuery) from DButils class.
     * @param company this is the company we will try to add to our SQL database table(companies).
     * @throws SQLException throw SQL EXCEPTION
     */
    // If I catch the exception here it will cause a problem by not knowing if the company was added successfully or not.
    @Override
    public void addCompany(Company company) throws SQLException {
        Map<Integer, Object> parms = new HashMap<>();
        parms.put(1, company.getName());
        parms.put(2, company.getEmail());
        parms.put(3, company.getPassword());
        DButils.runBetterQuery(ADD_COMPANY, parms);
    }

    /**
     * This method updates company from the companies table in the database.
     * This method calls another method(runBetterQuery) from DButils class.
     * @param company this is the company we will update.
     * @throws SQLException throw SQL EXCEPTION
     */
    @Override
    public void updateCompany(Company company) throws SQLException {
        Map<Integer, Object> parms = new HashMap<>();
        parms.put(1, company.getEmail());
        parms.put(2, company.getPassword());
        parms.put(3, company.getId());
        DButils.runBetterQuery(UPDATE_COMPANY, parms);
    }

    /**
     * This method deletes company from the company table in the database.
     * This method calls another method(runBetterQuery) from DButils class.
     *
     * @param companyID this is the ID of the company we will delete from the SQL database table(companies).
     */
    @Override
    public void deleteCompany(int companyID) {
        Map<Integer, Object> parms = new HashMap<>();
        parms.put(1, companyID);
        try {
            DButils.runBetterQuery(REMOVE_COMPANY, parms);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * This method creates ArrayList of all the companies from the database table(companies).
     *
     * @return ArrayList of all the companies from the database table(companies).
     */
    @Override
    public ArrayList<Company> getAllCompanies() {
        ArrayList<Company> companies = new ArrayList<>();
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_ALL_COMPANIES);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                Company company = new Company(result.getInt("ID"),
                        result.getString("NAME"),
                        result.getString("EMAIL"),
                        result.getString("PASSWORD"),
                        getAllCouponsByCompanyID(result.getInt("id")));
                companies.add(company);
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
        return companies;

    }

    /**
     * This method gets 1 company.
     *
     * @param companyID this is the ID of the company we will return.
     * @return 1 company with all the coupons she sell.
     */
    @Override
    public Company getOneCompany(int companyID) {
        Company company = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_ONE_COMPANY);
            statement.setInt(1, companyID);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                company = new Company(result.getInt("ID"),
                        result.getString("NAME"),
                        result.getString("EMAIL"),
                        result.getString("PASSWORD"),
                        getAllCouponsByCompanyID(companyID));
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
        return company;
    }

    /**
     * This method gets params of company and returns id number of the company.
     *
     * @param email    this is the email of the company we will return the id number.
     * @param password this is the password of the company.
     * @return id number of 1 company by email and password info from the sql.
     */
    @Override
    public int getCompanyId(String email, String password) {
        int result = -1;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_VERIFIED_COMPANY_ID);
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
     * This method creates ArrayList of all the coupons that in the specific id of company which mean all coupons that company have.
     *
     * @param companyID this is the id number of the company who sell  coupons
     * @return ArrayList of all the coupons that the company.
     */
    public ArrayList<Coupon> getAllCouponsByCompanyID(int companyID) {
        ArrayList<Coupon> coupons = new ArrayList<>();
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_ALL_COUPONS_BY_COMPANY_ID);
            statement.setInt(1, companyID);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                coupons.add(new Coupon(result.getInt("ID"),
                        result.getInt("company_id"), Category.values()[result.getInt("category_id") - 1],
                        result.getString("title"),
                        result.getString("description"),
                        result.getDate("start_date"),
                        result.getDate("end_date"), result.getInt("amount"),
                        result.getDouble("price"),
                        result.getString("image")));
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
        return coupons;
    }

    /**
     * This method creates ArrayList of all the coupons of specific id that already purchased .
     *
     * @param couponID this is the id number of the coupon.
     * @return ArrayList of all the coupons that already purchased.
     */
    //NEW, ADDED BY OFER TO VERIFY HISTORY WAS DELETED BY CASCADE IN THE TESTER
    @Override
    public ArrayList<Coupon> getAllPurchasedByCouponID(int couponID) {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, couponID);
        ArrayList<Coupon> allPurchCop = new ArrayList<>();

        try {
            ResultSet rs = runQueryGetRS(GET_All_PURCHASED_BY_COUPON_ID, params);
            while (rs.next()) {
                allPurchCop.add(new Coupon(rs.getInt("id"), rs.getInt("company_id"), Category.values()[rs.getInt("category_id") - 1], rs.getString("title"), rs.getString("description"), rs.getDate("start_date"), rs.getDate("end_date"), rs.getInt("amount"), rs.getDouble("price"), rs.getString("image")));
            }

        } catch (SQLException t) {
            System.out.println(t.getMessage());
        }
        return allPurchCop;
    }
}
