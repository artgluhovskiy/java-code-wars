package org.art.projects.java_code_wars.dao;

import org.art.projects.java_code_wars.dao.exceptions.DAOSystemException;
import org.art.projects.java_code_wars.dto.OrderDTO;
import org.art.projects.java_code_wars.entities.TaskOrder;
import org.art.projects.java_code_wars.entities.User;

import java.util.List;

/**
 * TaskOrderDao interface with special methods
 */
public interface TaskOrderDao extends DAO<TaskOrder> {

    /**
     * Method returns the list of the orders {@link OrderDTO} with the tasks solved by user
     *
     * @param id user ID
     * @return the list of the tasks solved by user
     * @throws DAOSystemException if {@link java.sql.SQLException}
     *                            was thrown during the task orders reading from the database
     */
    List<OrderDTO> getUserTaskOrders(long id) throws DAOSystemException;

    /**
     * Method finds the order with the task which has not already solved in
     * the "task_orders" table
     *
     * @param user the user whose order you need to find
     * @return task order with the task which has not already solved
     * @throws DAOSystemException if {@link java.sql.SQLException}
     *                            was thrown during the task order reading from the database
     */
    TaskOrder getNotSolvedOrder(User user) throws DAOSystemException;

    /**
     * Creation of the table "task_orders" in the database
     *
     * @throws DAOSystemException if {@link java.sql.SQLException}
     *                            was thrown during the table creation in the database
     */
    void createTaskOrderTable() throws DAOSystemException;

    /**
     * Deleting of the table "task_orders" from the database
     *
     * @throws DAOSystemException if {@link java.sql.SQLException}
     *                            was thrown during the table deleting from the database
     */
    void deleteTaskOrderTable() throws DAOSystemException;
}
