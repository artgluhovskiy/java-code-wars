package org.art.projects.java_code_wars.web.filters;

import org.apache.log4j.Logger;
import org.art.projects.java_code_wars.entities.User;
import org.art.projects.java_code_wars.web.command.enums.CommandType;
import org.art.projects.java_code_wars.web.handlers.RequestHandler;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.art.projects.java_code_wars.web.command.enums.CommandType.ADMIN;

@WebFilter(urlPatterns = "/frontController")
public class AuthorizationFilter extends BaseFilter {

    private static final Logger LOG = Logger.getLogger(AuthorizationFilter.class);

    @Override
    public void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) throws IOException, ServletException {
        LOG.info("Authorization Filter: <-- in");
        CommandType type = RequestHandler.getCommand(req);
        if (ADMIN.equals(type)) {
            String contextPath = req.getContextPath();
            HttpSession session = req.getSession();
            User user = (User) session.getAttribute("user");
            if (user == null || !user.getRole().equals("admin")) {
                resp.sendRedirect(contextPath + "/login.jsp");
                return;
            }
        }
        chain.doFilter(req, resp);
        LOG.info("Authorization Filter: out -->");
    }
}
