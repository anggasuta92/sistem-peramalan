<%-- 
    Document   : 500
    Created on : Mar 8, 2020, 7:52:32 AM
    Author     : OxysystemPC
--%>

<%@page import="com.peramalan.config.MainConfig"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><%= MainConfig.APP_NAME %></title>
            <!-- Custom styles for this template-->
            <link href="<%= MainConfig.getAssetUrl(request)%>/bootstrap-3.3.7/css/bootstrap.min.css" rel="stylesheet">
        <style>
            html{
            }
            body{
                margin: 0;
                padding: 0;
                background: #e7ecf0;
                font-family: Arial, Helvetica, sans-serif;
            }
            *{
                margin: 0;
                padding: 0;
            }
            p{
                font-size: 12px;
                color: #373737;
                font-family: Arial, Helvetica, sans-serif;
                line-height: 18px;
            }
            p a{
                color: #218bdc;
                font-size: 12px;
                text-decoration: none;
            }
            a{
                outline: none;
            }
            .f-left{
                float:left;
            }
            .f-right{
                float:right;
            }
            .clear{
                clear: both;
                overflow: hidden;
            }
            #block_error{
                width: 845px;
                height: 384px;
                border: 1px solid #cccccc;
                margin: 72px auto 0;
                -moz-border-radius: 4px;
                -webkit-border-radius: 4px;
                border-radius: 4px;
                background: #fff;
            }
            #block_error div{
                padding: 100px 40px 0 186px;
            }
            #block_error div h2{
                color: #218bdc;
                font-size: 24px;
                display: block;
                padding: 0 0 14px 0;
                border-bottom: 1px solid #cccccc;
                margin-bottom: 12px;
                font-weight: normal;
            }
        </style>
    </head>
    <body> 
        <div id="block_error">
            <div>
                <h2 class="text-danger">
                    <span class="fa fa-3x fa-window-close"></span>
                    <strong style="font-size: 64px;">Status <%= request.getAttribute("javax.servlet.error.status_code") %></strong>.<br/>
                    <%= request.getAttribute("javax.servlet.error.exception_type") %>
                </h2>
                <p>
                   <%= request.getAttribute("javax.servlet.error.message") %>
                </p>
            </div>
        </div>

        <script src="<%= MainConfig.getAssetUrl(request)%>/bootstrap-3.3.7/js/bootstrap.min.js"></script>
    </body>
</html>
