package it.unipi.lsmsd.ecommerce.dao.mysql;

import it.unipi.lsmsd.ecommerce.dao.ProductDAO;
import it.unipi.lsmsd.ecommerce.dao.base.BaseMySQLDAO;
import it.unipi.lsmsd.ecommerce.dao.exception.DAOException;
import it.unipi.lsmsd.ecommerce.dto.PageDTO;
import it.unipi.lsmsd.ecommerce.dto.ProductDTO;
import it.unipi.lsmsd.ecommerce.model.enums.ProductTypeEnum;
import it.unipi.lsmsd.ecommerce.model.product.Product;
import it.unipi.lsmsd.ecommerce.utils.Constants;
import redis.clients.jedis.util.IOUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductMySQLDAO extends BaseMySQLDAO implements ProductDAO {
    @Override
    public PageDTO<ProductDTO> listProductPage(int page, int type, String searchKeyword) throws DAOException {
        PageDTO<ProductDTO> pageDTO = new PageDTO<ProductDTO>();
        List<ProductDTO> entries = new ArrayList<>();
        int totalCount = 0;
        int pageOffset = (page - 1) * Constants.PAGE_SIZE;
        StringBuilder sqlSelect = new StringBuilder();
        sqlSelect.append("select p.id, p.name, p.image_url, p.price from ec_product p ");
        sqlSelect.append("where 1 = 1 ");
        if (searchKeyword != null && !searchKeyword.isEmpty()){
            sqlSelect.append(" and lower(p.name) like concat('%', lower(?), '%') ");
        }
        if (ProductTypeEnum.ALL.getType() != type){
            sqlSelect.append(" and p.type = ? ");
        }
        sqlSelect.append("order by lower(p.name) ");
        sqlSelect.append("LIMIT ").append(pageOffset).append(", ").append(Constants.PAGE_SIZE);
        StringBuilder sqlCount = new StringBuilder();
        sqlCount.append("select count(p.id) from ec_product p ");
        sqlCount.append("where 1 = 1 ");
        if (searchKeyword != null && !searchKeyword.isEmpty()){
            sqlCount.append(" and lower(p.name) like concat('%', lower(?), '%') ");
        }
        if (ProductTypeEnum.ALL.getType() != type){
            sqlCount.append(" and p.type = ? ");
        }
        PreparedStatement pstmtSelect = null;
        PreparedStatement pstmtCount = null;
        ResultSet resultSetSelect = null;
        ResultSet resultSetCount = null;

        try(Connection connection = getConnection()){
            pstmtSelect = connection.prepareStatement(sqlSelect.toString());
            pstmtCount = connection.prepareStatement(sqlCount.toString());
            int idxParam = 1;
            if (searchKeyword != null && !searchKeyword.isEmpty()){
                pstmtSelect.setString(idxParam, searchKeyword);
                pstmtCount.setString(idxParam, searchKeyword);
                idxParam++;
            }
            if (ProductTypeEnum.ALL.getType() != type){
                pstmtSelect.setInt(idxParam, type);
                pstmtCount.setInt(idxParam, type);
            }
            resultSetSelect = pstmtSelect.executeQuery();
            resultSetCount = pstmtCount.executeQuery();
            if (resultSetCount.next()){
                totalCount = resultSetCount.getInt(1);
            }
            while (resultSetSelect.next()){
                ProductDTO productDTO = new ProductDTO();
                productDTO.setId(resultSetSelect.getLong(1));
                productDTO.setName(resultSetSelect.getString(2));
                productDTO.setImageUrl(resultSetSelect.getString(3));
                productDTO.setPrice(resultSetSelect.getFloat(4));
                entries.add(productDTO);
            }
            pageDTO.setTotalCount(totalCount);
            pageDTO.setEntries(entries);
        } catch (Exception ex) {
            throw new DAOException(ex);
        }finally{
            IOUtils.closeQuietly(resultSetSelect);
            IOUtils.closeQuietly(resultSetCount);
            IOUtils.closeQuietly(pstmtSelect);
            IOUtils.closeQuietly(pstmtCount);
        }
        return pageDTO;
    }

    @Override
    public Product getProductById(Long id, Object ...params) throws DAOException {
        Product product = null;
        StringBuilder sql = new StringBuilder();
        sql.append("select p.id, p.name, p.short_description, p.description, p.brand, p.image_url, p.price, p.stock, p.type ");
        sql.append("from ec_product p where p.id = ?");
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        Connection connectionParam = null;
        if (params != null && params.length > 0){
            connectionParam = (Connection)params[0];
        }
        try{
            connectionParam = connectionParam != null ? connectionParam : getConnection();
            pstmt = connectionParam.prepareStatement(sql.toString());
            pstmt.setLong(1, id);
            resultSet = pstmt.executeQuery();
            if (resultSet.next()){
                ProductTypeEnum type = ProductTypeEnum.fromType(resultSet.getInt(9));
                product = Product.newInstance(type);
                product.setId(resultSet.getLong(1));
                product.setName(resultSet.getString(2));
                product.setShortDescription(resultSet.getString(3));
                product.setDescription(resultSet.getString(4));
                product.setBrand(resultSet.getString(5));
                product.setImageUrl(resultSet.getString(6));
                product.setPrice(resultSet.getFloat(7));
                product.setStock(resultSet.getInt(8));
            }
        } catch (Exception ex) {
            throw new DAOException(ex);
        }finally{
            IOUtils.closeQuietly(resultSet);
            IOUtils.closeQuietly(pstmt);
        }
        return product;
    }

    @Override
    public void updateStock(Long id, Integer stock, Object ...params) throws DAOException {
        Product product = null;
        String sql = "update ec_product set stock = ? where id = ?";
        PreparedStatement pstmt = null;
        Connection connectionParam = null;
        if (params != null && params.length > 0 && params[0] instanceof Connection){
            connectionParam = (Connection)params[0];
        }
        Connection connection =null;
        try{
            connection = connectionParam != null ? connectionParam : getConnection();
            pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, stock);
            pstmt.setLong(2, id);
            pstmt.executeUpdate();
        } catch (Exception ex) {
            throw new DAOException(ex);
        }finally{
            IOUtils.closeQuietly(pstmt);
            if (connectionParam == null){
                IOUtils.closeQuietly(connection);
            }
        }
    }
}
