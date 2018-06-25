package org.art.projects.java_code_wars.services.exceptions;

/**
 * In most cases {@code ServiceException} is actually
 * a wrapper for {@link org.art.projects.java_code_wars.dao.exceptions.DAOSystemException}
 * and some other types of exceptions in Service classes
 */
public class ServiceSystemException extends ServiceException {

    public ServiceSystemException(String message, Throwable cause) {
        super(message, cause);
    }
}
