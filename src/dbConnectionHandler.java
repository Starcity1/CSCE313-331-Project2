import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Handles database connection and operations.
 */
class dbConnectionHandler {
    // Database user credentials.
    public static final String user = "csce331_903_davidrodriguez24";
    public static final String pswd = "cEl240403";

    /**
     * Requests data from the database using the provided query.
     *
     * @param query The SQL query as a String.
     * @return A ResultSet containing the data retrieved from the database.
     */
    public final ResultSet requestData(String query) {
        Connection conn = null;
        String dbConnectionString = "jdbc:postgresql://csce-315-db.engr.tamu.edu/csce315331_03r_db";
        try {
            conn = DriverManager.getConnection(dbConnectionString, user, pswd);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(1);
        }
        try {
            Statement stmt = conn.createStatement();
            ResultSet res = stmt.executeQuery(query);
            conn.close();
            return res;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Executes a query and retrieves a single integer value from the database.
     *
     * @param query The SQL query as a String.
     * @return The integer retrieved from the database, or null if there is no result.
     */
    public final Integer requestInt(String query) {
        Connection conn = null;
        Integer res = null;
        String dbConnectionString = "jdbc:postgresql://csce-315-db.engr.tamu.edu/csce315331_03r_db";
        try {
            conn = DriverManager.getConnection(dbConnectionString, user, pswd);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(1);
        }
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                res = rs.getInt(1);
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return res;
    }

    /**
     * Executes an update operation on the database.
     *
     * @param updateQuery The SQL update statement as a String.
     * @return The number of rows affected by the update.
     */
    public int executeUpdate(String updateQuery) {
        Connection conn = null;
        int rowsAffected = 0;
        String dbConnectionString = "jdbc:postgresql://csce-315-db.engr.tamu.edu/csce315331_03r_db";
        try {
            conn = DriverManager.getConnection(dbConnectionString, user, pswd);
            Statement stmt = conn.createStatement();
            rowsAffected = stmt.executeUpdate(updateQuery);
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return rowsAffected;
    }
}
