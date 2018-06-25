package org.art.projects.java_code_wars.services.impl;

import org.art.projects.java_code_wars.dao.JavaTaskDao;
import org.art.projects.java_code_wars.dao.TaskOrderDao;
import org.art.projects.java_code_wars.dao.UserDao;
import org.art.projects.java_code_wars.dao.exceptions.DAOSystemException;
import org.art.projects.java_code_wars.dao.impl.JavaTaskDaoImpl;
import org.art.projects.java_code_wars.dao.impl.TaskOrderDaoImpl;
import org.art.projects.java_code_wars.dao.impl.UserDaoImpl;
import org.art.projects.java_code_wars.dao.db.ConnectionPoolManager;
import org.art.projects.java_code_wars.entities.JavaTask;
import org.art.projects.java_code_wars.entities.TaskOrder;
import org.art.projects.java_code_wars.entities.User;
import org.art.projects.java_code_wars.services.JavaTaskService;
import org.art.projects.java_code_wars.services.TransactionManager;
import org.art.projects.java_code_wars.services.exceptions.ServiceBusinessException;
import org.art.projects.java_code_wars.services.exceptions.ServiceSystemException;

import java.sql.Connection;
import java.util.List;

public class JavaTaskServiceImpl extends TransactionManager implements JavaTaskService {

    private JavaTaskDao javaTaskDao;

    private TaskOrderDao orderDao;

    private UserDao userDao;

    private ConnectionPoolManager connPool;

    private static volatile JavaTaskService instance;

    private JavaTaskServiceImpl() {
        LOG.info("JavaTaskServiceImpl instantiation...");
        connPool = ConnectionPoolManager.getInstance();
        connectionHolder = connPool.getConnectionHolder();
        javaTaskDao = JavaTaskDaoImpl.getInstance();
        orderDao = TaskOrderDaoImpl.getInstance();
        userDao = UserDaoImpl.getInstance();
    }

    public static JavaTaskService getInstance() {
        JavaTaskService javaTaskService = instance;
        if (javaTaskService == null) {
            synchronized (JavaTaskServiceImpl.class) {
                javaTaskService = instance;
                if (javaTaskService == null) {
                    instance = javaTaskService = new JavaTaskServiceImpl();
                }
            }
        }
        return javaTaskService;
    }

    @Override
    public JavaTask save(JavaTask javaTask) throws ServiceSystemException {
        Connection conn = connPool.getConnection();
        connectionHolder.set(conn);
        try {
            startTransaction();
            javaTaskDao.save(javaTask);
            endTransaction();
        } catch (DAOSystemException e) {
            tryRollBackTransaction(e);
            LOG.info("Exception while saving task into database!", e);
            throw new ServiceSystemException("Exception while saving task into database!", e);
        } finally {
            ConnectionPoolManager.close(conn);
            connectionHolder.remove();
        }
        return javaTask;
    }

    @Override
    public JavaTask get(long id) throws ServiceSystemException, ServiceBusinessException {
        Connection conn = connPool.getConnection();
        connectionHolder.set(conn);
        JavaTask javaTask;
        try {
            startTransaction();
            javaTask = javaTaskDao.get(id);
            endTransaction();
            if (javaTask == null) {
                throw new ServiceBusinessException("No task with such ID was found!");
            }
        } catch (DAOSystemException e) {
            LOG.info("Exception while getting task from database! Task ID: " + id, e);
            throw new ServiceSystemException("Exception while getting task from database! Task ID: " + id, e);
        } finally {
            ConnectionPoolManager.close(conn);
            connectionHolder.remove();
        }
        return javaTask;
    }

    @Override
    public void update(JavaTask javaTask) throws ServiceSystemException, ServiceBusinessException {
        Connection conn = connPool.getConnection();
        connectionHolder.set(conn);
        try {
            startTransaction();
            int updRows = javaTaskDao.update(javaTask);
            endTransaction();
            if (updRows == 0) {
                throw new ServiceBusinessException("No task with such ID was found!");
            }
        } catch (DAOSystemException e) {
            tryRollBackTransaction(e);
            LOG.info("Exception while updating task in database!", e);
            throw new ServiceSystemException("Exception while updating task in database!", e);
        } finally {
            ConnectionPoolManager.close(conn);
            connectionHolder.remove();
        }
    }

    @Override
    public int delete(long id) throws ServiceSystemException, ServiceBusinessException {
        Connection conn = connPool.getConnection();
        connectionHolder.set(conn);
        int delRows;
        try {
            startTransaction();
            delRows = javaTaskDao.delete(id);
            endTransaction();
            if (delRows == 0) {
                throw new ServiceBusinessException("No task with such ID was found!");
            }
        } catch (DAOSystemException e) {
            tryRollBackTransaction(e);
            LOG.info("Exception while deleting task from database!", e);
            throw new ServiceSystemException("Exception while deleting task from database!", e);
        } finally {
            ConnectionPoolManager.close(conn);
            connectionHolder.remove();
        }
        return delRows;
    }

    @Override
    public JavaTask getNextTaskByDiffGroup(User user, long solvedTaskID) throws ServiceBusinessException, ServiceSystemException {
        Connection conn = connPool.getConnection();
        connectionHolder.set(conn);
        JavaTask newTask;
        TaskOrder order;
        try {
            startTransaction();
            //Getting next task in database
            newTask = javaTaskDao.getNextTaskByDiffGroup(user.getLevel(), solvedTaskID);
            //After registration user rating increases by 1
            if (solvedTaskID == 0) {

            }
            //Creation of new "NOT SOLVED" task order with new task
            order = new TaskOrder(user.getUserID(), newTask.getTaskID(), "NOT SOLVED");
            TaskOrder order1 = orderDao.save(order);
            endTransaction();
            if (newTask == null) {
                throw new ServiceBusinessException("No task with such ID was found!");
            }
        } catch (DAOSystemException e) {
            tryRollBackTransaction(e);
            LOG.info("Exception while getting task from database!", e);
            throw new ServiceSystemException("Exception while getting task from database!", e);
        } finally {
            ConnectionPoolManager.close(conn);
            connectionHolder.remove();
        }
        return newTask;
    }

    @Override
    public JavaTask getNotSolvedTask(User user) throws ServiceSystemException, ServiceBusinessException {
        Connection conn = connPool.getConnection();
        connectionHolder.set(conn);
        JavaTask javaTask;
        TaskOrder order;
        try {
            startTransaction();
            order = orderDao.getNotSolvedOrder(user);
            if (order == null) {
                throw new ServiceBusinessException("No order with not solved task was found!");
            }
            javaTask = javaTaskDao.get(order.getTaskID());
            endTransaction();
            if (javaTask == null) {
                throw new ServiceBusinessException("No task with such ID was found (getNotSolvedTask method)!");
            }
        } catch (DAOSystemException e) {
            LOG.info("Exception while getting not solved task from database!", e);
            throw new ServiceSystemException("Exception while getting not solved task from database!", e);
        } finally {
            ConnectionPoolManager.close(conn);
            connectionHolder.remove();
        }
        return javaTask;
    }

    @Override
    public List<JavaTask> getPopularJavaTasks(int taskAmount) throws ServiceBusinessException, ServiceSystemException {
        Connection conn = connPool.getConnection();
        connectionHolder.set(conn);
        List<JavaTask> taskList;
        try {
            startTransaction();
            taskList = javaTaskDao.getPopularJavaTasks(taskAmount);
            endTransaction();
            if (taskList.size() == 0) {
                throw new ServiceBusinessException("No tasks were found!");
            }
        } catch (DAOSystemException e) {
            LOG.info("Exception while getting popular tasks from database!", e);
            throw new ServiceSystemException("Exception while getting popular tasks from database!", e);
        } finally {
            ConnectionPoolManager.close(conn);
            connectionHolder.remove();
        }
        return taskList;
    }

    @Override
    public List<JavaTask> getAll() throws ServiceSystemException, ServiceBusinessException {
        Connection conn = connPool.getConnection();
        connectionHolder.set(conn);
        List<JavaTask> taskList;
        try {
            startTransaction();
            taskList = javaTaskDao.getAll();
            endTransaction();
            if (taskList.size() == 0) {
                throw new ServiceBusinessException("No tasks were found!");
            }
        } catch (DAOSystemException e) {
            LOG.info("Exception while getting all tasks from database!", e);
            throw new ServiceSystemException("Exception while getting all tasks from database!", e);
        } finally {
            ConnectionPoolManager.close(conn);
            connectionHolder.remove();
        }
        return taskList;
    }

    @Override
    public void createJavaTasksTable() throws ServiceSystemException {
        Connection conn = connPool.getConnection();
        connectionHolder.set(conn);
        try {
            javaTaskDao.createJavaTasksTable();
        } catch (DAOSystemException e) {
            LOG.info("Exception while creating tasks table in database!", e);
            throw new ServiceSystemException("Exception while creating tasks table in database!", e);
        } finally {
            ConnectionPoolManager.close(conn);
            connectionHolder.remove();
        }
    }

    @Override
    public void deleteJavaTasksTable() throws ServiceSystemException {
        Connection conn = connPool.getConnection();
        connectionHolder.set(conn);
        try {
            javaTaskDao.deleteJavaTasksTable();
        } catch (DAOSystemException e) {
            LOG.info("Exception while deleting tasks table in database!", e);
            throw new ServiceSystemException("Exception while deleting tasks table in database!", e);
        } finally {
            ConnectionPoolManager.close(conn);
            connectionHolder.remove();
        }
    }
}
