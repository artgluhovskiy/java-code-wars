package org.art.projects.java_code_wars.dao.db;

/**
 * {@code DbManagerException} is thrown in case of
 * problems while getting connection to the database.
 */
public class DbManagerException extends RuntimeException {

    public DbManagerException(String message) {
        super(message);
    }

    public DbManagerException(Throwable e) {
        super(e);
    }

    public DbManagerException(String message, Throwable cause) {
        super(message, cause);
    }
}
