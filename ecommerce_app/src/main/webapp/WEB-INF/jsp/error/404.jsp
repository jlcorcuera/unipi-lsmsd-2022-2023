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
</head><!--/head-->

<body>
<div class="container text-center">
  <div class="logo-404">
    <a href="<c:url value="/shop"/>"><img src="<c:url value="/images/logo.png"/>" alt="" /></a>
  </div>
  <div class="content-404">
    <img src="<c:url value="/images/404.png"/>" class="img-responsive" alt="" />
    <h1><b>OPPS!</b> We Couldn’t Find this Page</h1>
    <p>Uh... So it looks like you brock something. The page you are looking for has up and Vanished.</p>
    <h2><a href="<c:url value="/shop"/>">Bring me back Shopping</a></h2>
  </div>
</div>


</body>
</html>
