package org.art.projects.java_code_wars.web.filters;

import org.apache.log4j.Logger;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = "/frontController")
public class LocaleFilter extends BaseFilter {

    private static final Logger LOG = Logger.getLogger(LocaleFilter.class);

    @Override
    public void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) throws IOException, ServletException {
        LOG.info("Locale Filter: <-- in");
        String locale = req.getParameter("locale");
        if (locale != null && !locale.isEmpty()) {
            req.getSession().setAttribute("locale", locale);
        } else {
            req.getSession().setAttribute("locale", "en");
        }
        chain.doFilter(req, resp);
        LOG.info("Locale Filter: out -->");
    }
}
