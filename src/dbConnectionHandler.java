import java.sql.Statement;
import java.sql.DriverManager;

import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.SQLException;

class dbConnectionHandler {

    public static final String user = "csce331_903_davidrodriguez24";
    public static final String pswd = "cEl240403";

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
         }
         return null;
     }

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
            
            // Check if the result set has data
            if (rs.next()) {
                res = rs.getInt(1); // Retrieve the integer from the first column
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
        
        return res; // Return the retrieved integer or null if there was an issue or no data found
    }

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