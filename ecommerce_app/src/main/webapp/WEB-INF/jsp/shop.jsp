<%@ page import="it.unipi.lsmsd.ecommerce.dto.ProductDTO" %>
<%@ page import="it.unipi.lsmsd.ecommerce.dto.PageDTO" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: jose
  Date: 03/11/22
  Time: 13:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <meta name="author" content="">
        <title>Shop | E-Shopper</title>
        <%@ include file="/WEB-INF/jsp/template/head_includes.jsp" %>
        <%
            /* code used for printing the products */
            PageDTO<ProductDTO> pageDTO = (PageDTO<ProductDTO>)request.getAttribute("pageDTO");
            List<ProductDTO> productDTOList = null;
            Integer numberResults = pageDTO.getTotalCount();
            int currentPage = (int) request.getAttribute("page");
            int type = (int) request.getAttribute("type");
            int startRange = (currentPage - 1) * Constants.PAGE_SIZE + 1;
            int endRange = currentPage * Constants.PAGE_SIZE;
            endRange = endRange > numberResults ? numberResults : endRange;
            Integer numberPages = (int)Math.ceil(numberResults * 1.0 / Constants.PAGE_SIZE) ;
            if (pageDTO != null){
                productDTOList = pageDTO.getEntries();
            }
            /* code for the pagination UI component */
            int startPage = currentPage - 1;
            startPage = startPage <= 0 ? 1 : startPage;
            int endPage = currentPage + 1;
            endPage = endPage > numberPages ? numberPages : endPage;
            String errorMessage = (String) request.getAttribute("errorMessage");
        %>
        <style>
            .profile_fullname {
                display: block;
                font-size: 14px;
                margin-top: 10px;
                margin-bottom: 10px;
            }
            .product_img {
                display: block;
                width: 100px;
                height: 237px;
                object-fit:contain;
            }
        </style>
        <script type="text/javascript">
            var page = <%= currentPage%>;
            var type = <%= type %>;
            function changePage(nextPage, type) {
                url = '<c:url value="/shop"/>?page=' + nextPage;
                if (type != null){
                    url += "&type=" + type;
                }
                searchKeyword = $('#searchKeyword').val();
                if (searchKeyword != undefined && searchKeyword != ''){
                    url += "&searchKeyword=" + searchKeyword;
                }
                window.location = url;
            }
            function addShoppingCart(productId){
                $.post('<c:url value="/shopping-cart"/>', {action: 'add', productId: productId}, function(result){
                    $("#numberOfItems").text(result);
                }).fail(function(xhr, status, error) {
                    alert('error');
                });
            }
        </script>
    </head><!--/head-->

    <body>

        <%@ include file="/WEB-INF/jsp/template/header.jsp" %>

        <section id="advertisement">
            <div class="container">
                <img src="<c:url value="/images/advertisement.jpg"/>" alt="" />
            </div>
        </section>

        <section>
            <div class="container">
                <div class="row">
                    <div class="row">
                        <ul class="left-sidebar pagination">
                            <% if (startPage > 1) { %>
                            <li><a href="javascript:changePage(<%= startPage - 1 %>, <%= type %>);">&laquo;</a></li>
                            <% } %>
                            <%  int idx = startPage;
                                while (idx <= endPage) { %>
                            <li class="<%= idx == currentPage ? "active": "" %>"><a href="javascript:changePage(<%= idx %>, <%= type %>);"><%= idx %></a></li>
                            <%
                                    idx = idx + 1;
                                } %>
                            <% if (endPage < numberPages) { %>
                            <li><a href="javascript:changePage(<%= endPage + 1 %>, <%= type %>);">&raquo;</a></li>
                            <% } %>
                        </ul>
                        <% if (errorMessage != null) { %>
                        <div class="alert alert-danger" role="alert"><%= errorMessage %></div>
                        <% } else { %>
                        <h4><%= startRange%>-<%= endRange%> of over <%= numberResults%> results<%= searchKeyword != null && !searchKeyword.isEmpty() ? " for " + searchKeyword : "" %>.</h4>
                        <% } %>
                        <% if (loggedUser) { %>
                        <div class="col-sm-3">
                            <div class="left-sidebar">
                                <h2>Category</h2>
                                <div class="panel-group category-products" id="accordian"><!--category-productsr-->
                                    <div class="panel panel-default">
                                        <div class="panel-heading">
                                            <h4 class="panel-title"><a href="javascript:changePage(1, 0);"><%= type == 0 ? ">>": "" %> ALL</a></h4>
                                        </div>
                                    </div>
                                    <div class="panel panel-default">
                                        <div class="panel-heading">
                                            <h4 class="panel-title"><a href="javascript:changePage(1, 1);"><%= type == 1 ? ">>": "" %> Monitors</a></h4>
                                        </div>
                                    </div>
                                    <div class="panel panel-default">
                                        <div class="panel-heading">
                                            <h4 class="panel-title"><a href="javascript:changePage(1, 2);"><%= type == 2 ? ">>": "" %> Beers</a></h4>
                                        </div>
                                    </div>
                                    <div class="panel panel-default">
                                        <div class="panel-heading">
                                            <h4 class="panel-title"><a href="javascript:changePage(1, 3);"><%= type == 3 ? ">>": "" %> Books</a></h4>
                                        </div>
                                    </div>
                                </div><!--/category-productsr-->

                                <div class="shipping text-center"><!--shipping-->
                                    <img src="images/home/shipping.jpg" alt="" />
                                </div><!--/shipping-->

                            </div>
                        </div>
                        <% } %>
                        <div class="<%= loggedUser ? "col-sm-9 padding-right": ""%>">
                            <div class="features_items"><!--features_items-->
                                <h2 class="title text-center">OUR PRODUCTS</h2>
                                <%
                                    if (productDTOList != null && !productDTOList.isEmpty()) {
                                        for(ProductDTO productDTO: productDTOList) {
                                %>
                                    <div class="col-sm-4">
                                        <div class="product-image-wrapper">
                                            <div class="single-products">
                                                <div class="productinfo text-center">
                                                    <img class="product_img" src="<%= productDTO.getImageUrl() %>" alt="<%= productDTO.getName() %>" />
                                                    <h2>$ <%= productDTO.getPrice() %> </h2>
                                                    <p><%= productDTO.getName().length() > 50 ? productDTO.getName().substring(0, 45) + "..." : productDTO.getName()  %></p>
                                                    <% if (loggedUser) { %>
                                                        <a href="javascript:addShoppingCart(<%= productDTO.getId() %>);" class="btn btn-default add-to-cart"><i class="fa fa-shopping-cart"></i>Add to cart</a>
                                                    <% } %>
                                                </div>

                                            </div>
                                            <% if (loggedUser) { %>
                                            <div class="choose">
                                                <ul class="nav nav-pills nav-justified">
                                                    <li><a href=""><i class="fa fa-plus-square"></i>Add to wishlist</a></li>
                                                    <li><a href=""><i class="fa fa-plus-square"></i>Write a review</a></li>
                                                </ul>
                                            </div>
                                            <% } %>
                                        </div>
                                    </div>
                                <%
                                        }
                                    }
                                %>
                            </div><!--features_items-->
                        <div/>
                            <ul class="pagination">
                                <% if (startPage > 1) { %>
                                <li><a href="javascript:changePage(<%= startPage - 1 %>, <%= type %>);">&laquo;</a></li>
                                <% } %>
                                <%  idx = startPage;
                                    while (idx <= endPage) { %>
                                <li class="<%= idx == currentPage ? "active": "" %>"><a href="javascript:changePage(<%= idx %>, <%= type %>);"><%= idx %></a></li>
                                <%
                                        idx = idx + 1;
                                    } %>
                                <% if (endPage < numberPages) { %>
                                <li><a href="javascript:changePage(<%= endPage + 1 %>, <%= type %>);">&raquo;</a></li>
                                <% } %>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </section>

        <%@ include file="/WEB-INF/jsp/template/footer.jsp" %>

    </body>
</html>