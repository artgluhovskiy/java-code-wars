package org.art.projects.java_code_wars.web.command.impl;

import org.apache.log4j.Logger;
import org.art.projects.java_code_wars.entities.User;
import org.art.projects.java_code_wars.services.UserService;
import org.art.projects.java_code_wars.services.exceptions.ServiceBusinessException;
import org.art.projects.java_code_wars.services.exceptions.ServiceSystemException;
import org.art.projects.java_code_wars.services.impl.UserServiceImpl;
import org.art.projects.java_code_wars.web.auth.Encoder;
import org.art.projects.java_code_wars.web.command.Controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginController implements Controller {

    private static final Logger LOG = Logger.getLogger(LoginController.class);

    private UserService userService = UserServiceImpl.getInstance();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        HttpSession session = req.getSession();
        if ("".equals(login) || "".equals(password) || login == null || password == null) {
            req.setAttribute("errorMsg", "Invalid login or password!");
            req.getRequestDispatcher(LOGIN_PAGE).forward(req, resp);
            return;
        }
        User user;
        String contextPath = req.getContextPath();
        try {
            user = userService.getUserByLogin(login);
        } catch (ServiceBusinessException e) {
            //If no user with such login was found
            req.setAttribute("errorMsg", "Invalid login or password!");
            req.getRequestDispatcher(LOGIN_PAGE).forward(req, resp);
            LOG.info("LoginController: Invalid login or password");
            return;
        } catch (ServiceSystemException e) {
            req.setAttribute("errorMsg", SERVER_ERROR_MESSAGE);
            req.getRequestDispatcher(LOGIN_PAGE).forward(req, resp);
            LOG.error("LoginController: Service System Exception", e);
            return;
        }
        if (user.getPassword().equals(Encoder.encode(password)) && user.getStatus().equals("ACTIVE")) {
            session.setAttribute("user", user);
            if (user.getRole().equals("admin")) {
                resp.sendRedirect(contextPath + "/frontController?command=admin");
                return;
            }
            resp.sendRedirect(contextPath + "/frontController?command=statistics");
            return;
        } else if (user.getStatus().equals("NOT ACTIVE")) {
            req.setAttribute("errorMsg", "User with such login and password is not active now! Please, contact with technical support!");
            req.getRequestDispatcher(LOGIN_PAGE).forward(req, resp);
        } else {
            //In case of incorrect password
            req.setAttribute("errorMsg", "Invalid login or password!");
            req.getRequestDispatcher(LOGIN_PAGE).forward(req, resp);
            return;
        }
    }
}
