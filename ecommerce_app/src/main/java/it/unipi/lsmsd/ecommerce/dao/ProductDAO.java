package it.unipi.lsmsd.ecommerce.dao;

import it.unipi.lsmsd.ecommerce.dao.exception.DAOException;
import it.unipi.lsmsd.ecommerce.dto.PageDTO;
import it.unipi.lsmsd.ecommerce.dto.ProductDTO;
import it.unipi.lsmsd.ecommerce.model.product.Product;

public interface ProductDAO {
    PageDTO<ProductDTO> listProductPage(int page, int type, String productName) throws DAOException;
    Product getProductById(Long id, Object ...params ) throws DAOException;

    void updateStock(Long id, Integer stock, Object ...params) throws DAOException;
}
