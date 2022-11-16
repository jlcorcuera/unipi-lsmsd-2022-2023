package it.unipi.lsmsd.ecommerce.dao.mysql;

import it.unipi.lsmsd.ecommerce.dao.OrderDAO;
import it.unipi.lsmsd.ecommerce.dao.base.BaseMySQLDAO;
import it.unipi.lsmsd.ecommerce.dao.exception.DAOException;
import it.unipi.lsmsd.ecommerce.model.Order;
import it.unipi.lsmsd.ecommerce.model.ProductDetail;
import redis.clients.jedis.util.IOUtils;

import java.sql.*;

public class OrderMySQLDAO extends BaseMySQLDAO implements OrderDAO {
    @Override
    public void save(Order order, Object ... params) throws DAOException {
        StringBuilder insertOrder = new StringBuilder();
        insertOrder.append("insert into ec_order (customer_id, order_number, order_date, ");
        insertOrder.append("shipping_date, shipping_address, shipping_country, payment_type, ");
        insertOrder.append("payment_number, status, total) values ");
        insertOrder.append("(?,?,?,?,?,?,?,?,?,?)");
        StringBuilder insertProductDetail = new StringBuilder();
        insertProductDetail.append("insert into ec_product_detail(order_id, product_id, quantity, total) ");
        insertProductDetail.append(" values (?, ?, ?, ?)");
        Connection connectionParam = null;
        if (params != null && params.length > 0 && params[0] instanceof Connection){
            connectionParam = (Connection)params[0];
        }
        Connection connection = null;
        PreparedStatement preparedStatement1 = null;
        PreparedStatement preparedStatement2 = null;
        try{
            connection = connectionParam != null ? connectionParam : getConnection();
            preparedStatement1 = connection.prepareStatement(insertOrder.toString(),
                    Statement.RETURN_GENERATED_KEYS);
            preparedStatement2 = connection.prepareStatement(insertProductDetail.toString());

            preparedStatement1.setLong(1, order.getCustomer().getId());
            preparedStatement1.setString(2, order.getOrderNumber());
            preparedStatement1.setTimestamp(3, new Timestamp(order.getOrderDate().getTime()));
            preparedStatement1.setTimestamp(4, new Timestamp(order.getShippingDate().getTime()));
            preparedStatement1.setString(5, order.getShippingAddress());
            preparedStatement1.setString(6, order.getShippingCountry());
            preparedStatement1.setString(7, order.getPaymentType());
            preparedStatement1.setString(8, order.getPaymentNumber());
            preparedStatement1.setInt(9, order.getOrderStatus().getCode());
            preparedStatement1.setDouble(10, order.getTotal());
            preparedStatement1.executeUpdate();
            Long generatedId = null;
            try (ResultSet generatedKeys = preparedStatement1.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    generatedId = generatedKeys.getLong(1);
                }
            }
            for(ProductDetail productDetail: order.getProductDetailList()){
                preparedStatement2.setLong(1, generatedId);
                preparedStatement2.setLong(2, productDetail.getProduct().getId());
                preparedStatement2.setInt(3, productDetail.getQuantity());
                preparedStatement2.setFloat(4, productDetail.getTotal());
                preparedStatement2.addBatch();
            }
            preparedStatement2.executeBatch();
        } catch(Exception ex) {
            throw new DAOException(ex);
        } finally {
            IOUtils.closeQuietly(preparedStatement1);
            IOUtils.closeQuietly(preparedStatement2);
            if (connectionParam == null){
                IOUtils.closeQuietly(connection);
            }
        }
    }
}
