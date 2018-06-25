package org.art.projects.java_code_wars.dao;

import org.art.projects.java_code_wars.dao.exceptions.DAOSystemException;

/**
 * DAO interface with CRUD operations
 *
 * @param <T> generic type for entities
 */
public interface DAO<T> {

    /**
     * Method saves entity to the database
     *
     * @param t entity
     * @return entity with its ID from the database
     * @throws DAOSystemException if {@link java.sql.SQLException}
     *                            was thrown during the saving operation to the database
     */
    T save(T t) throws DAOSystemException;

    /**
     * Method reads (gets) entity from the database by its ID
     *
     * @param id entity's ID
     * @return entity with required ID
     * @throws DAOSystemException if {@link java.sql.SQLException}
     *                            was thrown during the reading operation from the database
     */
    T get(long id) throws DAOSystemException;

    /**
     * Method updates entity in the database
     *
     * @param t entity with fields you need to update
     * @return amount of updated entities
     * @throws DAOSystemException if {@link java.sql.SQLException}
     *                            was thrown during the updating operation in the database
     */
    int update(T t) throws DAOSystemException;

    /**
     * Method deletes entity from the database
     *
     * @param id entity ID you need to delete
     * @return amount of deleted entities
     * @throws DAOSystemException if {@link java.sql.SQLException}
     *                            was thrown during the deleting operation from the database
     */
    int delete(long id) throws DAOSystemException;
}
