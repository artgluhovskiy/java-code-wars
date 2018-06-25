package org.art.projects.java_code_wars.web.filters;

import org.apache.log4j.Logger;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "encodingFilter", urlPatterns = "/*")
public class ExceptionHandlerFilter extends BaseFilter {

    private static final Logger LOG = Logger.getLogger(ExceptionHandlerFilter.class);

    @Override
    public void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) throws IOException {
        try {
            LOG.info("Exception Handler Filter: <-- in");
            chain.doFilter(req, resp);
            LOG.info("Exception Handler Filter: out -->");
        } catch (Exception e) {
            LOG.info("Exception Handler Filter: Exception has been caught (redirect to Main page)!", e);
            resp.sendRedirect(req.getContextPath() + "/frontController?command=main");
        }
    }
}
