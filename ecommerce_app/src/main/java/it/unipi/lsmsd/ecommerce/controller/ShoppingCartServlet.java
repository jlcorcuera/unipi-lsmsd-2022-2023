package it.unipi.lsmsd.ecommerce.controller;

import it.unipi.lsmsd.ecommerce.dto.AuthenticatedUserDTO;
import it.unipi.lsmsd.ecommerce.dto.CheckoutInfoDTO;
import it.unipi.lsmsd.ecommerce.dto.ProductCartDTO;
import it.unipi.lsmsd.ecommerce.dto.ShoppingCartDTO;
import it.unipi.lsmsd.ecommerce.service.ServiceLocator;
import it.unipi.lsmsd.ecommerce.service.ShoppingCartService;
import it.unipi.lsmsd.ecommerce.service.exception.BusinessException;
import it.unipi.lsmsd.ecommerce.utils.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/shopping-cart")
public class ShoppingCartServlet extends HttpServlet {

    private static Logger logger = LoggerFactory.getLogger(ShoppingCartServlet.class);
    protected ShoppingCartService shoppingCartService;

    public ShoppingCartServlet(){
        shoppingCartService = ServiceLocator.getShoppingCartService();
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action") != null ? request.getParameter("action").toString() : null;
        AuthenticatedUserDTO authenticatedUserDTO = SecurityUtils.getAuthenticatedUser(request);
        String targetJSP = "/WEB-INF/jsp/shopping-cart.jsp";
        if ("add".equals(action)){
            addProductInCartInfo(request, response, authenticatedUserDTO);
            return;
        } else if ("remove".equals(action)){
            //TODO
        } else if ("delete".equals(action)){
            deleteProductInCartInfo(request, authenticatedUserDTO);
        } else if ("place-order".equals(action)){
            placeOrder(request, response, authenticatedUserDTO);
            return;
        }
        addShoppingCartInfoToRequest(request, authenticatedUserDTO);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(targetJSP);
        requestDispatcher.forward(request, response);
    }

    private void placeOrder(HttpServletRequest request, HttpServletResponse response, AuthenticatedUserDTO authenticatedUserDTO) throws IOException {
        try {
            CheckoutInfoDTO checkoutInfoDTO = new CheckoutInfoDTO();
            checkoutInfoDTO.setShippingAddress(request.getParameter("shippingAddress"));
            checkoutInfoDTO.setShippingCountry(request.getParameter("shippingCountry"));
            checkoutInfoDTO.setPaymentType(request.getParameter("paymentType"));
            checkoutInfoDTO.setPaymentNumber(request.getParameter("paymentNumber"));
            String orderNumber = shoppingCartService.checkout(authenticatedUserDTO.getId(), checkoutInfoDTO);
            response.getWriter().print(orderNumber);
            response.getWriter().flush();
        } catch (BusinessException e) {
            logger.error("Unable to place a new order.", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private void addProductInCartInfo(HttpServletRequest request, HttpServletResponse response, AuthenticatedUserDTO authenticatedUserDTO) throws IOException {
        Long productId = Long.parseLong(request.getParameter("productId"));
        Integer quantity = request.getParameter("quantity") != null ? Integer.parseInt(request.getParameter("quantity")): 1;
        ProductCartDTO productCartDTO = new ProductCartDTO(productId, quantity);
        int newNumberItems = 0;
        try {
            newNumberItems = shoppingCartService.addProductCart(authenticatedUserDTO.getId(), productCartDTO);
            response.getWriter().print(newNumberItems);
            response.getWriter().flush();
        } catch (BusinessException e) {
            logger.error("Unable to add product into a shopping cart.", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }


    public void addShoppingCartInfoToRequest(HttpServletRequest request, AuthenticatedUserDTO authenticatedUserDTO) {
        int numberItemsShoppingCart = shoppingCartService.getTotalNumberOfItems(authenticatedUserDTO.getId());
        request.setAttribute("shoppingCartNumItems", numberItemsShoppingCart);
        ShoppingCartDTO shoppingCartDTO = shoppingCartService.getByCustomerId(authenticatedUserDTO.getId());
        request.setAttribute("shoppingCartDTO", shoppingCartDTO);
    }

    public void deleteProductInCartInfo(HttpServletRequest request, AuthenticatedUserDTO authenticatedUserDTO){
        Long productId = Long.parseLong(request.getParameter("productId"));
        try {
            shoppingCartService.removeProductCart(authenticatedUserDTO.getId(), productId);
        } catch (BusinessException e) {
            logger.error("An error happened when trying to delete a product from a shopping cart.", e);
        }
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
