<%--
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
        <title>Login | E-Shopper</title>
        <%@ include file="/WEB-INF/jsp/template/head_includes.jsp" %>
        <style>
            .country_dropdown{
                padding: 10px 5px;
                margin-bottom: 10px;
            }
        </style>
    </head><!--/head-->

    <body>
        <%@ include file="/WEB-INF/jsp/template/header.jsp" %>
        <%
            String errorMessage = (String) request.getAttribute("errorMessage");
        %>

        <section id="form"><!--form-->
            <div class="container">
                <% if (errorMessage != null) { %>
                <div class="alert alert-danger" role="alert"><%= errorMessage %></div>
                <% } %>
                <div class="row">
                    <div class="col-sm-4 col-sm-offset-1">
                        <div class="login-form"><!--login form-->
                            <h2>Login to your account</h2>
                            <form method="post" action="<c:url value="/login"/>">
                                <input type="email" name="username" placeholder="Email address" />
                                <input type="password" name="password" placeholder="Password" />
                                <input type="hidden" name="action" value="login"/>
                                <button type="submit" class="btn btn-default">Login</button>
                            </form>
                        </div><!--/login form-->
                    </div>
                    <div class="col-sm-1">
                        <h2 class="or">OR</h2>
                    </div>
                    <div class="col-sm-4">
                        <div class="signup-form"><!--sign up form-->
                            <h2>New User Signup!</h2>
                            <form method="post" action="<c:url value="/login"/>">
                                <input type="text" name="firstName" placeholder="First name"/>
                                <input type="text" name="lastName" placeholder="Last name"/>
                                <input type="text" name="address" placeholder="Address"/>
                                <select name="country" class="country_dropdown">
                                    <option>-- Country --</option>
                                    <option>United States</option>
                                    <option>Italy</option>
                                    <option>Perú</option>
                                    <option>Spain</option>
                                    <option>Brasil</option>
                                    <option>Canada</option>
                                </select>
                                <input type="text" name="phone" placeholder="Phone"/>
                                <input type="email" name="username" placeholder="Email address"/>
                                <input type="password" name="password" placeholder="Password"/>
                                <input type="hidden" name="action" value="signup"/>
                                <button type="submit" class="btn btn-default">Signup</button>
                            </form>
                        </div><!--/sign up form-->
                    </div>
                </div>
            </div>
        </section><!--/form-->

        <%@ include file="/WEB-INF/jsp/template/footer.jsp" %>

    </body>
</html>
