package it.unipi.lsmsd.ecommerce.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ShoppingCart {
    private Long customerId;
    private Date updatedDate;
    private List<ProductCart> productCartList = new ArrayList<>();

    public boolean isEmpty(){
        return productCartList.isEmpty();
    }

    public ShoppingCart(Long customerId, Date updatedDate) {
        this.customerId = customerId;
        this.updatedDate = updatedDate;
    }

    public void addProductCart(ProductCart productCart){
        ProductCart productCart1 = getProductCart(productCart.getProductId());
        if (productCart1 != null){
            productCart1.setQuantity(productCart.getQuantity() + productCart1.getQuantity());
            productCart1.setTotal(productCart.getTotal() + productCart1.getTotal());
        } else {
            this.productCartList.add(productCart);
        }
    }

    public void updateProductCart(ProductCart productCart){
        ProductCart productCart1 = getProductCart(productCart.getProductId());
        if (productCart1 != null){
            productCart1.setQuantity(productCart.getQuantity());
            productCart1.setTotal(productCart.getTotal());
        } else {
            this.productCartList.add(productCart);
        }
    }

    public void removeProduct(Long productId){
        int i = 0;
        while (i < productCartList.size()){
            if (productCartList.get(i).getProductId().equals(productId)){
                productCartList.remove(i);
                break;
            }
            i++;
        }
    }

    public ProductCart getProductCart(Long productId){
        int i = 0;
        for(ProductCart productCart:productCartList){
            if (productCart.getProductId().equals(productId)){
                return productCart;
            }
        }
        return null;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public List<ProductCart> getProductCartList() {
        return productCartList;
    }

    public void setProductCartList(List<ProductCart> productCartList) {
        this.productCartList = productCartList;
    }

    public int getTotalNumberItems() {
        int totalNumberItems = 0;
        for(ProductCart productCart:getProductCartList()){
            totalNumberItems += productCart.getQuantity();
        }
        return totalNumberItems;
    }

    public Float getTotal() {
        float total = 0;
        for(ProductCart productCart:getProductCartList()){
            total += productCart.getTotal();
        }
        return total;
    }
}
