<%-- 
    Document   : index
    Created on : Dec 1, 2019, 10:55:04 PM
    Author     : AnggaSutaDharmawan
--%>

<%@page import="com.peramalan.services.JSPHandler"%>
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
        <script src="<%= MainConfig.getAssetUrl(request)%>/sb/js/jquery-1.10.2.js"></script>
        <script src="<%= MainConfig.getAssetUrl(request)%>/js/sweetalert.js"></script>

    </head>

    <body>
        <div class="login-page">
            <div class="form">
                <form class="login-form" action="<%= JSPHandler.generateUrl(request, "login", "auth", "") %>" method="post">
                    <img src="<%= MainConfig.getAssetUrl(request)%>/images/avatar.png" alt="Avatar" style="width:120px;height: 120px;border-radius: 50%;">
                    <h3><strong>SISTEM PERAMALAN</strong></h3>
                    <input type="text" name="user" placeholder="Username"/>
                    <input type="password" name="pass" placeholder="Password"/>
                    <button>login</button>
                </form>
            </div>
        </div>
                    
<script>
    <%
        if(session.getAttribute(JSPHandler.SESSION_MESSAGING)!=null){ 
    %>
    swal('<%= session.getAttribute(JSPHandler.SESSION_MESSAGING) %>');
    swal("Login gagal...", "<%= session.getAttribute(JSPHandler.SESSION_MESSAGING) %>", "warning");
    <%
            session.removeAttribute(JSPHandler.SESSION_MESSAGING);
        }
    %>
</script>
                    
        <script src="<%= MainConfig.getAssetUrl(request)%>/sb/js/jquery-1.10.2.js"></script>
        <script src="<%= MainConfig.getAssetUrl(request)%>/sb/js/bootstrap.js"></script>

    </body>
</html>
