package it.unipi.lsmsd.ecommerce.service.impl;

import it.unipi.lsmsd.ecommerce.dao.DAOLocator;
import it.unipi.lsmsd.ecommerce.dao.ProductDAO;
import it.unipi.lsmsd.ecommerce.dao.UserDAO;
import it.unipi.lsmsd.ecommerce.dao.enums.DataRepositoryEnum;
import it.unipi.lsmsd.ecommerce.dto.AuthenticatedUserDTO;
import it.unipi.lsmsd.ecommerce.dto.CustomerRegistrationDTO;
import it.unipi.lsmsd.ecommerce.dto.PageDTO;
import it.unipi.lsmsd.ecommerce.dto.ProductDTO;
import it.unipi.lsmsd.ecommerce.model.Customer;
import it.unipi.lsmsd.ecommerce.model.RegisteredUser;
import it.unipi.lsmsd.ecommerce.service.ProductService;
import it.unipi.lsmsd.ecommerce.service.UserService;
import it.unipi.lsmsd.ecommerce.service.exception.BusinessException;

import java.sql.SQLException;

public class UserServiceImpl implements UserService {

    private UserDAO userDAO;

    public UserServiceImpl(){
        this.userDAO = DAOLocator.getUserDAO(DataRepositoryEnum.MYSQL);
    }


    @Override
    public AuthenticatedUserDTO authenticate(String username, String password) throws BusinessException {
        try {
            RegisteredUser registeredUser = userDAO.authenticate(username, password);
            AuthenticatedUserDTO authenticatedUserDTO = new AuthenticatedUserDTO();
            authenticatedUserDTO.setId(registeredUser.getId());
            authenticatedUserDTO.setFirstName(registeredUser.getFirstName());
            authenticatedUserDTO.setLastName(registeredUser.getLastName());
            authenticatedUserDTO.setEmail(username);
            if (registeredUser instanceof Customer){
                Customer customer = (Customer)registeredUser;
                authenticatedUserDTO.setAddress(customer.getAddress());
                authenticatedUserDTO.setCountry(customer.getCountry());
                authenticatedUserDTO.setPhone(customer.getPhone());
            }
            return authenticatedUserDTO;
        } catch (Exception e) {
            throw new BusinessException(e);
        }
    }

    @Override
    public AuthenticatedUserDTO registerCustomerAndAuth(CustomerRegistrationDTO customerRegistrationDTO) throws BusinessException {
        try {
            Customer customer = new Customer();
            customer.setFirstName(customerRegistrationDTO.getFirstName());
            customer.setLastName(customerRegistrationDTO.getLastName());
            customer.setAddress(customerRegistrationDTO.getAddress());
            customer.setCountry(customerRegistrationDTO.getCountry());
            customer.setPhone(customerRegistrationDTO.getPhone());
            customer.setUsername(customerRegistrationDTO.getUsername());
            customer.setPassword(customerRegistrationDTO.getPassword());
            /*
                Here I can set default values for my entity,
                if my company decides to set inactive the status of new acounts for instance.
             */
            customer.setActive(true);

            RegisteredUser registeredUser = userDAO.register(customer);
            AuthenticatedUserDTO authenticatedUserDTO = new AuthenticatedUserDTO();
            authenticatedUserDTO.setId(registeredUser.getId());
            authenticatedUserDTO.setFirstName(registeredUser.getFirstName());
            authenticatedUserDTO.setLastName(registeredUser.getLastName());
            authenticatedUserDTO.setAddress(customer.getAddress());
            authenticatedUserDTO.setCountry(customer.getCountry());
            authenticatedUserDTO.setPhone(customer.getPhone());
            return authenticatedUserDTO;
        } catch (Exception e) {
            throw new BusinessException(e);
        }
    }
}
