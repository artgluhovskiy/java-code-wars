package org.art.projects.java_code_wars.services;

import org.apache.log4j.Logger;
import org.art.projects.java_code_wars.dao.exceptions.DAOSystemException;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class TransactionManager {

    protected final Logger LOG = Logger.getLogger(this.getClass());

    protected ThreadLocal<Connection> connectionHolder;

    public void startTransaction() throws DAOSystemException {
        try {
            Connection conn = connectionHolder.get();
            conn.setAutoCommit(false);
            System.out.println(Thread.currentThread().getName() + " starts transaction! With connection: " + conn);
        } catch (SQLException e) {
            LOG.info("Cannot start transaction!");
            throw new DAOSystemException("Cannot start transaction!", e);
        }

    }

    public void endTransaction() throws DAOSystemException {
        try {
            Connection conn = connectionHolder.get();
            conn.commit();
            System.out.println(Thread.currentThread().getName() + " ends transaction! With connection: " + conn);
        } catch (SQLException e) {
            LOG.info("Cannot end transaction!");
            throw new DAOSystemException("Cannot end transaction!", e);
        }

    }

    public void backTransaction() throws DAOSystemException {
        try {
            connectionHolder.get().rollback();
            System.out.println(Thread.currentThread().getName() + " rolls back transaction!");
        } catch (SQLException e) {
            LOG.info("Cannot roll back transaction");
            throw new DAOSystemException("Cannot roll back transaction", e);
        }
    }

    protected void tryRollBackTransaction(Exception e) {
        try {
            backTransaction();
        } catch (DAOSystemException e1) {
            LOG.info("Exception while rolling back the transaction! This exception was added to suppressed.", e);
            e.addSuppressed(e1);
        }
    }
}
