package it.unipi.lsmsd.ecommerce.dao;

import it.unipi.lsmsd.ecommerce.dao.enums.DataRepositoryEnum;
import it.unipi.lsmsd.ecommerce.dao.mysql.OrderMySQLDAO;
import it.unipi.lsmsd.ecommerce.dao.mysql.ProductMySQLDAO;
import it.unipi.lsmsd.ecommerce.dao.mysql.StatisticsMySQLDAO;
import it.unipi.lsmsd.ecommerce.dao.mysql.UserMySQLDAO;
import it.unipi.lsmsd.ecommerce.dao.redis.ShoppingCartRedisDAO;

public class DAOLocator {

    public static StatisticsDAO getStatisticsDAO(DataRepositoryEnum dataRepositoryEnum){
        if (DataRepositoryEnum.MYSQL.equals(dataRepositoryEnum)){
            return new StatisticsMySQLDAO();
        }
        throw new UnsupportedOperationException("Data repository not supported: " + dataRepositoryEnum);
    }

    public static UserDAO getUserDAO(DataRepositoryEnum dataRepositoryEnum){
        if (DataRepositoryEnum.MYSQL.equals(dataRepositoryEnum)){
            return new UserMySQLDAO();
        }
        throw new UnsupportedOperationException("Data repository not supported: " + dataRepositoryEnum);
    }

    public static ProductDAO getProductDAO(DataRepositoryEnum dataRepositoryEnum){
        if (DataRepositoryEnum.MYSQL.equals(dataRepositoryEnum)){
            return new ProductMySQLDAO();
        }
        throw new UnsupportedOperationException("Data repository not supported: " + dataRepositoryEnum);
    }

    public static OrderDAO getOrderDAO(DataRepositoryEnum dataRepositoryEnum){
        if (DataRepositoryEnum.MYSQL.equals(dataRepositoryEnum)){
            return new OrderMySQLDAO();
        }
        throw new UnsupportedOperationException("Data repository not supported: " + dataRepositoryEnum);
    }

    public static ShoppingCartDAO getShoppingCartDAO(DataRepositoryEnum dataRepositoryEnum){
        if (DataRepositoryEnum.REDIS.equals(dataRepositoryEnum)){
            return new ShoppingCartRedisDAO();
        }
        throw new UnsupportedOperationException("Data repository not supported: " + dataRepositoryEnum);
    }
}
