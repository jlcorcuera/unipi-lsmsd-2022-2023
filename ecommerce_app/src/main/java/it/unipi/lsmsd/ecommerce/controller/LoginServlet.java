package it.unipi.lsmsd.ecommerce.controller;

import it.unipi.lsmsd.ecommerce.dto.AuthenticatedUserDTO;
import it.unipi.lsmsd.ecommerce.dto.CustomerRegistrationDTO;
import it.unipi.lsmsd.ecommerce.service.ServiceLocator;
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
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private static Logger logger = LoggerFactory.getLogger(LoginServlet.class);

    protected void processRequest(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        UserService userService = ServiceLocator.getUserService();
        AuthenticatedUserDTO authenticatedUserDTO = SecurityUtils.getAuthenticatedUser(httpServletRequest);
        String action = httpServletRequest.getParameter("action");
        String targetJSP = "/WEB-INF/jsp/login.jsp";
        if (authenticatedUserDTO != null){
            if ("logout".equals(action)){
                httpServletRequest.getSession().removeAttribute(Constants.AUTHENTICATED_USER_KEY);
                httpServletRequest.getSession().invalidate();
            }
            targetJSP = "/shop";
        } else if (action != null && !action.isEmpty()){
            if ("signup".equals(action)){
                try {
                    CustomerRegistrationDTO customerRegistrationDTO = ConverterUtils.fromRequestToCustomerRegDTO(httpServletRequest);
                    authenticatedUserDTO = userService.registerCustomerAndAuth(customerRegistrationDTO);
                } catch (BusinessException e) {
                    logger.error("Error during signup operation.",e);
                    httpServletRequest.setAttribute("errorMessage", "Unable to complete the registration. Please control your information.");
                }
            } else if ("login".equals(action)){
                String username = httpServletRequest.getParameter("username");
                String password = httpServletRequest.getParameter("password");
                try {
                    if (username != null && password != null && !username.isEmpty() && !password.isEmpty()){
                        authenticatedUserDTO = userService.authenticate(username, password);
                    } else {
                        httpServletRequest.setAttribute("errorMessage", "Invalid username or password.");
                    }
                } catch (BusinessException e) {
                    logger.error("Error during login operation.",e);
                    httpServletRequest.setAttribute("errorMessage", "Invalid username or password.");
                }
            }
            if (authenticatedUserDTO != null) {
                HttpSession session = httpServletRequest.getSession(true);
                session.setAttribute(Constants.AUTHENTICATED_USER_KEY, authenticatedUserDTO);
                targetJSP = "shop";
            }
        }
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
