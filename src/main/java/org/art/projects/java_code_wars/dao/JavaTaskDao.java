package org.art.projects.java_code_wars.dao;

import org.art.projects.java_code_wars.dao.exceptions.DAOSystemException;
import org.art.projects.java_code_wars.entities.JavaTask;

import java.util.List;

/**
 * JavaTaskDao interface with special methods
 */
public interface JavaTaskDao extends DAO<JavaTask> {

    /**
     * Method finds <i>next</i> java task in the database (with
     * appropriate difficulty) with ID going <b>after</b> the ID
     * of the solved task
     *
     * @param difGroup group of difficulty of the task (can be "BEGINNER",
     *                 "EXPERIENCED", "EXPERT")
     * @param taskID ID of the solved task
     * @return JavaTask with "id" (with appropriate difficulty) going
     * after the task which was solved by the user
     * @throws DAOSystemException if {@link java.sql.SQLException}
     *                            was thrown during the task reading from the database
     */
    JavaTask getNextTaskByDiffGroup(String difGroup, long taskID) throws DAOSystemException;

    /**
     * Method finds the most popular (with the highest popularity) tasks in the database
     *
     * @param taskAmount amount (from top list) of popular tasks
     * @return the list of the most popular java tasks
     * @throws DAOSystemException if {@link java.sql.SQLException}
     *                            was thrown during the tasks reading from the database
     */
    List<JavaTask> getPopularJavaTasks(int taskAmount) throws DAOSystemException;

    /**
     * Get all tasks from the database
     *
     * @return the list of all tasks from database
     * @throws DAOSystemException if {@link java.sql.SQLException}
     *                            was thrown during the tasks reading from the database
     */
    List<JavaTask> getAll() throws DAOSystemException;

    /**
     * Creation of the table "java_tasks" in the database
     *
     * @throws DAOSystemException if {@link java.sql.SQLException}
     *                            was thrown during the table creation in the database
     */
    void createJavaTasksTable() throws DAOSystemException;

    /**
     * Deleting of the table "java_tasks" from the database
     *
     * @throws DAOSystemException if {@link java.sql.SQLException}
     *                            was thrown during the table deleting from the database
     */
    void deleteJavaTasksTable() throws DAOSystemException;
}
