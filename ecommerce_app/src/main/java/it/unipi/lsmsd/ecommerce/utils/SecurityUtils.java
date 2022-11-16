package it.unipi.lsmsd.ecommerce.utils;

import it.unipi.lsmsd.ecommerce.dto.AuthenticatedUserDTO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SecurityUtils {

    public static AuthenticatedUserDTO getAuthenticatedUser(HttpServletRequest request){
        HttpSession session = request.getSession();
        return (AuthenticatedUserDTO)session.getAttribute(Constants.AUTHENTICATED_USER_KEY);
    }
}
