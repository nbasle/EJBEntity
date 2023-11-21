package com.yaps.petstore.server.util.persistence;

import com.yaps.petstore.common.locator.ServiceLocator;
import com.yaps.petstore.common.logging.Trace;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * This class follows the Data Acces Objecr (DAO) Design Pattern.
 * It uses JDBC to store object values in a database.
 * Every concrete DAO class should extends this class.
 */
public abstract class AbstractDataAccessObject implements DataAccessConstants {

    // ======================================
    // =             Attributes             =
    // ======================================

    // Used for logging
    private final transient String _cname = this.getClass().getName();
    private static final String sname = AbstractDataAccessObject.class.getName();

    // ======================================
    // =           Business methods         =
    // ======================================
    /**
     * This method returns a database connection.
     *
     * @return a JDBC connection to the petstore database
     * @throws SQLException if a SQl expcetion if found
     */
    public static final Connection getConnection() throws SQLException {
        final Connection connection;
        DataSource dataSource = ServiceLocator.getInstance().getDataSource(DATASOURCE);
        connection = dataSource.getConnection();
        return connection;
    }

    /**
     * This method displays all information of an SQL exception. Its error code, state,
     * sql message and ultimately the stacktrace of the Exception
     *
     * @param e SQLException that you want to display
     */
    public static void displaySqlException(final SQLException e) {
        final String mname = "displaySqlException";

        Trace.severe(sname, mname, "Error code  : " + e.getErrorCode());
        Trace.severe(sname, mname, "SQL state   : " + e.getSQLState());
        Trace.severe(sname, mname, "SQL message : " + e.getMessage());
        Trace.throwing(sname, mname, e);
    }

    /**
     * This method displays all information of an SQL exception and a custom message.
     * Display the sql error code, state, sql message and ultimately the stacktrace of the Exception
     *
     * @param message custom message to display
     * @param e       SQLException that you want to display
     */
    public static void displaySqlException(final String message, final SQLException e) {
        final String mname = "displaySqlException";

        Trace.severe(sname, mname, "Message     : " + message);
        Trace.severe(sname, mname, "Error code  : " + e.getErrorCode());
        Trace.severe(sname, mname, "SQL state   : " + e.getSQLState());
        Trace.severe(sname, mname, "SQL message : " + e.getMessage());
        Trace.throwing(sname, mname, e);
    }

    // ======================================
    // =         Getters and Setters        =
    // ======================================
    protected String getCname() {
        return _cname;
    }
}
