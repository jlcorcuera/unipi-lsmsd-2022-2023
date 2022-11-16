package it.unipi.lsmsd.ecommerce.controller;

import it.unipi.lsmsd.ecommerce.dto.AuthenticatedUserDTO;
import it.unipi.lsmsd.ecommerce.dto.PageDTO;
import it.unipi.lsmsd.ecommerce.dto.ProductDTO;
import it.unipi.lsmsd.ecommerce.service.ProductService;
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

@WebServlet("/shop")
public class ShopServlet extends HttpServlet {

    private static Logger logger = LoggerFactory.getLogger(ShopServlet.class);

    protected void processRequest(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        ShoppingCartService shoppingCartService = ServiceLocator.getShoppingCartService();
        ProductService productService = ServiceLocator.getProductService();
        int page = httpServletRequest.getParameter("page") != null ? Integer.parseInt(httpServletRequest.getParameter("page")) : 1;
        String searchKeyword = httpServletRequest.getParameter("searchKeyword") == null ? "" : httpServletRequest.getParameter("searchKeyword");
        int type = httpServletRequest.getParameter("type") != null ? Integer.parseInt(httpServletRequest.getParameter("type")) : 0;
        logger.info("ShopServlet: page = {}, searchKeyword = {}, type = {}.", new Object[]{
                page,
                searchKeyword,
                type});
        PageDTO<ProductDTO> pageDTO = null;
        try {
            pageDTO = productService.listProductPage(page, type, searchKeyword);
        } catch (BusinessException e) {
            logger.error("Unable to fetch products info.", e);
            httpServletRequest.setAttribute("errorMessage", "Something goes wrong in the server.");
        }
        httpServletRequest.setAttribute("searchKeyword", searchKeyword);
        httpServletRequest.setAttribute("type", type);
        httpServletRequest.setAttribute("pageDTO", pageDTO);
        httpServletRequest.setAttribute("page", page);
        String targetJSP = "/WEB-INF/jsp/shop.jsp";
        AuthenticatedUserDTO authenticatedUserDTO = SecurityUtils.getAuthenticatedUser(httpServletRequest);
        if (authenticatedUserDTO != null){
            int numberItemsShoppingCart = shoppingCartService.getTotalNumberOfItems(authenticatedUserDTO.getId());
            httpServletRequest.setAttribute("shoppingCartNumItems", numberItemsShoppingCart);
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
