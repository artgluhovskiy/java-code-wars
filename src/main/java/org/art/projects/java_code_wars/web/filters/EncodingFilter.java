package org.art.projects.java_code_wars.web.filters;

import org.apache.log4j.Logger;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(
        filterName = "encodingFilter",
        urlPatterns = "/*",
        initParams = {
                @WebInitParam(name = "encoding", value = "UTF-8")
        })
public class EncodingFilter extends BaseFilter {

    private static final Logger LOG = Logger.getLogger(EncodingFilter.class);

    private String encoding = "UTF-8";

    @Override
    public void init(FilterConfig config) {
        LOG.info("Encoding Filter: init method");
        String encodingParam = config.getInitParameter("encoding");
        if (encodingParam != null && !"".equals(encodingParam)) {
            encoding = encodingParam;
        }
    }

    @Override
    public void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) throws IOException, ServletException {
        LOG.info("Encoding Filter: <-- in");
        req.setCharacterEncoding(encoding);
        resp.setContentType("text/html; charset=" + encoding);
        resp.setCharacterEncoding(encoding);
        resp.setHeader("Content-Language", "UTF-8");
        chain.doFilter(req, resp);
        LOG.info("Encoding Filter: out -->");
    }
}
