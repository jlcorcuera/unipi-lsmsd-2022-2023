package it.unipi.lsmsd.ecommerce.service.impl;

import it.unipi.lsmsd.ecommerce.dao.DAOLocator;
import it.unipi.lsmsd.ecommerce.dao.ProductDAO;
import it.unipi.lsmsd.ecommerce.dao.enums.DataRepositoryEnum;
import it.unipi.lsmsd.ecommerce.dto.PageDTO;
import it.unipi.lsmsd.ecommerce.dto.ProductDTO;
import it.unipi.lsmsd.ecommerce.service.ProductService;
import it.unipi.lsmsd.ecommerce.service.exception.BusinessException;

import java.sql.SQLException;

public class ProductServiceImpl implements ProductService {

    private ProductDAO productDAO;

    public ProductServiceImpl(){
        this.productDAO = DAOLocator.getProductDAO(DataRepositoryEnum.MYSQL);
    }

    @Override
    public PageDTO<ProductDTO> listProductPage(int page, int type, String searchKeyword) throws BusinessException {
        try {
            return productDAO.listProductPage(page, type, searchKeyword);
        } catch (Exception e) {
            throw new BusinessException(e);
        }
    }

    @Override
    public void updateStock(Long id, Integer stock) throws BusinessException {
        try {
            productDAO.updateStock(id, stock);
        } catch (Exception e) {
            throw new BusinessException(e);
        }
    }
}
