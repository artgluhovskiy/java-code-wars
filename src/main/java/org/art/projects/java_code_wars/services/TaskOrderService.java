package org.art.projects.java_code_wars.services;

import org.art.projects.java_code_wars.dao.exceptions.DAOSystemException;
import org.art.projects.java_code_wars.dto.OrderDTO;
import org.art.projects.java_code_wars.entities.JavaTask;
import org.art.projects.java_code_wars.entities.TaskOrder;
import org.art.projects.java_code_wars.entities.User;
import org.art.projects.java_code_wars.services.exceptions.ServiceBusinessException;
import org.art.projects.java_code_wars.services.exceptions.ServiceSystemException;

import java.util.List;

public interface TaskOrderService extends Service<TaskOrder> {

    /**
     * This service method returns the list of the orders {@link OrderDTO}
     * with the tasks solved by user
     *
     * @param id user ID
     * @return the list of the tasks solved by user
     * @throws ServiceSystemException   if {@link DAOSystemException} was thrown during
     *                                  the task orders reading from the database
     * @throws ServiceBusinessException if no java task orders were found in the database
     */
    List<OrderDTO> getUserTaskOrders(long id) throws ServiceSystemException, ServiceBusinessException;

    /**
     * Service method for creation of task order with a new task
     * with appropriate difficulty group. This method also provides
     * modifying of old task order (modifying of task order status,
     * algorithm execution time)
     *
     * @param user     user for whom you need to create a new task order
     * @param task     solved task
     * @param execTime algorithm execution time of solved task
     * @throws ServiceSystemException   if {@link DAOSystemException} was thrown during
     *                                  the creation of new task order in the database
     * @throws ServiceBusinessException if java task was not found in the database
     */
    void createNewOrder(User user, JavaTask task, long execTime) throws ServiceSystemException, ServiceBusinessException;

    /**
     * Creation of the table "task_orders" in the database
     *
     * @throws ServiceSystemException if {@link DAOSystemException} was thrown during
     *                                the creation of "task_order" table in the database
     */
    void createTaskOrderTable() throws ServiceSystemException;

    /**
     * Deleting of the table "task_orders" from the database
     *
     * @throws ServiceSystemException if {@link DAOSystemException} was thrown during
     *                                the deleting of "task_order" table in the database
     */
    void deleteTaskOrderTable() throws ServiceSystemException;
}
