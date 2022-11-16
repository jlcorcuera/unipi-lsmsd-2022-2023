package it.unipi.lsmsd.ecommerce.model.product;

import it.unipi.lsmsd.ecommerce.model.enums.ProductTypeEnum;

import static it.unipi.lsmsd.ecommerce.model.enums.ProductTypeEnum.*;

public abstract class Product {
    private Long id;
    private String name;
    private String shortDescription;
    private String description;
    private String brand;
    private String imageUrl;
    private Float price;
    private Integer stock;

    public static Product newInstance(ProductTypeEnum type){
        switch (type) {
            case BOOK:
                return new Book();
            case MONITOR:
                return new Monitor();
            case BEER:
                return new Beer();
        }
        return null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", shortDescription='" + shortDescription + '\'' +
                ", description='" + description + '\'' +
                ", brand='" + brand + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", price=" + price +
                ", stock=" + stock +
                '}';
    }
}
