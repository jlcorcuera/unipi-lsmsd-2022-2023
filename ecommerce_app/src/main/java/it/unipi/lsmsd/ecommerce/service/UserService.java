package it.unipi.lsmsd.ecommerce.service;

import it.unipi.lsmsd.ecommerce.dto.AuthenticatedUserDTO;
import it.unipi.lsmsd.ecommerce.dto.CustomerRegistrationDTO;
import it.unipi.lsmsd.ecommerce.dto.PageDTO;
import it.unipi.lsmsd.ecommerce.dto.ProductDTO;
import it.unipi.lsmsd.ecommerce.service.exception.BusinessException;

public interface UserService {

    AuthenticatedUserDTO authenticate(String username, String password) throws BusinessException;
    AuthenticatedUserDTO registerCustomerAndAuth(CustomerRegistrationDTO customerRegistrationDTO) throws BusinessException;
}
