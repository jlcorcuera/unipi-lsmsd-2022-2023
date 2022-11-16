<%@ page import="java.util.List" %>
<%@ page import="it.unipi.lsmsd.ecommerce.dto.ShoppingCartDTO" %>
<%@ page import="it.unipi.lsmsd.ecommerce.dto.ProductCartDTO" %><%--
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
            ShoppingCartDTO shoppingCartDTO = (ShoppingCartDTO) request.getAttribute("shoppingCartDTO");
            List<ProductCartDTO> productCartDTOList = shoppingCartDTO != null ? shoppingCartDTO.getProductCartDTOList(): null;
        %>
        <style>
            .profile_fullname {
                display: block;
                font-size: 14px;
                margin-top: 10px;
                margin-bottom: 10px;
            }
            .product_img {
                width: 110px;
                height: 110px;
            }
            .product_name{
                word-break: break-word;
            }
            .cart_delete_custom{
                display: revert !important;
            }
        </style>
        <script type="text/javascript">

            function addShoppingCart(productId){
                $.post('<c:url value="/shopping-cart"/>', {action: 'add', productId: productId}, function(result){
                    $("#quantity" + productId).text(result);
                }).fail(function(xhr, status, error) {
                    alert('error');
                });
            }
            function add(productId){

            }
            function remove(productId){

            }
            function deleteProduct(productId){
                $("#productIdToDelete").val(productId);
                document.forms.deleteProductCart.submit();
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

        <section id="cart_items">
            <div class="container">
                <div class="breadcrumbs">
                    <ol class="breadcrumb">
                        <li><a href="<c:url value="/shop"/>">Home</a></li>
                        <li class="active">Shopping Cart</li>
                    </ol>
                </div>
                <% if (shoppingCartDTO == null) { %>
                    <div class="alert alert-warning" role="alert">Your cart is empty.</div>
                    <br/>
                    <br/>
                <% } else { %>
                <div class="table-responsive cart_info">
                    <table class="table table-condensed">
                        <thead>
                        <tr class="cart_menu">
                            <td class="image">Item</td>
                            <td class="description"></td>
                            <td class="price">Price</td>
                            <td class="quantity">Quantity</td>
                            <td class="total">Total</td>
                            <td></td>
                        </tr>
                        </thead>
                        <tbody>
                        <% if (productCartDTOList != null && !productCartDTOList.isEmpty()) { %>
                        <% for(ProductCartDTO productCartDTO: productCartDTOList) { %>
                        <tr>
                            <td>
                                <img class="product_img" src="<%= productCartDTO.getImageUrl() %>" alt="">
                            </td>
                            <td class="cart_description">
                                <h4>
                                <a href="" class="product_name"><%= productCartDTO.getProductName()  %></a>
                                </h4>
                            </td>
                            <td class="cart_price">
                                <p>$<%= String.format("%.2f", productCartDTO.getPrice())%></p>
                            </td>
                            <td class="cart_quantity">
                                <div class="cart_quantity_button">
                                    <a class="cart_quantity_up" href="javascript:add(<%= productCartDTO.getProductId() %>)"> + </a>
                                    <input id="quantity<%= productCartDTO.getProductId() %>" class="cart_quantity_input" type="text" name="quantity" value="<%= productCartDTO.getQuantity()%>" autocomplete="off" size="2">
                                    <a class="cart_quantity_down" href="javascript:remove(<%= productCartDTO.getProductId() %>)"> - </a>
                                </div>
                            </td>
                            <td class="cart_total">
                                <p class="cart_total_price">$<%= String.format("%.2f", productCartDTO.getTotal()) %></p>
                            </td>
                            <td class="cart_delete cart_delete_custom">
                                <a class="cart_quantity_delete" href="javascript:deleteProduct(<%= productCartDTO.getProductId() %>);"><i class="fa fa-times"></i></a>
                            </td>
                            <% } %>
                            <% } %>
                        </tr>


                        </tbody>
                    </table>
                </div>
                <% } %>
            </div>
        </section> <!--/#cart_items-->

        <% if (shoppingCartDTO != null) {%>
        <section id="do_action">
            <div class="container">
                <div class="row">

                    <div class="col-sm-6">
                        <div class="total_area">
                            <ul>
                                <li>Cart Sub Total <span>$<%= String.format("%.2f", shoppingCartDTO.getTotal())%></span></li>
                                <li>Shipping Cost <span>Free</span></li>
                                <li>Total <span>$<%= String.format("%.2f", shoppingCartDTO.getTotal())%></span></li>
                            </ul>
                            <a class="btn btn-default check_out" href="<c:url value="/checkout"/>">Check Out</a>
                        </div>
                    </div>
                </div>
            </div>
        </section><!--/#do_action-->

        <form id="deleteProductCart" style="visibility:hidden" action="<c:url value="/shopping-cart"/>" method="post">
            <input name="action" type="text" value="delete">
            <input id="productIdToDelete" name="productId" type="text">
        </form>

        <% } %>
        <%@ include file="/WEB-INF/jsp/template/footer.jsp" %>

    </body>
</html>