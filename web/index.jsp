<%-- 
    Document   : index
    Created on : Dec 1, 2019, 10:55:04 PM
    Author     : AnggaSutaDharmawan
--%>

<%@page import="com.peramalan.services.JSPHandler"%>
<%
    Connection conn = DbConnection.getConnection();
%>
<%@page import="com.peramalan.config.MainConfig"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.peramalan.conn.DbConnection"%>
<%@page import="java.sql.Connection"%>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta name="description" content="">
        <meta name="author" content="">
        <title><%= MainConfig.APP_NAME %></title>
        <link rel="icon" type="image/x-icon" class="js-site-favicon" href="<%= MainConfig.getAssetUrl(request)%>/img/favicon.ico">
        <link href="<%= MainConfig.getAssetUrl(request)%>/sb/css/bootstrap.css" rel="stylesheet">
        <link href="<%= MainConfig.getAssetUrl(request)%>/sb/css/sb-admin.css" rel="stylesheet">
        <link rel="stylesheet" href="<%= MainConfig.getAssetUrl(request)%>/sb/font-awesome/css/font-awesome.min.css">
        <link href="<%= MainConfig.getAssetUrl(request)%>/css/apps.css" rel="stylesheet">
        <link href="<%= MainConfig.getAssetUrl(request)%>/css/login.css" rel="stylesheet">

    </head>

    <body>
        <div class="login-page">
            <div class="form">
                <form class="login-form" action="<%= JSPHandler.generateUrl(request, "home", "", "") %>" method="post">
                    <img src="<%= MainConfig.getAssetUrl(request)%>/images/avatar.png" alt="Avatar" style="width:120px;height: 120px;border-radius: 50%;">
                    <h3><strong>SISTEM PERAMALAN</strong></h3>
                    <input type="text" placeholder="Nama Pengguna"/>
                    <input type="password" placeholder="Kata Sandi"/>
                    <button>login</button>
                </form>
            </div>
        </div>
        <script src="<%= MainConfig.getAssetUrl(request)%>/sb/js/jquery-1.10.2.js"></script>
        <script src="<%= MainConfig.getAssetUrl(request)%>/sb/js/bootstrap.js"></script>

    </body>
</html>
