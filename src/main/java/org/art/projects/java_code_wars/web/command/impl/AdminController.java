package org.art.projects.java_code_wars.web.command.impl;

import org.apache.log4j.Logger;
import org.art.projects.java_code_wars.dto.OrderDTO;
import org.art.projects.java_code_wars.entities.JavaTask;
import org.art.projects.java_code_wars.entities.User;
import org.art.projects.java_code_wars.services.JavaTaskService;
import org.art.projects.java_code_wars.services.TaskOrderService;
import org.art.projects.java_code_wars.services.UserService;
import org.art.projects.java_code_wars.services.exceptions.ServiceBusinessException;
import org.art.projects.java_code_wars.services.exceptions.ServiceSystemException;
import org.art.projects.java_code_wars.services.impl.JavaTaskServiceImpl;
import org.art.projects.java_code_wars.services.impl.TaskOrderServiceImpl;
import org.art.projects.java_code_wars.services.impl.UserServiceImpl;
import org.art.projects.java_code_wars.web.command.Controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class AdminController implements Controller {

    private static final Logger LOG = Logger.getLogger(AdminController.class);

    private JavaTaskService taskService = JavaTaskServiceImpl.getInstance();
    private TaskOrderService orderService = TaskOrderServiceImpl.getInstance();
    private UserService userService = UserServiceImpl.getInstance();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        HttpSession session = req.getSession();
        @SuppressWarnings("unchecked")
        List<JavaTask> taskList = (List<JavaTask>) session.getAttribute("taskList");
        if (taskList == null) {
            readAllTasks(req);
        }
        String taskID = req.getParameter("taskID");
        if (taskID != null && !"".equals(taskID)) {
            updateTask(req);
            readAllTasks(req);
        }
        String userID = req.getParameter("userID");
        if (userID != null) {
            readUserInfo(req);
        }
        String updateUserID = req.getParameter("updateUserID");
        if (updateUserID != null && !"".equals(updateUserID)) {
            updateUser(req);
        }
        req.getRequestDispatcher(MAIN_PAGE).forward(req, resp);
    }

    private void updateTask(HttpServletRequest req) {
        JavaTask task;
        long taskID = Long.parseLong(req.getParameter("taskID"));
        String diffGroup = req.getParameter("diffGroup");
        String shortDescr = req.getParameter("shortDescr");
        String elapsedTime = req.getParameter("elapsedTime");
        String popularity = req.getParameter("popularity");
        try {
            task = taskService.get(taskID);
        } catch (ServiceSystemException e) {
            req.setAttribute("updateErrorMsg", SERVER_ERROR_MESSAGE);
            return;
        } catch (ServiceBusinessException e) {
            req.setAttribute("updateErrorMsg", "Can't find task in DB");
            return;
        }
        if (!"".equals(diffGroup)) {
            task.setDifficultyGroup(diffGroup);
        }
        if (!"".equals(shortDescr)) {
            task.setShortDescr(shortDescr);
        }
        if (!"".equals(elapsedTime)) {
            int elTime = Integer.parseInt(elapsedTime);
            task.setElapsedTime(elTime);
        }
        if (!"".equals(popularity)) {
            int pop = Integer.parseInt(popularity);
            task.setPopularity(pop);
        }
        try {
            taskService.update(task);
            req.setAttribute("updateStatusMsg", "Task was successfully updated!");
        } catch (ServiceSystemException e) {
            req.setAttribute("updateErrorMsg", SERVER_ERROR_MESSAGE);
            LOG.error("AdminController: updateTask(): catch block: Service System exception", e);
        } catch (ServiceBusinessException e) {
            req.setAttribute("updateErrorMsg", "Cannot update task!");
            LOG.info("AdminController: updateTask(): catch block: Cannot update task");
        }
    }

    private void readAllTasks(HttpServletRequest req) {
        try {
            List<JavaTask> taskList = taskService.getAll();
            req.getSession().setAttribute("taskList", taskList);
        } catch (ServiceSystemException e) {
            req.setAttribute("errorMsg", SERVER_ERROR_MESSAGE);
            LOG.error("AdminController: readAllTasks(): catch block: Service System exception", e);
        } catch (ServiceBusinessException e) {
            req.setAttribute("errorMsg", "There is no tasks in database");
            LOG.info("AdminController: readAllTasks(): catch block: No tasks were found");
        }
    }

    private void readUserInfo(HttpServletRequest req) {
        List<OrderDTO> orderList;
        User user;
        Long userID = Long.parseLong(req.getParameter("userID"));
        try {
            user = userService.get(userID);
            orderList = orderService.getUserTaskOrders(userID);
            req.getSession().setAttribute("userInfo", user);
            req.getSession().setAttribute("orderList", orderList);
        } catch (ServiceSystemException e) {
            req.getSession().removeAttribute("userInfo");
            req.getSession().removeAttribute("orderList");
            req.setAttribute("userErrorMsg", SERVER_ERROR_MESSAGE);
            LOG.error("AdminController: readUserInfo(): catch block: Service System exception", e);
        } catch (ServiceBusinessException e) {
            req.getSession().removeAttribute("userInfo");
            req.getSession().removeAttribute("orderList");
            req.setAttribute("userErrorMsg", "There is no user in database");
            LOG.info("AdminController: readUserInfo(): catch block: No users were found in database");
        }
    }

    private void updateUser(HttpServletRequest req) {
        User user;
        long userID = Long.parseLong(req.getParameter("updateUserID"));
        String level = req.getParameter("level");
        String role = req.getParameter("role");
        String status = req.getParameter("status");
        String rating = req.getParameter("rating");
        try {
            user = userService.get(userID);
        } catch (ServiceSystemException e) {
            req.setAttribute("userUpdateErrorMsg", SERVER_ERROR_MESSAGE);
            LOG.error("AdminController: updateUser(): catch block: Service System exception", e);
            return;
        } catch (ServiceBusinessException e) {
            req.setAttribute("userUpdateErrorMsg", "Can't find user in database");
            LOG.info("AdminController: updateUser(): catch block: No users were found in database");
            return;
        }
        if (!"".equals(level)) {
            user.setLevel(level);
        }
        if (!"".equals(role)) {
            user.setRole(role);
        }
        if (!"".equals(status)) {
            user.setStatus(status);
        }
        if (!"".equals(rating)) {
            int rat = Integer.parseInt(rating);
            user.setRating(rat);
        }
        try {
            userService.update(user);
            req.setAttribute("userUpdateStatusMsg", "User was successfully updated!");
        } catch (ServiceSystemException e) {
            req.setAttribute("userUpdateErrorMsg", SERVER_ERROR_MESSAGE);
            LOG.error("AdminController: updateUser(): catch block: Service System exception", e);
        } catch (ServiceBusinessException e) {
            req.setAttribute("userUpdateErrorMsg", "Cannot update user!");
            LOG.info("AdminController: updateUser(): catch block: Cannot update user");
        }
    }
}
