package it.unipi.lsmsd.ecommerce.service;

import it.unipi.lsmsd.ecommerce.service.impl.ProductServiceImpl;
import it.unipi.lsmsd.ecommerce.service.impl.ShoppingCartServiceImpl;
import it.unipi.lsmsd.ecommerce.service.impl.StatisticsServiceImpl;
import it.unipi.lsmsd.ecommerce.service.impl.UserServiceImpl;

public class ServiceLocator {

    private static ShoppingCartService shoppingCartService = new ShoppingCartServiceImpl();
    private static ProductService productService = new ProductServiceImpl();
    private static UserService userService = new UserServiceImpl();
    private static StatisticsService statisticsService = new StatisticsServiceImpl();

    public static StatisticsService getStatisticsService(){
        return statisticsService;
    }

    public static ShoppingCartService getShoppingCartService(){
        return shoppingCartService;
    }

    public static ProductService getProductService(){
        return productService;
    }

    public static UserService getUserService(){
        return userService;
    }
}
