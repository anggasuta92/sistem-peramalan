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
        <title><%= MainConfig.APP_NAME%></title>
        <link rel="icon" type="image/x-icon" class="js-site-favicon" href="<%= MainConfig.getAssetUrl(request)%>/img/favicon.ico">
        <link href="<%= MainConfig.getAssetUrl(request)%>/bootstrap-4.4.1-dist/css/bootstrap.min.css" rel="stylesheet">
        <link href="<%= MainConfig.getAssetUrl(request)%>/css/apps.css" rel="stylesheet">
        <link href="<%= MainConfig.getAssetUrl(request)%>/css/dashboard.css" rel="stylesheet">
        <link href="<%= MainConfig.getAssetUrl(request)%>/fontawesome/css/font-awesome.min.css" rel="stylesheet">
        
    <body>
        <%@include file="navbar.jsp" %>
        
        <div class="container-fluid">
            <div class="row">
                
                <%@include file="sidebar.jsp" %>
                
                <!-- main & breadcrump -->
                <main role="main" class="col-md-9 ml-sm-auto col-lg-10 px-4">
                  <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
                    <h1 class="h2">UD. Parama Store</h1>
                    <div class="btn-toolbar mb-2 mb-md-0">
                      <div class="btn-group mr-2">
                        Home >> Dashboard
                      </div>
                    </div>
                  </div>
                <!-- here content -->
                asd
                </main>
            </div>
        </div>
                