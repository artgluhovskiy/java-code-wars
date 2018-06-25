package org.art.projects.java_code_wars.dao.db;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.log4j.Logger;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Provides connections to the database
 * getting them from a connection pool (c3po).
 */
public class ConnectionPoolManager {

    private static final Logger LOG = Logger.getLogger(ConnectionPoolManager.class);

    private ComboPooledDataSource dataSource = new ComboPooledDataSource();

    private final ThreadLocal<Connection> connectionHolder  = new ThreadLocal<>();

    private static volatile ConnectionPoolManager instance;

    private ConnectionPoolManager() throws DbManagerException {
        //Connection Pool Manager initialization
        try {
            ResourceBundle rb = ResourceBundle.getBundle("database");
            String driver = rb.getString("driver");
            String url = rb.getString("url");
            String user = rb.getString("user");
            String password = rb.getString("password");

            dataSource.setDriverClass(driver);
            dataSource.setJdbcUrl(url);
            dataSource.setUser(user);
            dataSource.setPassword(password);
        } catch (MissingResourceException e) {
            throw new DbManagerException("Resource bundle issue (resource bundle file was not found or invalid prop key was used)!", e);
        } catch (PropertyVetoException e) {
            throw new DbManagerException("Properties for connection pool represent an unacceptable values!", e);
        }
        dataSource.setMinPoolSize(5);
        dataSource.setAcquireIncrement(5);
        dataSource.setMaxPoolSize(20);
        dataSource.setMaxStatements(180);
        dataSource.setMaxStatementsPerConnection(2);
    }

    public static ConnectionPoolManager getInstance() throws DbManagerException {
        ConnectionPoolManager dataSource = instance;
        if (dataSource == null) {
            synchronized (ConnectionPoolManager.class) {
                dataSource = instance;
                if (dataSource == null) {
                    instance = dataSource = new ConnectionPoolManager();
                }
            }
        }
        return dataSource;
    }

    public ThreadLocal<Connection> getConnectionHolder() {
        return connectionHolder;
    }

    public Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new DbManagerException("Could not get new connection!", e);
        }
    }

    public static void close(AutoCloseable res) {
        try {
            if (res != null) {
                res.close();
            }
        } catch (Exception e) {
            /* OK. C3P0 provides automatic connection closing */
            LOG.info("Exception while connection closing!", e);
        }
    }
}
