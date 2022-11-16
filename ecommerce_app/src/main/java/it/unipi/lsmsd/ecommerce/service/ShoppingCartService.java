package it.unipi.lsmsd.ecommerce.service;

import it.unipi.lsmsd.ecommerce.dto.CheckoutInfoDTO;
import it.unipi.lsmsd.ecommerce.dto.ProductCartDTO;
import it.unipi.lsmsd.ecommerce.dto.ShoppingCartDTO;
import it.unipi.lsmsd.ecommerce.service.exception.BusinessException;

public interface ShoppingCartService {
    String checkout(Long customerId, CheckoutInfoDTO checkoutInfoDTO) throws BusinessException ;
    int addProductCart(Long customerId, ProductCartDTO productCartDTO) throws BusinessException ;
    void removeProductCart(Long customerId, Long productId) throws BusinessException;
    ShoppingCartDTO getByCustomerId(Long customerId);
    int getTotalNumberOfItems(Long customerId);
}
