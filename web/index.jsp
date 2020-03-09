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
        <link href="<%= MainConfig.getAssetUrl(request)%>/bootstrap-4.4.1-dist/css/bootstrap.min.css" rel="stylesheet">
        <link href="<%= MainConfig.getAssetUrl(request)%>/css/apps.css" rel="stylesheet">
        
        <style type="text/css">
            .login-form {
                width: 340px;
                margin: 50px auto;
            }
            .login-form form {
                margin-bottom: 15px;
                background: #f7f7f7;
                box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);
                padding: 30px;
            }
            .login-form h2 {
                margin: 0 0 15px;
            }
            .form-control, .btn {
                min-height: 38px;
                border-radius: 2px;
            }
            .btn {        
                font-size: 15px;
                font-weight: bold;
            }
        </style>
    </head>

    <body class="bg-dark">
        <div class="login-form">
            <form action="<%= JSPHandler.generateUrl(request, "login", JSPHandler.ACTION_OK, "") %>" method="post">
                <h3 class="text-center"><%= MainConfig.APP_NAME %></h3>
                <div class="form-group">
                    <input type="text" name="username" class="form-control" placeholder="Nama pengguna" required="required">
                </div>
                <div class="form-group">
                    <input type="password" name="password" class="form-control" placeholder="Kata sandi" required="required">
                </div>
                <div class="form-group">
                    <button type="submit" class="btn btn-primary btn-block">Masuk</button>
                </div>
                <div class="clearfix">
                    <a href="#" class="pull-right">Lupa kata sandi?</a>
                </div>        
            </form>
        </div>
            
        
        <script src="<%= MainConfig.getAssetUrl(request)%>/js/jquery.min.js"></script>
        <script src="<%= MainConfig.getAssetUrl(request)%>/bootstrap-4.4.1-dist/js/bootstrap.min.js"></script>
    </body>
</html>
