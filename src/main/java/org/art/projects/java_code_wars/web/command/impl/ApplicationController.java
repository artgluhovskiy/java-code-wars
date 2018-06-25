package org.art.projects.java_code_wars.web.command.impl;

import org.apache.log4j.Logger;
import org.art.projects.java_code_wars.entities.JavaTask;
import org.art.projects.java_code_wars.entities.User;
import org.art.projects.java_code_wars.services.JavaTaskService;
import org.art.projects.java_code_wars.services.exceptions.ServiceBusinessException;
import org.art.projects.java_code_wars.services.exceptions.ServiceSystemException;
import org.art.projects.java_code_wars.services.impl.JavaTaskServiceImpl;
import org.art.projects.java_code_wars.web.command.Controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class ApplicationController implements Controller {

    private static final Logger LOG = Logger.getLogger(ApplicationController.class);

    private JavaTaskService taskService = JavaTaskServiceImpl.getInstance();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        JavaTask javaTask;
        try {
            //If no tasks were solved
            if (user.getRating() == 1) {
                //Requiring of the first task for user (with task ID > 0) after registration
                if (!hasNotSolvedTask(user)) {
                    javaTask = taskService.getNextTaskByDiffGroup(user, 0);
                } else {
                    javaTask = taskService.getNotSolvedTask(user);
                }
            } else {
                javaTask = taskService.getNotSolvedTask(user);
            }
        } catch (ServiceBusinessException e) {
            req.setAttribute("errorMsg", "We can't find a new task for you.<br>It seems that you solved all of them!");
            session.setAttribute("prevPage", "Statistics");
            req.getRequestDispatcher(ERROR_PAGE).forward(req, resp);
            LOG.error("ApplicationController: catch block: Cannot find appropriate task in database");
            return;
        } catch (ServiceSystemException e) {
            req.setAttribute("errorMsg", SERVER_ERROR_MESSAGE);
            session.setAttribute("prevPage", "Statistics");
            req.getRequestDispatcher(ERROR_PAGE).forward(req, resp);
            LOG.info("ApplicationController: catch block: Service System Exception", e);
            return;
        }
        session.removeAttribute("errorMsg");
        session.setAttribute("task", javaTask);
        req.getRequestDispatcher(MAIN_PAGE).forward(req, resp);
    }

    private boolean hasNotSolvedTask(User user) throws ServiceSystemException {
        try {
            taskService.getNotSolvedTask(user);
        } catch (ServiceBusinessException e) {
            //User has no solved task in database
            return false;
        }
        return true;
    }
}
