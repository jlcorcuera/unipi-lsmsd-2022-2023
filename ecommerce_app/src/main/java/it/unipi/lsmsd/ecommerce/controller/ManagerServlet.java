package it.unipi.lsmsd.ecommerce.controller;

import it.unipi.lsmsd.ecommerce.dto.AuthenticatedUserDTO;
import it.unipi.lsmsd.ecommerce.dto.CustomerRegistrationDTO;
import it.unipi.lsmsd.ecommerce.dto.statistics.DashboardDTO;
import it.unipi.lsmsd.ecommerce.service.ServiceLocator;
import it.unipi.lsmsd.ecommerce.service.StatisticsService;
import it.unipi.lsmsd.ecommerce.service.UserService;
import it.unipi.lsmsd.ecommerce.service.exception.BusinessException;
import it.unipi.lsmsd.ecommerce.utils.Constants;
import it.unipi.lsmsd.ecommerce.utils.ConverterUtils;
import it.unipi.lsmsd.ecommerce.utils.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.ws.Service;
import java.io.IOException;

@WebServlet("/manager")
public class ManagerServlet extends HttpServlet {

    private static Logger logger = LoggerFactory.getLogger(ManagerServlet.class);

    private StatisticsService statisticsService;

    public ManagerServlet(){
        statisticsService = ServiceLocator.getStatisticsService();
    }


    protected void processRequest(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        String targetJSP = "/WEB-INF/jsp/manager.jsp";
        DashboardDTO dashboardDTO = null;
        try {
            dashboardDTO = statisticsService.getDashboardStatsDTO();
        } catch (BusinessException e) {
            logger.error("Unable to fetch statistics for dashboard.", e);
        }
        httpServletRequest.setAttribute("dashboardDTO", dashboardDTO);
        RequestDispatcher requestDispatcher = httpServletRequest.getRequestDispatcher(targetJSP);
        requestDispatcher.forward(httpServletRequest, httpServletResponse);
    }

    @Override
    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        processRequest(httpServletRequest, httpServletResponse);
    }

    @Override
    protected void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        processRequest(httpServletRequest, httpServletResponse);
    }

}
