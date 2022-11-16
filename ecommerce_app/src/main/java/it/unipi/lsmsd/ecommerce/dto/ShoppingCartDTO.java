package it.unipi.lsmsd.ecommerce.dto;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCartDTO {

    private Float total;
    private List<ProductCartDTO> productCartDTOList = new ArrayList<>();

    public void addProductCartDTO(ProductCartDTO dto){
        this.productCartDTOList.add(dto);
    }

    public Float getTotal() {
        return total;
    }

    public void setTotal(Float total) {
        this.total = total;
    }

    public List<ProductCartDTO> getProductCartDTOList() {
        return productCartDTOList;
    }

    public void setProductCartDTOList(List<ProductCartDTO> productCartDTOList) {
        this.productCartDTOList = productCartDTOList;
    }
}
