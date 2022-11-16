<%@ page import="it.unipi.lsmsd.ecommerce.dto.AuthenticatedUserDTO" %>
<%@ page import="it.unipi.lsmsd.ecommerce.utils.Constants" %><%--
  Created by IntelliJ IDEA.
  User: jose
  Date: 03/11/22
  Time: 13:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%
    Long userId = null;
    String fullname = null;
    boolean loggedUser = false;
    AuthenticatedUserDTO authenticatedUserDTO = null;
    String searchKeyword = request.getAttribute("searchKeyword") != null ? request.getAttribute("searchKeyword").toString() : "";
    String shoppingCartNumItems = request.getAttribute("shoppingCartNumItems") != null ? request.getAttribute("shoppingCartNumItems").toString() : "";
    shoppingCartNumItems = "0".equals(shoppingCartNumItems) ? "": shoppingCartNumItems;
    if (session != null && session.getAttribute(Constants.AUTHENTICATED_USER_KEY) != null){
        authenticatedUserDTO = (AuthenticatedUserDTO) session.getAttribute(Constants.AUTHENTICATED_USER_KEY);
        fullname = authenticatedUserDTO.getFirstName() + " " + authenticatedUserDTO.getLastName();
        userId = authenticatedUserDTO.getId();
        loggedUser = true;
    }
%>
<header id="header"><!--header-->
    <div class="header_top"><!--header_top-->
        <div class="container">
            <div class="row">
                <div class="col-sm-6">
                    <div class="contactinfo">
                        <ul class="nav nav-pills">
                            <li><a href=""><i class="fa fa-phone"></i> +0 800 LSMSD</a></li>
                            <li><a href=""><i class="fa fa-envelope"></i> contact@lsmsd.unipi.it</a></li>
                        </ul>
                    </div>
                </div>
                <div class="col-sm-6">
                    <div class="social-icons pull-right">
                        <ul class="nav navbar-nav">
                            <li><a href=""><i class="fa fa-facebook"></i></a></li>
                            <li><a href=""><i class="fa fa-twitter"></i></a></li>
                            <li><a href=""><i class="fa fa-linkedin"></i></a></li>
                            <li><a href=""><i class="fa fa-dribbble"></i></a></li>
                            <li><a href=""><i class="fa fa-google-plus"></i></a></li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div><!--/header_top-->

    <div class="header-middle"><!--header-middle-->
        <div class="container">
            <div class="row">
                <div class="col-sm-4">
                    <div class="logo pull-left">
                        <a href="<c:url value="/shop"/>"><img src="<c:url value="/images/logo.png"/>" alt="" /></a>
                    </div>
                </div>
                <div class="col-sm-8">
                    <div class="shop-menu pull-right">
                        <ul class="nav navbar-nav">
                            <% if (loggedUser) { %>
                                <li><span class="profile_fullname">Welcome <%= fullname %></span></li>
                                <li><a href=""><i class="fa fa-user"></i> Account</a></li>
                                <li><a href=""><i class="fa fa-star"></i> Wishlist</a></li>
                                <li><a href="<c:url value="/checkout"/>"><i class="fa fa-crosshairs"></i> Checkout</a></li>
                                <li><a href="<c:url value="/shopping-cart"/>"><span id="numberOfItems" class="badge"><%= shoppingCartNumItems %></span> <i class="fa fa-shopping-cart"></i>Cart</a></li>
                            <% } %>
                            <% if (!loggedUser) { %>
                                <li><a href="<c:url value="/login"/>" class="active"><i class="fa fa-lock"></i> Login</a></li>
                            <% } else { %>
                                <li><a href="<c:url value="/login?action=logout"/>" class="active"><i class="fa fa-lock"></i> Logout</a></li>
                            <% } %>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div><!--/header-middle-->

    <div class="header-bottom"><!--header-bottom-->
        <div class="container">
            <div class="row">
                <div class="col-sm-9">
                    <div class="navbar-header">
                        <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                            <span class="sr-only">Toggle navigation</span>
                            <span class="icon-bar"></span>
                            <span class="icon-bar"></span>
                            <span class="icon-bar"></span>
                        </button>
                    </div>
                    <div class="mainmenu pull-left">
                        <ul class="nav navbar-nav collapse navbar-collapse">
                            <li><a href="<c:url value="/shop"/>">Home</a></li>
                            <li><a href="#">Contact</a></li>
                        </ul>
                    </div>
                </div>
                <% if (loggedUser) { %>
                <div class="col-sm-3">
                    <div class="search_box pull-right">
                        <input id="searchKeyword" name="searchKeyword" type="text" placeholder="Search" value="<%= searchKeyword %>"/>
                    </div>
                </div>
                <% } %>
            </div>
        </div>
    </div><!--/header-bottom-->
</header><!--/header-->