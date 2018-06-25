package org.art.projects.java_code_wars.web.command.impl;

import org.apache.log4j.Logger;
import org.art.projects.java_code_wars.entities.DifficultyGroup;
import org.art.projects.java_code_wars.entities.User;
import org.art.projects.java_code_wars.services.UserService;
import org.art.projects.java_code_wars.services.exceptions.ServiceSystemException;
import org.art.projects.java_code_wars.services.impl.UserServiceImpl;
import org.art.projects.java_code_wars.web.auth.Encoder;
import org.art.projects.java_code_wars.web.command.Controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;

import static org.art.projects.java_code_wars.services.validators.UserValidator.validateUserData;

public class RegistrationController implements Controller {

    private static final Logger LOG = Logger.getLogger(RegistrationController.class);

    private UserService userService = UserServiceImpl.getInstance();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String fName = req.getParameter("fname");
        String lName = req.getParameter("lname");
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String email = req.getParameter("email");
        String clanName = req.getParameter("clan_name");
        String birth = req.getParameter("birth");
        String errorMsg = validateUserData(fName, lName, login, password, email, birth);
        if (errorMsg != null) {
            req.setAttribute("errorMsg", errorMsg);
            req.getRequestDispatcher(REGISTRATION_PAGE).forward(req, resp);
            return;
        }
        if ("".equals(clanName)) {
            clanName = "alone";
        }
        User user = new User(clanName, login, Encoder.encode(password), fName, lName, email, new Date(System.currentTimeMillis()),
                "user", "ACTIVE", UserServiceImpl.toSQLDate(birth), DifficultyGroup.BEGINNER.toString());
        try {
            //Increasing of rating by 1 after registration
            user.setRating(1);
            user = userService.save(user);
        } catch (ServiceSystemException e) {
            req.setAttribute("errorMsg", SERVER_ERROR_MESSAGE);
            req.getRequestDispatcher(REGISTRATION_PAGE).forward(req, resp);
            LOG.error("RegistrationController: Service System Exception", e);
            return;
        }
        String contextPath = req.getContextPath();
        req.getSession().setAttribute("user", user);
        resp.sendRedirect(contextPath + "/frontController?command=statistics");
    }
}
