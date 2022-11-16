package it.unipi.lsmsd.ecommerce.dao;

import it.unipi.lsmsd.ecommerce.dao.exception.DAOException;
import it.unipi.lsmsd.ecommerce.model.RegisteredUser;


public interface UserDAO {
    RegisteredUser register(RegisteredUser user) throws DAOException;
    RegisteredUser authenticate(String username, String password) throws DAOException;
}
