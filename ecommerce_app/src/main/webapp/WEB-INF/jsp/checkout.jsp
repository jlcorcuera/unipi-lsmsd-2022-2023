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
        .prices {
            font-size: 24px;
        }
    </style>
    <script type="text/javascript">

        function generateUUID() { // Public Domain/MIT
            var d = new Date().getTime();//Timestamp
            var d2 = ((typeof performance !== 'undefined') && performance.now && (performance.now()*1000)) || 0;//Time in microseconds since page-load or 0 if unsupported
            return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
                var r = Math.random() * 16;//random number between 0 and 16
                if(d > 0){//Use timestamp until depleted
                    r = (d + r)%16 | 0;
                    d = Math.floor(d/16);
                } else {//Use microseconds since page-load if supported
                    r = (d2 + r)%16 | 0;
                    d2 = Math.floor(d2/16);
                }
                return (c === 'x' ? r : (r & 0x3 | 0x8)).toString(16);
            });
        }

        function placeOrder(){
            shippingAddress = $("#shippingAddressInput").val();
            shippingCountry = $("#shippingCountrySelect").val();
            /*
                THIS INFORMATION IS RECEIVED BY THE PAYMENT GATEWAY IN THE BACKEND.
                FOR THIS EXAMPLE, WE ARE GOING TO SEND THEM FROM THE UI.
            */
            paymentType = $('#remember').is(':checked') ? 'BANK' : 'PAYPAL';
            paymentNumber = generateUUID();
            data = {
                    action: 'place-order',
                    shippingAddress: shippingAddress,
                    shippingCountry: shippingCountry,
                    paymentType: paymentType,
                    paymentNumber: paymentNumber,
            };
            $.post('<c:url value="/shopping-cart"/>', data, function(result){
                alert('Your order has been place!. Order number: ' + result);
                window.location = '<c:url value="/shop"/>';
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
                <li class="active">Check out</li>
            </ol>
        </div><!--/breadcrums-->

<% if (shoppingCartDTO == null) { %>
        <div class="alert alert-warning" role="alert">Your cart is empty.</div>
        <br/>
        <br/>
<% } else { %>
        <div class="shopper-informations">
            <div class="row">
                <div class="col-sm-3">
                    <div class="shopper-info">
                        <p>Shopper Information</p>
                        <form>
                            <input type="text" placeholder="First name" value="<%= authenticatedUserDTO.getFirstName() %>">
                            <input type="text" placeholder="Last name" value="<%= authenticatedUserDTO.getLastName() %>">
                            <input type="text" placeholder="Email" value="<%= authenticatedUserDTO.getEmail() %>">
                            <input type="text" placeholder="Phone" value="<%= authenticatedUserDTO.getPhone() %>">
                            <input type="text" placeholder="Address" value="<%= authenticatedUserDTO.getAddress() %>">
                            <input type="text" placeholder="Country" value="<%= authenticatedUserDTO.getCountry() %>">
                        </form>
                    </div>
                </div>
                <div class="col-sm-5 clearfix">
                    <div class="bill-to">
                        <p>Shipping Information</p>
                        <div class="form-two">
                            <form>
                                <input id="shippingAddressInput"  type="text" placeholder="Shipping Address">
                                <select id="shippingCountrySelect">
                                    <option>-- Country --</option>
                                    <option>United States</option>
                                    <option>Italy</option>
                                    <option>Perú</option>
                                    <option>Spain</option>
                                    <option>Brasil</option>
                                    <option>Canada</option>
                                </select>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="review-payment">
            <h2>Review & Payment</h2>
        </div>

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
                </tr>
                    <% } %>
                <tr>
                    <td colspan="2">&nbsp;</td>
                    <td colspan="3">
                        <table class="table table-condensed total-result">
                            <tr>
                                <td class="prices">Cart Sub Total</td>
                                <td class="prices">$<%= String.format("%.2f", shoppingCartDTO.getTotal()) %></td>
                            </tr>
                            <tr class="shipping-cost">
                                <td class="prices">Shipping Cost</td>
                                <td class="prices">Free</td>
                            </tr>
                            <tr>
                                <td class="prices">Total</td>
                                <td class="prices">$<%= String.format("%.2f", shoppingCartDTO.getTotal()) %></td>
                            </tr>
                        </table>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>

        <div class="payment-options">
					<span>
						<label><input id="bankPaymentCheck" type="checkbox"> Direct Bank Transfer</label>
					</span>
            <span>
						<label><input id="payPaltCheck" type="checkbox"> Paypal</label>
					</span>
            <span class="pull-right">
								<a class="btn btn-default check_out" href="javascript:placeOrder();">Place Order</a>
					</span>
        </div>

    </div>
<% }  %>
<form id="deleteProductCart" style="visibility:hidden" action="<c:url value="/checkout"/>" method="post">
    <input name="action" type="text" value="delete">
    <input id="productIdToDelete" name="productId" type="text">
</form>


<%@ include file="/WEB-INF/jsp/template/footer.jsp" %>

</body>
</html>