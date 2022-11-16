package it.unipi.lsmsd.ecommerce.model.enums;

import it.unipi.lsmsd.ecommerce.model.Customer;
import it.unipi.lsmsd.ecommerce.model.RegisteredUser;

public enum UserTypeEnum {

    MANAGER(1),
    CUSTOMER(2);

    int code;


    public int getCode() {
        return code;
    }

    UserTypeEnum(int code){
        this.code = code;
    }

    public static boolean isManager(int code) {
        return MANAGER.code == code;
    }

    public static UserTypeEnum fromObject(RegisteredUser instance){
        return instance instanceof Customer ? CUSTOMER : MANAGER;
    }


}
