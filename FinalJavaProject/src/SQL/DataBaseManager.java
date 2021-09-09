package SQL;

import Beans.Category;

import java.sql.SQLException;

import static SQL.DButils.runQuery;

/**
 * Data base manager class
 * has attributes of strings for the sql query all the attributes are static.
 * we dont have ctor in this class
 */
public class DataBaseManager {
    protected static final String URL = "jdbc:mysql://localhost:3306?createDatabaseIfNotExist=FALSE";
    protected static final String USER_NAME = "root";
    protected static final String PASSWORD = "12345678";



    private static final String CREATE_DB = "CREATE SCHEMA if not exists GrooponSystem";
    private static final String DROP_DB = "DROP SCHEMA IF EXISTS GrooponSystem";


    private static final String CREATE_TABLE_COMPANIES = "CREATE TABLE if not exists `GrooponSystem`.`COMPANIES`" +
            " (`ID` INT NOT NULL AUTO_INCREMENT," +
            "`NAME` VARCHAR(50) UNIQUE NOT NULL," +
            "`EMAIL` VARCHAR(30) UNIQUE NOT NULL, " +
            "`PASSWORD` VARCHAR(30) NOT NULL," +
            "PRIMARY KEY (`ID`));";

    private static final String CREATE_TABLE_CUSTOMERS = "CREATE TABLE if not exists `GrooponSystem`.`CUSTOMERS`" +
            " (`ID` INT NOT NULL AUTO_INCREMENT," +
            "`FIRST_NAME` VARCHAR(50) NOT NULL," +
            "`LAST_NAME` VARCHAR(50) NOT NULL," +
            "`EMAIL` VARCHAR(30) UNIQUE NOT NULL, " +
            "`PASSWORD` VARCHAR(30) NOT NULL," +
            "PRIMARY KEY (`ID`));";

    private static final String CREATE_TABLE_CATEGORIES = "CREATE TABLE if not exists `GrooponSystem`.`CATEGORIES`" +
            " (`ID` INT NOT NULL AUTO_INCREMENT," +
            "`NAME` VARCHAR(50) UNIQUE NOT NULL," +
            "PRIMARY KEY (`ID`));";

    private static final String CREATE_TABLE_COUPONS = "CREATE TABLE if not exists `GrooponSystem`.`coupons` " +
            "(`id` INT NOT NULL AUTO_INCREMENT," +
            "`company_id` INT NOT NULL," +
            "`category_id` INT NOT NULL," +
            "`title` VARCHAR(40) NOT NULL," +
            "`description` VARCHAR(200) NOT NULL," +
            "`start_date` DATE NOT NULL," +
            "`end_date` DATE NOT NULL," +
            "`amount` INT NOT NULL, " +
            "`price` DOUBLE NOT NULL, " +
            "`image` VARCHAR(150) NOT NULL," +
            "PRIMARY KEY(`id`)," +
            "FOREIGN KEY(`company_id`) REFERENCES `GrooponSystem`.`companies`(`id`) ON DELETE CASCADE," +
            "FOREIGN KEY(`category_id`) REFERENCES `GrooponSystem`.`categories`(`id`)" +
            ", UNIQUE INDEX (`company_id`,`title`), CHECK (`amount` > 0));";

    private static final String CREATE_TABLE_CUSTOMERS_VS_COUPONS = "CREATE TABLE if not exists `GrooponSystem`.`customers_vs_coupons` " +
            "(`customer_id` INT NOT NULL," +
            "`coupon_id` INT NOT NULL ," +
            "PRIMARY KEY(`customer_id`,`coupon_id`) , " +
            "FOREIGN KEY(`customer_id`) REFERENCES `GrooponSystem`.`customers`(`id`) ON DELETE CASCADE," +
            "FOREIGN KEY(`coupon_id`) REFERENCES `GrooponSystem`.`coupons`(`id`) ON DELETE CASCADE);";

    private static final String INSERT_CATEGORIES = "INSERT INTO `grooponSystem`.`categories` (NAME) VALUES";

    /**
     * this method will create the enum fields into the sql table
     */

    private static String createEnumSql() {
        StringBuilder str = new StringBuilder();
        str.append(INSERT_CATEGORIES);
        for (Category category1 : Category.values()) {
            str.append("('" + category1.name() + "'),");
        }
        str.deleteCharAt(str.length() - 1);
        return str.toString();
    }

    public static void initializeCategories() {
        try {
            runQuery(createEnumSql());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * this method create the SCHEMA in the SQL by running the sql QUERY
     */
    public static void createDataBase() {
        try {
            runQuery(CREATE_DB);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * this method create all the tables in the SQL by running the query's of the SQL
     */
    public static void createTables() {
        try {
            runQuery(CREATE_TABLE_COMPANIES);
            runQuery(CREATE_TABLE_CUSTOMERS);
            runQuery(CREATE_TABLE_CATEGORIES);
            runQuery(CREATE_TABLE_COUPONS);
            runQuery(CREATE_TABLE_CUSTOMERS_VS_COUPONS);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void dropTable() {
        try {
            runQuery(DROP_DB);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


}
