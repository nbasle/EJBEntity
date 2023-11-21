package com.yaps.petstore.server.util.persistence;

/**
 * This interface lists all the constants that the system uses to get a Database connection.
 */
public interface DataAccessConstants {

    /**
     * Datasource from where to get connections.
     */
    String DATASOURCE = "java:/petstoreDS";

    /**
     * URL of where the database is located.
     */
    String URL_DB = "jdbc:mysql://localhost:3306/petstore";

    /**
     * Username to access the database.
     */
    String USER_DB = "root";

    /**
     * Password to access the database.
     */
    String PASSWD_DB = "";
}
