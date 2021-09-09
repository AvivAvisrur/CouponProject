package SQL;

import java.sql.*;
import java.util.Map;

/**
 * DB UTIL CLASS have inside it all the util methods we need to run the query's
 * has one attribute of sql query for the categories.
 */
public class DButils {
    private static final String INSERT_CATEGORIES="INSERT INTO `grooponSystem`.`categories` (NAME) VALUES";
    /**
     * this method take JAVA String and run the content of it as aSQL Query.
     * @param sql this is the java string that the method will run as query.
     * @throws SQLException throw sql exception
     */
    public static void runQuery(String sql) throws SQLException {
        Connection connection = null;
        try {
            //take a connection for connection pool.
            connection = ConnectionPool.getInstance().getConnection();
            //run the sql command
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.execute();
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }  finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }
    }
    /**
     * This method is upgrade of the previous one. run query with MAP collection.
     * @param query the Java string that will run as sql query.
     * @param params the map collection
     * @throws SQLException throw sql exception
     */
    public static boolean runBetterQuery(String query, Map<Integer, Object> params) throws SQLException {
        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            //our simple way
            params.forEach((key, value) -> {
                try {
                    if (value instanceof Integer) {
                        statement.setInt(key, (int) value);
                    } else if (value instanceof String){
                        statement.setString(key,String.valueOf(value));
                    } else if (value instanceof Date){
                        statement.setDate(key, (Date)value);
                    } else if (value instanceof Boolean){
                        statement.setBoolean(key,(Boolean)value);
                    } else if (value instanceof Double){
                        statement.setDouble(key,(Double)value);
                    } else if (value instanceof Float){
                        statement.setFloat(key,(Float)value);
                    } else if (value instanceof Timestamp){
                        statement.setTimestamp(key,(Timestamp) value);
                    }
                } catch (SQLException err) {
                    System.out.println("Error in sql :" + err.getMessage());
                }
            });
            statement.execute();
        } catch (InterruptedException e) {
            System.out.println("Error in executing sql");
            return false;
        }  finally {
            try {
                ConnectionPool.getInstance().returnConnection(connection);
            } catch (SQLException throwables) {
                throwables.getMessage();
                System.out.println(throwables.getErrorCode());
            }
        }
        return true;
    }
    /**
     * this method run SQL QUERY AND Return a resultSet
     * @param query sql QUERY
     * @param params Map collection
     * @throws SQLException throw sql exception
     */
    public static ResultSet runQueryGetRS(String query, Map<Integer, Object> params) throws SQLException {
        Connection connection = null;
        ResultSet resultSet = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            params.forEach((key, value) -> {
                try {
                    if (value instanceof Integer) {
                        statement.setInt(key, (int) value);
                    } else if (value instanceof String){
                        statement.setString(key,String.valueOf(value));
                    } else if (value instanceof Date){
                        statement.setDate(key, (Date)value);
                    } else if (value instanceof Boolean){
                        statement.setBoolean(key,(Boolean)value);
                    } else if (value instanceof Double){
                        statement.setDouble(key,(Double)value);
                    } else if (value instanceof Float){
                        statement.setFloat(key,(Float)value);
                    } else if (value instanceof Timestamp){
                        statement.setTimestamp(key,(Timestamp) value);
                    }
                } catch (SQLException err) {
                    System.out.println("Error in sql :" + err.getMessage());
                }
            });
            resultSet = statement.executeQuery();
        } catch (InterruptedException | SQLException e) {
            System.out.println("Error in executing sql");
        }  finally {
            ConnectionPool.getInstance().returnConnection(connection);
        }
        return resultSet;
    }

}
