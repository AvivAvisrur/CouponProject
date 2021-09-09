package DBDao;

import Beans.Coupon;
import DAO.CouponsDAO;
import Beans.Category;
import SQL.ConnectionPool;
import SQL.DButils;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import static SQL.DButils.*;

/**
 * this class implements data access(CRUD) SQL methods related to the coupons table in the database
 */
public class CouponsDBDAO implements CouponsDAO {
    Connection connection;

    /**
     * prepared sql statements
     */
    private static final String ADD_COUPON = "INSERT INTO `GrooponSystem`.`COUPONS` (`company_id`,`category_id`,`title`,`description`,`start_date`,`end_date`,`amount`,`price`,`image`) VALUES(?,?,?,?,?,?,?,?,?)";
    private static final String UPDATE_COUPON = "UPDATE `GrooponSystem`.`COUPONS` SET title = ?,description = ?,end_date = ?,amount=?,price=?,image=? WHERE ID = ?";
    private static final String DELETE_COUPON = "DELETE FROM `GrooponSystem`.`COUPONS` WHERE ID = ?";
    private static final String GET_ALL_COUPONS = "SELECT*FROM `GrooponSystem`.`COUPONS`";
    private static final String ADD_COUPON_PURCHASE = "INSERT INTO `GrooponSystem`.`customers_vs_coupons` (`customer_id`,`coupon_id`) VALUES(?,?)";
    private static final String IS_IN_STOCK = "SELECT amount FROM grooponsystem.coupons WHERE id=?";
    private static final String REDUCE_ONE = "UPDATE `GrooponSystem`.`COUPONS` SET amount = ? WHERE ID = ?";
    private static final String DELETE_EXPIRED_COUPONS  = "DELETE  FROM `GrooponSystem`.`COUPONS` WHERE end_date < ?";
    private static final String IS_COUPON_EXISTS = "SELECT EXISTS(SELECT 1 FROM `GrooponSystem`.`coupons` WHERE id = ? LIMIT 1)";

    /**
     * this method adds a coupon to the database
     * by creating a parameter map and sending it and a prepared sql statement to the runBetterQueryMethod
     * @param coupon a bean instance of coupon intended to be added
     * @throws SQLException throw SQL EXCEPTION
     */
    @Override
    public void addCoupon(Coupon coupon) throws SQLException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, coupon.getCompanyId());
        params.put(2, coupon.getCategory().ordinal() + 1);
        params.put(3, coupon.getTitle());
        params.put(4, coupon.getDescription());
        params.put(5, coupon.getStartDate());
        params.put(6, coupon.getEndDate());
        params.put(7, coupon.getAmount());
        params.put(8, coupon.getPrice());
        params.put(9, coupon.getImage());
        runBetterQuery(ADD_COUPON, params);
    }


    /**
     * this method modifies an existing coupon in the DB
     * by creating a parameter map and sending it and a prepared sql statement to the runBetterQueryMethod
     * @param coupon an instance of coupon used to alter an existing db entry
     *@throws SQLException throw SQL EXCEPTION
     */
    @Override
    public void updateCoupon(Coupon coupon) throws SQLException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, coupon.getTitle());
        params.put(2, coupon.getDescription());
        params.put(3, coupon.getEndDate());
        params.put(4, coupon.getAmount());
        params.put(5, coupon.getPrice());
        params.put(6, coupon.getImage());
        params.put(7, coupon.getId());
        runBetterQuery(UPDATE_COUPON, params);
    }

    /**
     * this method deletes a coupon by sending a query through runBetterQuery method
     *
     * @param couponID id of the coupon to be deleted
     */
    @Override
    public void deleteCoupon(int couponID) {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, couponID);
        try {
            runBetterQuery(DELETE_COUPON, params);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    /**
     * this method receives all columns from the coupons table
     *
     * @return returns a list of all coupons created by using the runQueryGetRS method
     */
    @Override
    public ArrayList<Coupon> getAllCoupons() {
        ArrayList<Coupon> couponArrayList = new ArrayList<>();
        try {
            ResultSet rs = runQueryGetRS(GET_ALL_COUPONS, new HashMap<>());
            while (rs.next()) {
                couponArrayList.add(new Coupon(rs.getInt("id"),
                        rs.getInt("company_id"),
                        Category.values()[rs.getInt("category_id") - 1],
                        rs.getString("title"), rs.getString("description"),
                        rs.getDate("start_date"), rs.getDate("end_date"),
                        rs.getInt("amount"), rs.getDouble("price"),
                        rs.getString("image")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return couponArrayList;
    }


    /**
     * a getter method for a specific coupon by id from the DB
     *
     * @param couponID id of the requested coupon
     * @return returns a coupon bean instance using the runQueryGetRS method
     */
    @Override
    public Coupon getOneCoupon(int couponID) {
        Coupon result = null;
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, couponID);
        LinkedList<String> columns = new LinkedList<>();
        columns.add("id=?");
        try {
            result = getCouponsBy(columns, map).get(0);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    /**
     * this method adds an entry to the COSTUMERS_VS_COUPONS table, documenting a customer's coupon purchase in the database.
     * @param customerID the id of the customer who made the purchase
     * @param couponID   the id of the coupon the customer had purchased
     *@throws SQLException throw SQL EXCEPTION
     */
    @Override
    public void addCouponPurchase(int customerID, int couponID) throws SQLException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, customerID);
        params.put(2, couponID);
        runBetterQuery(ADD_COUPON_PURCHASE, params);
    }

    /**
     * this method returns a list of coupons based on a company's id using the more general getCouponsBy method
     *
     * @param companyID the id of the company
     * @return returns a list of coupons based on a company's id
     */
    @Override
    public ArrayList<Coupon> getCompanyCouponsById(int companyID) {
        LinkedList<String> columns = new LinkedList<>();
        Map<Integer, Object> params = new HashMap<>();
        columns.add("company_id=?");
        params.put(1, companyID);
        ArrayList<Coupon> result = null;
        try {
            result = getCouponsBy(columns, params);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    /**
     * this method returns a list of coupons based on a company's id and category type using the more generic getCouponsBy method
     *
     * @param companyID the requested company's id
     * @param category  the category type of the coupons
     * @return returns a list of coupons based on a company's id and category type
     */

    @Override
    public ArrayList<Coupon> getCompanyCouponsByIdAndCat(int companyID, Category category) {
        LinkedList<String> columns = new LinkedList<>();
        Map<Integer, Object> params = new HashMap<>();
        columns.add("company_id=?");
        params.put(1, companyID);
        columns.add("category_id=?");
        params.put(2, category.ordinal() + 1);
        ArrayList<Coupon> result = null;
        try {
            result = getCouponsBy(columns, params);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    /**
     * a getter method for a list of a company's coupons by the company's id and up to a max price
     *
     * @param companyID the requested company's id
     * @param maxPrice  a max price parameter
     * @return returns a list of coupons based on a company's id and a max price
     */
    @Override
    public ArrayList<Coupon> getCompanyCouponsByIdAndMax(int companyID, double maxPrice) {
        LinkedList<String> columns = new LinkedList<>();
        Map<Integer, Object> params = new HashMap<>();
        columns.add("company_id=?");
        params.put(1, companyID);
        columns.add("price<=?");
        params.put(2, maxPrice);
        ArrayList<Coupon> result = null;
        try {
            result = getCouponsBy(columns, params);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    /**
     * this method checks if the coupon's amount field is higher than 0(is in stock)
     *
     * @param coupon a coupon instance to check
     * @return true if amount field is >0, false if not
     *
     */

    @Override
    public boolean isInStock(Coupon coupon) {
        boolean isInStock = false;
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, coupon.getId());
        ResultSet res = null;
        try {
            res = runQueryGetRS(IS_IN_STOCK, params);
            while (res.next()) {
                isInStock = res.getInt("amount") > 0;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return isInStock;
    }


    /**
     * this method reduces a bean's amount field in the database by 1
     * @param coupon a coupon instance to reduce it's amount
     *@throws SQLException throw SQL EXCEPTION
     */
    @Override
    public void reduceOne(Coupon coupon) throws SQLException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, coupon.getAmount() - 1);
        params.put(2, coupon.getId());
        runBetterQuery(REDUCE_ONE, params);
    }

    @Override
    public void deleteExpiredCoupons(Date date) {
        Map<Integer,Object>params = new HashMap<>();
        params.put(1,date);
        try {
            runBetterQuery(DELETE_EXPIRED_COUPONS,params);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * this method generates "get" SQL strings using a linked list of column names;
     *
     * @param columnsAndSymbols a linked list containing column names and arithmetic operators used to construct the sql
     * @return an sql string, containing question mark place holders instead of values
     */
    private String generateGetCouponsBySQL(LinkedList<String> columnsAndSymbols) {
        StringBuilder strb = new StringBuilder();
        strb.append("SELECT * FROM `GrooponSystem`.`COUPONS`");
        if (!columnsAndSymbols.isEmpty()) {

            strb.append(" WHERE ");
        }
        strb.append(String.join(" AND ", columnsAndSymbols));
        return strb.toString();
    }


    /**
     * a general method for the coupons table search. "gets" a list according to specifications
     *
     * @param columns a linked list of strings containing column names and arithmetic operators
     * @param params  a map of integer indexes and objects
     * @return returns a list of coupons based on the columns list specifications, using the runQueryGetRS method
     *@throws SQLException throw SQL EXCEPTION
     */
    private ArrayList<Coupon> getCouponsBy(LinkedList<String> columns, Map<Integer, Object> params) throws SQLException {
        ArrayList<Coupon> couponArrayList = new ArrayList<>();
        ResultSet rs;
            rs = DButils.runQueryGetRS(generateGetCouponsBySQL(columns), params);
            while (rs.next()) {
                couponArrayList.add(new Coupon(rs.getInt("id"), rs.getInt("company_id"), Category.values()[rs.getInt("category_id") - 1], rs.getString("title"), rs.getString("description"), rs.getDate("start_date"), rs.getDate("end_date"), rs.getInt("amount"), rs.getDouble("price"), rs.getString("image")));
            }
        return couponArrayList;
    }
    public boolean isCouponExists(int couponID) {
        boolean isExists = false;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(IS_COUPON_EXISTS);
            statement.setInt(1, couponID);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                isExists = result.getInt(1) > 0;
            }
        } catch (InterruptedException | SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (connection != null) {
                try {
                    ConnectionPool.getInstance().returnConnection(connection);
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }

            }
        }
        return isExists;
    }

}
