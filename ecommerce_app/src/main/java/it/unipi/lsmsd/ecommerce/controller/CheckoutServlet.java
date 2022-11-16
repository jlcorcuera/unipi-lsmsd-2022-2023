package it.unipi.lsmsd.ecommerce.controller;

import it.unipi.lsmsd.ecommerce.dto.AuthenticatedUserDTO;
import it.unipi.lsmsd.ecommerce.dto.PageDTO;
import it.unipi.lsmsd.ecommerce.dto.ProductDTO;
import it.unipi.lsmsd.ecommerce.service.ProductService;
import it.unipi.lsmsd.ecommerce.service.ServiceLocator;
import it.unipi.lsmsd.ecommerce.service.ShoppingCartService;
import it.unipi.lsmsd.ecommerce.service.exception.BusinessException;
import it.unipi.lsmsd.ecommerce.utils.SecurityUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/checkout")
public class CheckoutServlet extends ShoppingCartServlet{

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action") != null ? request.getParameter("action").toString() : null;
        AuthenticatedUserDTO authenticatedUserDTO = SecurityUtils.getAuthenticatedUser(request);
        if ("delete".equals(action)){
            deleteProductInCartInfo(request, authenticatedUserDTO);
        }
        ShoppingCartService shoppingCartService = ServiceLocator.getShoppingCartService();
        String targetJSP = "/WEB-INF/jsp/checkout.jsp";
        super.addShoppingCartInfoToRequest(request, authenticatedUserDTO);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(targetJSP);
        requestDispatcher.forward(request, response);
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
