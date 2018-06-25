package org.art.projects.java_code_wars.web.command.impl;

import org.apache.log4j.Logger;
import org.art.projects.java_code_wars.dto.OrderDTO;
import org.art.projects.java_code_wars.entities.User;
import org.art.projects.java_code_wars.services.TaskOrderService;
import org.art.projects.java_code_wars.services.exceptions.ServiceBusinessException;
import org.art.projects.java_code_wars.services.exceptions.ServiceSystemException;
import org.art.projects.java_code_wars.services.impl.TaskOrderServiceImpl;
import org.art.projects.java_code_wars.web.command.Controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class StatisticsController implements Controller {

    private static final Logger LOG = Logger.getLogger(StatisticsController.class);

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        List<OrderDTO> orderList = null;
        req.removeAttribute("errorMsg");
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        TaskOrderService taskOrderService = TaskOrderServiceImpl.getInstance();
        try {
            orderList = taskOrderService.getUserTaskOrders(user.getUserID());
        } catch (ServiceSystemException e) {
            req.setAttribute("errorMsg", SERVER_ERROR_MESSAGE);
            LOG.error("StatisticsController: Service System Exception", e);
        } catch (ServiceBusinessException e) {
            req.setAttribute("errorMsg", "Unfortunately, no task orders were found!");
            LOG.info("StatisticsController: No task orders were found");
        }
        session.setAttribute("orderList", orderList);
        req.getRequestDispatcher(MAIN_PAGE).forward(req, resp);
    }
}
