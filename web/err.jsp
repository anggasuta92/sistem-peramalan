<%@page import="com.peramalan.config.MainConfig"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><%= MainConfig.APP_NAME %></title>
        <link href="<%= MainConfig.getAssetUrl(request)%>/sb/css/bootstrap.css" rel="stylesheet">
    </head>
    <body style="text-align: center">
        <h1 style="font-weight: bolder">Status <%= request.getAttribute("javax.servlet.error.status_code") %>!</h1>
        <div><%= request.getAttribute("javax.servlet.error.exception_type")==null ? "": request.getAttribute("javax.servlet.error.exception_type") %></div>
        <div><%= request.getAttribute("javax.servlet.error.message") %></div>
        <script src="<%= MainConfig.getAssetUrl(request)%>/sb/js/jquery-1.10.2.js"></script>
        <script src="<%= MainConfig.getAssetUrl(request)%>/sb/js/bootstrap.js"></script>
    </body>
</html>
