package it.unipi.lsmsd.ecommerce.utils;

import it.unipi.lsmsd.ecommerce.dto.CustomerRegistrationDTO;

import javax.servlet.http.HttpServletRequest;

public class ConverterUtils {
    public static CustomerRegistrationDTO fromRequestToCustomerRegDTO(HttpServletRequest request){
        CustomerRegistrationDTO dto = new CustomerRegistrationDTO();
        dto.setFirstName(request.getParameter("firstName"));
        dto.setLastName(request.getParameter("lastName"));
        dto.setAddress(request.getParameter("address"));
        dto.setCountry(request.getParameter("country"));
        dto.setPhone(request.getParameter("phone"));
        dto.setUsername(request.getParameter("username"));
        dto.setPassword(request.getParameter("password"));
        return dto;
    }
}
