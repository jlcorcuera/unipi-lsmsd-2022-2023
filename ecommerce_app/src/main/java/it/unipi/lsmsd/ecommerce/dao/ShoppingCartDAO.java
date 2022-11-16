package it.unipi.lsmsd.ecommerce.dao;

import it.unipi.lsmsd.ecommerce.model.ProductCart;
import it.unipi.lsmsd.ecommerce.model.ShoppingCart;

public interface ShoppingCartDAO {

    void create(ShoppingCart shoppingCart);

    void refreshTTL(Long customerId);
    void removeShoppingCart(Long customerId);
    int addProductCart(Long customerId, ProductCart productCart);
    void updateProductCart(Long customerId, ProductCart productCart);
    void removeProductCart(Long customerId, Long productId);

    ShoppingCart getByCustomerId(Long customerId);
}
