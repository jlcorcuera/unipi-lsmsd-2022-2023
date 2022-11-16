package it.unipi.lsmsd.ecommerce.model;

import it.unipi.lsmsd.ecommerce.model.product.Product;

public class ProductDetail {
    private Order order;
    private Product product;
    private Integer quantity;
    private Float total;

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Float getTotal() {
        return total;
    }

    public void setTotal(Float total) {
        this.total = total;
    }
}
