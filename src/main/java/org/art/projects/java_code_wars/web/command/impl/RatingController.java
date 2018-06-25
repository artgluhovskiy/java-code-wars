package org.art.projects.java_code_wars.web.command.impl;

import org.apache.log4j.Logger;
import org.art.projects.java_code_wars.entities.User;
import org.art.projects.java_code_wars.services.UserService;
import org.art.projects.java_code_wars.services.exceptions.ServiceBusinessException;
import org.art.projects.java_code_wars.services.exceptions.ServiceSystemException;
import org.art.projects.java_code_wars.services.impl.UserServiceImpl;
import org.art.projects.java_code_wars.web.command.Controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class RatingController implements Controller {

    private static final Logger LOG = Logger.getLogger(RatingController.class);

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        UserService userService = UserServiceImpl.getInstance();
        HttpSession session = req.getSession();
        try {
            List<User> userList = userService.getTopUsers(10);
            session.setAttribute("topList", userList);
        } catch (ServiceBusinessException e) {
            req.setAttribute("errorMsg", "Can't find any users in the database!");
            req.getRequestDispatcher(MAIN_PAGE).forward(req, resp);
            LOG.info("RatingController: Can't find users in the database");
            return;
        } catch (ServiceSystemException e) {
            req.setAttribute("errorMsg", SERVER_ERROR_MESSAGE);
            req.getRequestDispatcher(ERROR_PAGE).forward(req, resp);
            LOG.error("RatingController: Service System Exception", e);
            return;
        }
        req.getRequestDispatcher(MAIN_PAGE).forward(req, resp);
    }
}
