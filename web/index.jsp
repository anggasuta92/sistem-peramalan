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

    </head>

    <body>
        <div class="container">
            <div class="row">
                <div class="col-md-4 col-md-offset-4">
                    <div class="login-panel panel panel-success">
                        <div class="panel-heading text-center">
                            <h3 class="panel-title"><strong><%= MainConfig.APP_NAME %></strong></h3>
                        </div>
                        <div class="panel-body">
                            <form method="post" action="<%= JSPHandler.generateUrl(request, "login", "login", "") %>">
                                <div class="form-group text-center text-primary">
                                    Silahkan login untuk menggunakan aplikasi
                                </div>
                                <div class="form-group">
                                    <input class="form-control" placeholder="Nama pengguna" name="username" type="text" required>
                                </div>
                                <div class="form-group">
                                    <input class="form-control" placeholder="Kata sandi" name="password" type="password" value="" required>
                                </div>
                                <button type="submit" class="btn btn-md btn-success btn-block">Login</button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        
        <script src="<%= MainConfig.getAssetUrl(request)%>/sb/js/jquery-1.10.2.js"></script>
        <script src="<%= MainConfig.getAssetUrl(request)%>/sb/js/bootstrap.js"></script>

    </body>
</html>
