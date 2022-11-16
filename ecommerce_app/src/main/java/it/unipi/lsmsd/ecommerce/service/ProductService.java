package it.unipi.lsmsd.ecommerce.service;

import it.unipi.lsmsd.ecommerce.dto.PageDTO;
import it.unipi.lsmsd.ecommerce.dto.ProductDTO;
import it.unipi.lsmsd.ecommerce.service.exception.BusinessException;

import java.util.List;

public interface ProductService {
    PageDTO<ProductDTO> listProductPage(int page, int type, String searchKeyword) throws BusinessException;

    void updateStock(Long id, Integer stock) throws BusinessException;
}
