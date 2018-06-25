package org.art.projects.java_code_wars.web.command.impl;

import org.apache.log4j.Logger;
import org.art.projects.java_code_wars.web.command.Controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LogoutController implements Controller {

    @SuppressWarnings("unused")
    private static final Logger LOG = Logger.getLogger(LogoutController.class);

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        //User session invalidation
        req.getSession().invalidate();
        resp.sendRedirect(req.getContextPath() + "/frontController?command=main");
    }
}
