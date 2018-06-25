package org.art.projects.java_code_wars.web.servlets;

import org.apache.log4j.Logger;
import org.art.projects.java_code_wars.web.command.enums.CommandType;
import org.art.projects.java_code_wars.web.handlers.RequestHandler;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Front Controller servlet (handles all client requests).
 */
@WebServlet(urlPatterns = "/frontController")
public class FrontController extends HttpServlet {

    @SuppressWarnings("unused")
    private static final Logger LOG = Logger.getLogger(FrontController.class);

    @Override
    public void service(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        CommandType commandType = RequestHandler.getCommand(req);
        commandType.getController().execute(req, resp);
    }
}
