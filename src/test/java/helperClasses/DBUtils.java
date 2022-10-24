package helperClasses;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static helperClasses.UtilityMethods.logError;


public class DBUtils {

    public static Connection getSQLServerConnection(String DB_SERVER, String DB_NAME, String DB_USERNAME, String DB_PASSWORD) {
        Connection conn = null;

        try{
            //Class.forName(net.sourceforge.jtds.jdbc.Driver);
            String connectionURL = "jdbc:jtds:sqlserver://" + DB_SERVER + ";DatabaseName=" + DB_NAME;

            conn = DriverManager.getConnection(connectionURL, DB_USERNAME, DB_PASSWORD);
        } catch (Exception e) {
            logError(e.toString());
        }

        return conn;
    }

    public static void executeDBQuery(String query) {
        //Connection conn = getSQLServerConnection();

        //Statement stmt = conn.createStatement();
        //ResultSet rs = stmt.executeQuery(query);
        //rs.next(); //Obtain first row value

        //releaseSQLStatement();
        //releaseSQLConnection();
    }

    public static void releaseSQLStatement(Statement stmt) {
        try {
            stmt.close();
        } catch (SQLException e) {
            logError(e.toString());
        }
    }

    public static void releaseSQLConnection(Connection conn) {
        try {
            conn.close();
        } catch (Exception e) {
            logError(e.toString());
        }
    }
}
