package in.co.rays.proj4.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * JDBCDataSource is a singleton utility class that manages database connections
 * using C3P0 connection pooling. It provides methods to obtain and close
 * database connections and associated JDBC resources safely.
 * 
 * <p>
 * Database configuration is read from <code>system.properties</code> file using
 * ResourceBundle.
 * </p>
 * 
 * Example usage:
 * <pre>
 * Connection conn = JDBCDataSource.getConnection();
 * // use connection
 * JDBCDataSource.closeConnection(conn);
 * </pre>
 * 
 * @author krati
 */
public final class JDBCDataSource {

    /** Singleton instance of JDBCDataSource */
    private static JDBCDataSource jds = null;

    /** C3P0 connection pool instance */
    private ComboPooledDataSource cpds = null;

    /** ResourceBundle for loading database properties */
    private static ResourceBundle rb = ResourceBundle.getBundle("in.co.rays.proj4.bundle.system");

    /**
     * Private constructor to initialize the C3P0 connection pool from
     * properties.
     */
    private JDBCDataSource() {
        try {
            cpds = new ComboPooledDataSource();
            cpds.setDriverClass(rb.getString("driver"));
            cpds.setJdbcUrl(rb.getString("url"));
            cpds.setUser(rb.getString("username"));
            cpds.setPassword(rb.getString("password"));
            cpds.setInitialPoolSize(Integer.parseInt(rb.getString("initialpoolsize")));
            cpds.setAcquireIncrement(Integer.parseInt(rb.getString("acquireincrement")));
            cpds.setMaxPoolSize(Integer.parseInt(rb.getString("maxpoolsize")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the singleton instance of JDBCDataSource.
     * <p>
     * Note: This lazy initialization is not thread-safe. In multi-threaded
     * environments, consider synchronization or double-checked locking.
     * </p>
     * 
     * @return JDBCDataSource singleton instance
     */
    public static JDBCDataSource getInstance() {
        if (jds == null) {
            jds = new JDBCDataSource();
        }
        return jds;
    }

    /**
     * Returns a database connection from the C3P0 pool.
     * 
     * @return Connection object or null if a connection cannot be obtained
     */
    public static Connection getConnection() {
        try {
            return getInstance().cpds.getConnection();
        } catch (SQLException e) {
            return null;
        }
    }

    /**
     * Closes JDBC resources safely: ResultSet, Statement, and Connection.
     * <p>
     * When using a connection pool, closing the Connection returns it to the
     * pool rather than physically closing it.
     * </p>
     * 
     * @param conn Connection to close
     * @param stmt Statement to close
     * @param rs   ResultSet to close
     */
    public static void closeConnection(Connection conn, Statement stmt, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Overloaded method to close Connection and Statement.
     * 
     * @param conn Connection to close
     * @param stmt Statement to close
     */
    public static void closeConnection(Connection conn, Statement stmt) {
        closeConnection(conn, stmt, null);
    }

    /**
     * Overloaded method to close only the Connection.
     * 
     * @param conn Connection to close
     */
    public static void closeConnection(Connection conn) {
        closeConnection(conn, null, null);
    }
}
