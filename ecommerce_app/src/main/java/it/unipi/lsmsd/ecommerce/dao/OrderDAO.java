package it.unipi.lsmsd.ecommerce.dao;

import it.unipi.lsmsd.ecommerce.dao.exception.DAOException;
import it.unipi.lsmsd.ecommerce.model.Order;

public interface OrderDAO {
    void save(Order order, Object... params) throws DAOException;
}
