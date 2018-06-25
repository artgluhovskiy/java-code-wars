package org.art.projects.java_code_wars.dao.impl;

import org.art.projects.java_code_wars.dao.UserDao;
import org.art.projects.java_code_wars.dao.exceptions.DAOSystemException;
import org.art.projects.java_code_wars.dao.db.ConnectionPoolManager;
import org.art.projects.java_code_wars.entities.DifficultyGroup;
import org.art.projects.java_code_wars.entities.User;
import org.art.projects.java_code_wars.services.UserService;
import org.art.projects.java_code_wars.services.exceptions.ServiceBusinessException;
import org.art.projects.java_code_wars.services.exceptions.ServiceSystemException;
import org.art.projects.java_code_wars.services.impl.UserServiceImpl;
import org.art.projects.java_code_wars.web.auth.Encoder;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.Date;

import static org.junit.Assert.assertEquals;

public class UserDaoImplTest {


    private ConnectionPoolManager connPool = ConnectionPoolManager.getInstance();

    private ThreadLocal<Connection> connectionHolder = connPool.getConnectionHolder();

    private UserDao userDao = UserDaoImpl.getInstance();

    private UserService userService = UserServiceImpl.getInstance();

    @Before
    public void initTest() throws ServiceSystemException {
        //Create 'users' table if not exists
        userService.createUsersTable();
    }

    @Test
    public void save() throws Exception {
        Connection conn = connPool.getConnection();
        connectionHolder.set(conn);

        //Amount of users in the database before saving
        int userAmount = userDao.getAllUsers().size();

        User user = new User("Sharks", "gooder", "8273gds", "Allen",
                "Swift", "swift@gmail.com", new Date(System.currentTimeMillis()), "user",
                "ACTIVE", UserServiceImpl.toSQLDate("24-02-1993"), DifficultyGroup.BEGINNER.toString());

        ((UserServiceImpl) userService).startTransaction();
        userDao.save(user);

        assertEquals(userAmount + 1, userDao.getAllUsers().size());

        ((UserServiceImpl) userService).backTransaction();
    }

    @Test
    public void get() throws Exception {
        Connection conn = connPool.getConnection();
        connectionHolder.set(conn);

        User user = new User("Sharks", "gooder", Encoder.encode("8273gds"), "Allen",
                "Swift", "swift@gmail.com", new Date(System.currentTimeMillis()), "user",
                "ACTIVE", UserServiceImpl.toSQLDate("24-02-1993"), DifficultyGroup.BEGINNER.toString());

        ((UserServiceImpl) userService).startTransaction();

        User user1 = userDao.save(user);
        User user2 = userDao.get(user1.getUserID());
        assertEquals(user1, user2);

        ((UserServiceImpl) userService).backTransaction();
    }

    @Test
    public void update() throws Exception {
        Connection conn = connPool.getConnection();
        connectionHolder.set(conn);

        User user = new User("Sharks", "gooder", Encoder.encode("8273gds"), "Allen",
                "Swift", "swift@gmail.com", new Date(System.currentTimeMillis()), "user",
                "ACTIVE", UserServiceImpl.toSQLDate("24-02-1993"), DifficultyGroup.BEGINNER.toString());

        ((UserServiceImpl) userService).startTransaction();

        User user1 = userDao.save(user);
        user1.setRating(12);
        user1.setEmail("storm@gmail.com");
        user1.setFName("Ronny");
        userDao.update(user1);
        User user2 = userDao.get(user1.getUserID());

        assertEquals(12, user2.getRating());
        assertEquals("storm@gmail.com", user2.getEmail());
        assertEquals("Ronny", user2.getFName());

        ((UserServiceImpl) userService).backTransaction();
    }

    @Test
    public void delete() throws Exception {
        Connection conn = connPool.getConnection();
        connectionHolder.set(conn);

        User user = new User("Sharks", "gooder", Encoder.encode("8273gds"), "Allen",
                "Swift", "swift@gmail.com", new Date(System.currentTimeMillis()), "user",
                "ACTIVE", UserServiceImpl.toSQLDate("24-02-1993"), DifficultyGroup.BEGINNER.toString());

        ((UserServiceImpl) userService).startTransaction();

        int userAmount = userDao.getAllUsers().size();
        user = userDao.save(user);
        assertEquals(userAmount + 1, userDao.getAllUsers().size());
        userDao.delete(user.getUserID());
        assertEquals(userAmount, userDao.getAllUsers().size());

        ((UserServiceImpl) userService).backTransaction();
    }

    @Test
    public void getUserByLogin() throws Exception {
        Connection conn = connPool.getConnection();
        connectionHolder.set(conn);

        User user = new User("Sharks", "gooder", Encoder.encode("8273gds"), "Allen",
                "Swift", "swift@gmail.com", new Date(System.currentTimeMillis()), "user",
                "ACTIVE", UserServiceImpl.toSQLDate("24-02-1993"), DifficultyGroup.BEGINNER.toString());

        ((UserServiceImpl) userService).startTransaction();

        user = userDao.save(user);
        User user1 = userDao.getUserByLogin("gooder");

        assertEquals(user.getLogin(), user1.getLogin());

        ((UserServiceImpl) userService).backTransaction();
    }

    @Test(expected = ServiceBusinessException.class)
    public void noUserInDatabase() throws Exception {
        UserService userService = UserServiceImpl.getInstance();
        userService.get(5);
    }

    @Test
    public void getUser() throws DAOSystemException {
        Connection conn = connPool.getConnection();
        connectionHolder.set(conn);

        ((UserServiceImpl) userService).startTransaction();

        User user = userDao.get(1);
        System.out.println(user);

        ((UserServiceImpl) userService).backTransaction();
    }
}

