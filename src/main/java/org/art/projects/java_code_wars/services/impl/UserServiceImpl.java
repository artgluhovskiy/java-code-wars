package org.art.projects.java_code_wars.services.impl;

import org.art.projects.java_code_wars.dao.JavaTaskDao;
import org.art.projects.java_code_wars.dao.TaskOrderDao;
import org.art.projects.java_code_wars.dao.UserDao;
import org.art.projects.java_code_wars.dao.exceptions.DAOSystemException;
import org.art.projects.java_code_wars.dao.impl.JavaTaskDaoImpl;
import org.art.projects.java_code_wars.dao.impl.TaskOrderDaoImpl;
import org.art.projects.java_code_wars.dao.impl.UserDaoImpl;
import org.art.projects.java_code_wars.dao.db.ConnectionPoolManager;
import org.art.projects.java_code_wars.entities.User;
import org.art.projects.java_code_wars.services.TransactionManager;
import org.art.projects.java_code_wars.services.UserService;
import org.art.projects.java_code_wars.services.exceptions.ServiceBusinessException;
import org.art.projects.java_code_wars.services.exceptions.ServiceSystemException;

import java.sql.Connection;
import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;

public class UserServiceImpl extends TransactionManager implements UserService {

    private UserDao userDao;

    private TaskOrderDao orderDao;

    private JavaTaskDao taskDao;

    private ConnectionPoolManager connPool;

    private static volatile UserService instance;

    private UserServiceImpl() {
        LOG.info("UserServiceImpl instantiation...");
        connPool = ConnectionPoolManager.getInstance();
        connectionHolder = connPool.getConnectionHolder();
        userDao = UserDaoImpl.getInstance();
        taskDao = JavaTaskDaoImpl.getInstance();
        orderDao = TaskOrderDaoImpl.getInstance();
    }

    public static UserService getInstance() {
        UserService userService = instance;
        if (userService == null) {
            synchronized (UserServiceImpl.class) {
                userService = instance;
                if (userService == null) {
                    instance = userService = new UserServiceImpl();
                }
            }
        }
        return userService;
    }

    @Override
    public User save(User user) throws ServiceSystemException {
        Connection conn = connPool.getConnection();
        connectionHolder.set(conn);
        try {
            startTransaction();
            userDao.save(user);
            endTransaction();
        } catch (DAOSystemException e) {
            tryRollBackTransaction(e);
            LOG.info("Exception while saving user into database!", e);
            throw new ServiceSystemException("Exception while saving user into database!", e);
        } finally {
            ConnectionPoolManager.close(conn);
            connectionHolder.remove();
        }
        return user;
    }

    @Override
    public User get(long id) throws ServiceSystemException, ServiceBusinessException {
        User user;
        Connection conn = connPool.getConnection();
        connectionHolder.set(conn);
        try {
            startTransaction();
            user = userDao.get(id);
            endTransaction();
            if (user == null) {
                throw new ServiceBusinessException("No such user in the database!");
            }
        } catch (DAOSystemException e) {
            LOG.info("Cannot get user from the database! ID: " + id, e);
            throw new ServiceSystemException("Cannot get user from the database! ID: " + id, e);
        } finally {
            ConnectionPoolManager.close(conn);
            connectionHolder.remove();
        }
        return user;
    }

    @Override
    public void update(User user) throws ServiceSystemException, ServiceBusinessException {
        Connection conn = connPool.getConnection();
        connectionHolder.set(conn);
        try {
            startTransaction();
            int amount = userDao.update(user);
            endTransaction();
            if (amount == 0) {
                throw new ServiceBusinessException("Cannot find user with such ID");
            }
        } catch (DAOSystemException e) {
            tryRollBackTransaction(e);
            LOG.info("Cannot update user in the database!", e);
            throw new ServiceSystemException("Cannot update user in the database!", e);
        } finally {
            ConnectionPoolManager.close(conn);
            connectionHolder.remove();
        }
    }

    @Override
    public int delete(long id) throws ServiceBusinessException, ServiceSystemException {
        Connection conn = connPool.getConnection();
        int num;
        connectionHolder.set(conn);
        try {
            startTransaction();
            num = userDao.delete(id);
            endTransaction();
            if (num == 0) {
                throw new ServiceBusinessException("No such user in the database!");
            }
        } catch (DAOSystemException e) {
            tryRollBackTransaction(e);
            LOG.info("Can't delete user from the database!", e);
            throw new ServiceSystemException("Can't delete user from the database!", e);
        } finally {
            ConnectionPoolManager.close(conn);
            connectionHolder.remove();
        }
        return num;
    }

    @Override
    public List<User> getUsersByClanName(String clanName) throws ServiceBusinessException, ServiceSystemException {
        Connection conn = connPool.getConnection();
        List<User> usersList;
        connectionHolder.set(conn);
        try {
            startTransaction();
            usersList = userDao.getUsersByClanName(clanName);
            endTransaction();
            if (usersList.size() == 0) {
                throw new ServiceBusinessException("No user was found!");
            }
        } catch (DAOSystemException e) {
            LOG.info("Cannot get users from the database!", e);
            throw new ServiceSystemException("Cannot get users from the database!", e);
        } finally {
            ConnectionPoolManager.close(conn);
            connectionHolder.remove();
        }
        return usersList;
    }

    @Override
    public User getUserByLogin(String login) throws ServiceBusinessException, ServiceSystemException {
        User user;
        Connection conn = connPool.getConnection();
        connectionHolder.set(conn);
        try {
            startTransaction();
            user = userDao.getUserByLogin(login);
            endTransaction();
            if (user == null) {
                throw new ServiceBusinessException("No user was found!");
            }
        } catch (DAOSystemException e) {
            LOG.info("Cannot get user from the database!", e);
            throw new ServiceSystemException("Cannot get user from the database!", e);
        } finally {
            ConnectionPoolManager.close(conn);
            connectionHolder.remove();
        }
        return user;
    }

    @Override
    public List<User> getTopUsers(int usersAmount) throws ServiceBusinessException, ServiceSystemException {
        Connection conn = connPool.getConnection();
        List<User> usersList;
        connectionHolder.set(conn);
        try {
            startTransaction();
            usersList = userDao.getTopUsers(usersAmount);
            endTransaction();
            if (usersList.size() == 0) {
                throw new ServiceBusinessException("No users were found!");
            }
        } catch (DAOSystemException e) {
            LOG.info("Cannot get users from the database!", e);
            throw new ServiceSystemException("Cannot get users from the database!", e);
        } finally {
            ConnectionPoolManager.close(conn);
            connectionHolder.remove();
        }
        return usersList;
    }

    @Override
    public List<User> getAllUsers() throws ServiceBusinessException, ServiceSystemException {
        Connection conn = connPool.getConnection();
        List<User> usersList;
        connectionHolder.set(conn);
        try {
            startTransaction();
            usersList = userDao.getAllUsers();
            endTransaction();
            if (usersList.size() == 0) {
                throw new ServiceBusinessException("No users were found!");
            }
        } catch (DAOSystemException e) {
            LOG.info("Cannot get users from the database!", e);
            throw new ServiceSystemException("Cannot get users from the database!", e);
        } finally {
            ConnectionPoolManager.close(conn);
            connectionHolder.remove();
        }
        return usersList;
    }

    @Override
    public void createUsersTable() throws ServiceSystemException {
        Connection conn = connPool.getConnection();
        connectionHolder.set(conn);
        try {
            userDao.createUsersTable();
        } catch (DAOSystemException e) {
            LOG.info("Cannot create users table in the database!", e);
            throw new ServiceSystemException("Cannot create users table in the database!", e);
        } finally {
            ConnectionPoolManager.close(conn);
            connectionHolder.remove();
        }
    }

    @Override
    public void deleteUsersTable() throws ServiceSystemException {
        Connection conn = connPool.getConnection();
        connectionHolder.set(conn);
        try {
            userDao.deleteUsersTable();
        } catch (DAOSystemException e) {
            LOG.info("Cannot delete users table from the database!", e);
            throw new ServiceSystemException("Cannot delete users table from the database!", e);
        } finally {
            ConnectionPoolManager.close(conn);
            connectionHolder.remove();
        }
    }

    /**
     * Method converts date from string to {@link Date}
     *
     * @param date date string in appropriate format
     * @return {@link Date}, converted from string
     */
    public static Date toSQLDate(String date) {
        LocalDate birthDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        int day = birthDate.getDayOfMonth();
        int year = birthDate.getYear();
        int month = birthDate.getMonthValue() - 1;
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        return new Date(calendar.getTimeInMillis());
    }

    /**
     * Method define user's age from his birth date
     *
     * @param birthDate user's birth date
     * @return user's age (in years)
     */
    public static int defineUserAge(Date birthDate) {
        LocalDate localBirthDate = birthDate.toLocalDate();
        LocalDate today = LocalDate.now();
        Period period = Period.between(localBirthDate, today);
        return period.getYears();
    }
}
