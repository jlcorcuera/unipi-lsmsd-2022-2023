package it.unipi.lsmsd.ecommerce.service.exception;

public class BusinessException extends Exception {
    public BusinessException(Exception ex){
        super(ex);
    }
    public BusinessException(String message){
        super(message);
    }
}
