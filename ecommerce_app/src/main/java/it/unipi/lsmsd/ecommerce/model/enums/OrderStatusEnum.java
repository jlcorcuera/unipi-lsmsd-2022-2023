package it.unipi.lsmsd.ecommerce.model.enums;

import java.util.HashMap;
import java.util.Map;

public enum OrderStatusEnum {

    PAID(1),
    IN_PREPARATION(2),
    SHIPPED(3),
    DELIVERED(4),
    CANCELLED(5);

    int code;
    private static Map<Integer, OrderStatusEnum> cache = new HashMap<Integer, OrderStatusEnum>();

    static {
        cache.put(PAID.code, PAID);
        cache.put(IN_PREPARATION.code, IN_PREPARATION);
        cache.put(SHIPPED.code, SHIPPED);
        cache.put(DELIVERED.code, DELIVERED);
        cache.put(CANCELLED.code, CANCELLED);
    }

    public int getCode() {
        return code;
    }

    OrderStatusEnum(int code){
        this.code = code;
    }

    public static OrderStatusEnum getById(int orderStatus){
        return cache.get(orderStatus);
    }


}
