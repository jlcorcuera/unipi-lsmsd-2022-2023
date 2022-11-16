package it.unipi.lsmsd.ecommerce.model.enums;

public enum ProductTypeEnum {
    ALL("All", 0),
    BOOK("Book", 1),
    BEER("Beer", 2),
    MONITOR("Monitor", 3);

    int type;
    String name;
    ProductTypeEnum(String name, int type){
        this.type = type;
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public static ProductTypeEnum fromType(int type){
        if (type > 0){
            for(ProductTypeEnum productTypeEnum:ProductTypeEnum.values()){
                if (productTypeEnum.getType() == type){
                    return productTypeEnum;
                }
            }
        }
        return null;
    }
}
