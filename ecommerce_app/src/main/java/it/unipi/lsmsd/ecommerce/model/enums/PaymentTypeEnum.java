package it.unipi.lsmsd.ecommerce.model.enums;

public enum PaymentTypeEnum {

    BANK_TRANSFER(1),
    PAYPAL(2);
    int code;

    PaymentTypeEnum(int code){
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
