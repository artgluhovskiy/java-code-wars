package org.art.projects.java_code_wars.web.command.impl;

import com.google.gson.Gson;
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
import java.io.IOException;
import java.io.PrintWriter;

public class CheckLoginController implements Controller {

    private static final Logger LOG = Logger.getLogger(CheckLoginController.class);

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        UserService userService = UserServiceImpl.getInstance();
        PrintWriter writer = resp.getWriter();
        String login = req.getParameter("login");
        try {
            User user = userService.getUserByLogin(login);
            if (user == null) {
                writer.write(new Gson().toJson("OK"));
            } else {
                writer.write(new Gson().toJson("FAIL"));
            }
        } catch (ServiceBusinessException e) {
            writer.write(new Gson().toJson("OK"));
            LOG.info("CheckLoginController: User with such login doesn't exist in the database. It's OK!");
        } catch (ServiceSystemException e) {
            writer.write(new Gson().toJson("FAIL"));
            LOG.error("CheckLoginController: Service System Exception", e);
        }
    }
}
