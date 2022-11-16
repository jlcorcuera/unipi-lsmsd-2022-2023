package it.unipi.lsmsd.ecommerce.dao.redis;

import com.google.gson.Gson;
import it.unipi.lsmsd.ecommerce.dao.ShoppingCartDAO;
import it.unipi.lsmsd.ecommerce.dao.base.BaseRedisDAO;
import it.unipi.lsmsd.ecommerce.model.ProductCart;
import it.unipi.lsmsd.ecommerce.model.ShoppingCart;
import redis.clients.jedis.Jedis;

import java.util.Date;

import static it.unipi.lsmsd.ecommerce.utils.Constants.*;

public class ShoppingCartRedisDAO extends BaseRedisDAO implements ShoppingCartDAO {

    private String getShoppingCartKeyInNS(Long customerId){
        return REDIS_APP_NAMESPACE + ":shopping-cart:" + customerId;
    }

    @Override
    public void create(ShoppingCart shoppingCart) {
        String key = getShoppingCartKeyInNS(shoppingCart.getCustomerId());
        try(Jedis jedis = getConnection()){
            Gson gson = new Gson();
            jedis.set(key, gson.toJson(shoppingCart));
            jedis.expire(key, SHOPPING_CART_EXPIRATION_IN_SEC);
        }
    }

    @Override
    public void refreshTTL(Long customerId) {
        String key = getShoppingCartKeyInNS(customerId);
        try(Jedis jedis = getConnection()){
            jedis.expire(key, SHOPPING_CART_EXPIRATION_IN_SEC);
        }
    }

    @Override
    public void removeShoppingCart(Long customerId) {
        String key = getShoppingCartKeyInNS(customerId);
        try(Jedis jedis = getConnection()){
            jedis.del(key);
        }
    }

    @Override
    public int addProductCart(Long customerId, ProductCart productCart) {
        String key = getShoppingCartKeyInNS(customerId);
        try(Jedis jedis = getConnection()){
            String jsonStr = jedis.get(key);
            ShoppingCart shoppingCart = null;
            Gson gson = new Gson();
            if (jsonStr == null) {
                shoppingCart = new ShoppingCart(customerId, new Date());
            } else {
                shoppingCart = gson.fromJson(jsonStr, ShoppingCart.class);
            }
            shoppingCart.addProductCart(productCart);
            jedis.set(key, gson.toJson(shoppingCart));
            return shoppingCart.getTotalNumberItems();
        }
    }

    @Override
    public void updateProductCart(Long customerId, ProductCart productCart) {
        String key = getShoppingCartKeyInNS(customerId);
        try(Jedis jedis = getConnection()){
            String jsonStr = jedis.get(key);
            ShoppingCart shoppingCart = null;
            Gson gson = new Gson();
            if (jsonStr == null) {
                shoppingCart = new ShoppingCart(customerId, new Date());
            } else {
                shoppingCart = gson.fromJson(jsonStr, ShoppingCart.class);
            }
            ProductCart currentProductCart = shoppingCart.getProductCart(productCart.getProductId());
            currentProductCart.setQuantity(productCart.getQuantity());
            currentProductCart.setTotal(productCart.getTotal());
            jedis.set(key, gson.toJson(shoppingCart));
        }
    }

    @Override
    public void removeProductCart(Long customerId, Long productId) {
        String key = getShoppingCartKeyInNS(customerId);
        try(Jedis jedis = getConnection()){
            String jsonStr = jedis.get(key);
            if (jsonStr != null) {
                Gson gson = new Gson();
                ShoppingCart shoppingCart = gson.fromJson(jsonStr, ShoppingCart.class);
                shoppingCart.removeProduct(productId);
                if (shoppingCart.isEmpty()){
                    jedis.del(key);
                }else{
                    jedis.set(key, gson.toJson(shoppingCart));
                }
            }
        }
    }

    @Override
    public ShoppingCart getByCustomerId(Long customerId) {
        String key = getShoppingCartKeyInNS(customerId);
        try(Jedis jedis = getConnection()){
            String jsonStr = jedis.get(key);
            if (jsonStr != null) {
                Gson gson = new Gson();
                return gson.fromJson(jsonStr, ShoppingCart.class);
            }
        }
        return null;
    }
}
