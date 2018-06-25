package org.art.projects.java_code_wars.dao.db;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Provides connections to the database with conventional
 * methods manually delegating them to {@link DriverManager}
 * (without the usage of Connection pool).
 */
public class ConnectionManager {

    private static final Logger LOG = Logger.getLogger(ConnectionManager.class);

    private String url;
    private String user;
    private String password;

    private static volatile boolean isDriverLoaded = false;

    private static volatile ConnectionManager instance;

    private ConnectionManager() {
        //Connection Manager initialization
        ResourceBundle rb;
        try {
            rb = ResourceBundle.getBundle("database");
            url = rb.getString("url");
            user = rb.getString("user");
            password = rb.getString("password");
        } catch (MissingResourceException e) {
            throw new DbManagerException("Missing resource file!", e);
        }

        //Get all loaded drivers (uses Service Loader API inside)
        Enumeration<Driver> myDrivers = DriverManager.getDrivers();
        while (myDrivers.hasMoreElements()) {
            Driver driver = myDrivers.nextElement();
            if (driver.getClass().getName().equals(rb.getString("driver"))) {
                LOG.info("Driver was loaded: " + driver);
                isDriverLoaded = true;
            }
        }
    }

    public static ConnectionManager getInstance() throws DbManagerException {
        ConnectionManager dataSource = instance;
        if (dataSource == null) {
            synchronized (ConnectionPoolManager.class) {
                dataSource = instance;
                if (dataSource == null) {
                    instance = dataSource = new ConnectionManager();
                }
            }
        }
        return dataSource;
    }

    public Connection getConnection() throws DbManagerException {
        if (!isDriverLoaded) {
            throw new DbManagerException("Driver wasn't loaded!");
        }
        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new DbManagerException("Error: could not get connection!");
        }
    }
}
