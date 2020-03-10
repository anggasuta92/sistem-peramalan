<%-- 
    Document   : index
    Created on : Dec 1, 2019, 10:55:04 PM
    Author     : AnggaSutaDharmawan
--%>
<%@page trimDirectiveWhitespaces="true" %>
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
        <link href="<%= MainConfig.getAssetUrl(request)%>/sb/css/bootstrap.css" rel="stylesheet">
        <link href="<%= MainConfig.getAssetUrl(request)%>/sb/css/sb-admin.css" rel="stylesheet">
        <link rel="stylesheet" href="<%= MainConfig.getAssetUrl(request)%>/sb/font-awesome/css/font-awesome.min.css">
        <link href="<%= MainConfig.getAssetUrl(request)%>/css/apps.css" rel="stylesheet">
        <style>
            .breadcrumb {
                margin-top: 20px !important;
                background-color:transparent !important;
                padding-bottom: 0px;
                margin-bottom: 8px !important;
            }
            .border-bottom {
                border-bottom: 1px solid #eee;
                padding-bottom: 0px;
                margin-bottom: 8px !important;
            }
        </style>
    <body>
        <div id="wrapper">
            <%@include file="navbar.jsp" %>
            
            <div id="page-wrapper">
                <div class="row border-bottom">
                    <div class="col-lg-8">
                        <h3>UD. Parama Store <small>Jalan Gatsu VI no. 46 Denpasar</small> </h3>
                    </div>
                    <div class="col-lg-4" align="right">
                        <% try{ %>
                        <ol class="breadcrumb">
                            <li class="text-muted"><span class="fa fa-plane"></span></li>
                            <%
                                 String[] breadcrumps = request.getAttribute("Page").toString().split(";");
                                 for(int b = 0; b < breadcrumps.length; b++){
                            %>
                            <li class="text-muted"><i class="icon-dashboard"></i> <%= breadcrumps[b] %></li>
                            <%
                                }
                            %>
                        </ol>
                        <% }catch(Exception e){} %>
                    </div>
                </div><!-- /.row -->
            </div><!-- /#page-wrapper -->
            
        </div>

                