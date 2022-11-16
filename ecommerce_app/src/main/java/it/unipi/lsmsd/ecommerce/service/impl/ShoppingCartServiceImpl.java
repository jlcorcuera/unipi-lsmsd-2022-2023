package it.unipi.lsmsd.ecommerce.service.impl;

import it.unipi.lsmsd.ecommerce.dao.DAOLocator;
import it.unipi.lsmsd.ecommerce.dao.OrderDAO;
import it.unipi.lsmsd.ecommerce.dao.ProductDAO;
import it.unipi.lsmsd.ecommerce.dao.ShoppingCartDAO;
import it.unipi.lsmsd.ecommerce.dao.base.BaseMySQLDAO;
import it.unipi.lsmsd.ecommerce.dao.enums.DataRepositoryEnum;
import it.unipi.lsmsd.ecommerce.dto.CheckoutInfoDTO;
import it.unipi.lsmsd.ecommerce.dto.ProductCartDTO;
import it.unipi.lsmsd.ecommerce.dto.ShoppingCartDTO;
import it.unipi.lsmsd.ecommerce.model.Order;
import it.unipi.lsmsd.ecommerce.model.ProductCart;
import it.unipi.lsmsd.ecommerce.model.ProductDetail;
import it.unipi.lsmsd.ecommerce.model.ShoppingCart;
import it.unipi.lsmsd.ecommerce.model.enums.OrderStatusEnum;
import it.unipi.lsmsd.ecommerce.model.product.Product;
import it.unipi.lsmsd.ecommerce.service.ShoppingCartService;
import it.unipi.lsmsd.ecommerce.service.exception.BusinessException;
import it.unipi.lsmsd.ecommerce.utils.Constants;
import redis.clients.jedis.util.IOUtils;

import java.sql.Connection;
import java.util.*;

public class ShoppingCartServiceImpl implements ShoppingCartService {

    private ShoppingCartDAO shoppingCartDAO;
    private OrderDAO orderDAO;
    private ProductDAO productDAO;

    public ShoppingCartServiceImpl(){
        shoppingCartDAO = DAOLocator.getShoppingCartDAO(DataRepositoryEnum.REDIS);
        orderDAO = DAOLocator.getOrderDAO(DataRepositoryEnum.MYSQL);
        productDAO = DAOLocator.getProductDAO(DataRepositoryEnum.MYSQL);
    }

    @Override
    public String checkout(Long customerId, CheckoutInfoDTO checkoutInfoDTO)  throws BusinessException{
        ShoppingCart shoppingCart = null;
        boolean success = false;
        Connection connection = null;
        try {
            connection = BaseMySQLDAO.getConnection();
            connection.setAutoCommit(false);
            // step 1: stock verification
            shoppingCart = shoppingCartDAO.getByCustomerId(customerId);
            List<ProductCart> products = shoppingCart.getProductCartList();
            Map<Long, Product> productsMap = new HashMap<Long, Product>();
            for(ProductCart productCart: products){
                Product product = productDAO.getProductById(productCart.getProductId(), connection);
                if (product.getStock().compareTo(productCart.getQuantity()) < 0){
                    throw new BusinessException("There is no stock for product: " + product.getId());
                }
                productsMap.put(product.getId(), product);
            }
            /*
                step 2: order creation - this logic should be moved to OrderService
                why? creating an order includes the operation of updating the stock.
                Note that creating an order can be done by using another channel.
                For example: when the manager has the possibility of creating an order
                for a customer (in this case there is no need for a shopping cart).
            */
            Float total = 0.0f;
            Order order = toOrder(customerId, checkoutInfoDTO);
            for(ProductCart productCart: products){
                Long productId = productCart.getProductId();
                Product product = productsMap.get(productId);
                ProductDetail productDetail = toProductDetail(product, productCart, order);
                order.addProductDetail(productDetail);
                total = total + productCart.getTotal();
            }
            order.setTotal(total);
            orderDAO.save(order, connection);
            // step 3: update stock
            for(ProductCart productCart: products){
                Long productId = productCart.getProductId();
                Product product = productsMap.get(productId);
                Integer newStock = product.getStock() - productCart.getQuantity();
                productDAO.updateStock(productId, newStock, connection);
            }
            // step 4: persisting database transaction
            connection.commit();
            // step 5: clean shopping cart
            shoppingCartDAO.removeShoppingCart(customerId);
            success = true;
            return order.getOrderNumber();
        } catch (Exception e) {
            throw new BusinessException(e);
        } finally {
            /*
                in case of errors during shopping cart deletion,
                we must restore the shopping cart data.
            */
            IOUtils.closeQuietly(connection);

            if (!success && shoppingCart != null){
                shoppingCartDAO.create(shoppingCart);
            }
        }
    }

    @Override
    public int addProductCart(Long customerId, ProductCartDTO productCartDTO) throws BusinessException {
        try {
            ProductCart productCart = new ProductCart();
            productCart.setProductId(productCartDTO.getProductId());
            productCart.setQuantity(productCartDTO.getQuantity());
            Product product = productDAO.getProductById(productCartDTO.getProductId());
            productCart.setPrice(product.getPrice());
            productCart.setName(product.getName());
            productCart.setImageUrl(product.getImageUrl());
            productCart.setTotal(product.getPrice() * productCartDTO.getQuantity());
            int numberOfItems = shoppingCartDAO.addProductCart(customerId, productCart);
            shoppingCartDAO.refreshTTL(customerId);
            return numberOfItems;
        } catch (Exception e) {
            throw new BusinessException(e);
        }
    }


    @Override
    public void removeProductCart(Long customerId, Long productId) throws BusinessException {
        try {
            shoppingCartDAO.removeProductCart(customerId, productId);
            shoppingCartDAO.refreshTTL(customerId);
        } catch (Exception e) {
            throw new BusinessException(e);
        }
    }

    @Override
    public ShoppingCartDTO getByCustomerId(Long customerId) {
        ShoppingCart shoppingCart = shoppingCartDAO.getByCustomerId(customerId);
        if (shoppingCart != null){
            ShoppingCartDTO shoppingCartDTO = new ShoppingCartDTO();
            shoppingCartDTO.setTotal(shoppingCart.getTotal());
            for(ProductCart productCart: shoppingCart.getProductCartList()){
                ProductCartDTO productCartDTO = new ProductCartDTO();
                productCartDTO.setProductId(productCart.getProductId());
                productCartDTO.setProductName(productCart.getName());
                productCartDTO.setPrice(productCart.getPrice());
                productCartDTO.setQuantity(productCart.getQuantity());
                productCartDTO.setTotal(productCart.getTotal());
                productCartDTO.setImageUrl(productCart.getImageUrl());
                shoppingCartDTO.addProductCartDTO(productCartDTO);
            }
            return shoppingCartDTO;
        }
        return null;
    }

    @Override
    public int getTotalNumberOfItems(Long customerId) {
        ShoppingCart shoppingCart = shoppingCartDAO.getByCustomerId(customerId);
        if (shoppingCart != null){
            return shoppingCart.getTotalNumberItems();
        }
        return 0;
    }

    private ProductDetail toProductDetail(Product product, ProductCart productCart, Order order){
        ProductDetail productDetail = new ProductDetail();
        productDetail.setProduct(product);
        productDetail.setQuantity(productCart.getQuantity());
        productDetail.setTotal(productCart.getTotal());
        productDetail.setOrder(order);
        return productDetail;
    }

    private Order toOrder(Long customerId, CheckoutInfoDTO checkoutInfoDTO){
        Order order = new Order(customerId);
        order.setOrderNumber(UUID.randomUUID().toString());
        order.setOrderDate(new Date());
        order.setOrderStatus(OrderStatusEnum.PAID);
        order.setPaymentNumber(checkoutInfoDTO.getPaymentNumber());
        order.setPaymentType(checkoutInfoDTO.getPaymentType());
        order.setShippingAddress(checkoutInfoDTO.getShippingAddress());
        order.setShippingCountry(checkoutInfoDTO.getShippingCountry());
        /* lets set it 10 days from now */
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, Constants.SHIPPING_DURATION_DAYS);
        order.setShippingDate(calendar.getTime());
        return order;
    }
}
