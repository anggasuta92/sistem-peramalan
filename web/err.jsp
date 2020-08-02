<%@page import="com.peramalan.config.MainConfig"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><%= MainConfig.APP_NAME %></title>
        <link href="<%= MainConfig.getAssetUrl(request)%>/sb/css/bootstrap.css" rel="stylesheet">
    </head>
    <body style="text-align: center; margin: 10px 200px">
        <table width="800" class="table table-bordered">
            <tbody>
                <tr class="danger">
                    <td style="font-weight: bolder" align="center" colspan="3"><h1>Ooopss...</h1></td>
                </tr>
                <tr>
                    <td style="font-weight: bolder" align="left" width="200">Error Code</td>
                    <td style="font-weight: bolder" align="left" width="1">:</td>
                    <td style="font-weight: bolder" align="left"><%= request.getAttribute("javax.servlet.error.status_code") %></td>
                </tr>
                <tr>
                    <td style="font-weight: bolder" align="left" width="100">Error Type</td>
                    <td style="font-weight: bolder" align="left" width="1">:</td>
                    <td align="left"><%= request.getAttribute("javax.servlet.error.exception_type")==null ? "-": request.getAttribute("javax.servlet.error.exception_type") %></td>
                </tr>
                <tr>
                    <td style="font-weight: bolder" align="left" width="100">Error Message</td>
                    <td style="font-weight: bolder" align="left" width="1">:</td>
                    <td align="left">
                        <%= request.getAttribute("javax.servlet.error.exception") %>
                        <br/>
                        <%= request.getAttribute("javax.servlet.error.message") %>
                    </td>
                </tr>
            </tbody>
                
        </table>
        <script src="<%= MainConfig.getAssetUrl(request)%>/sb/js/jquery-1.10.2.js"></script>
        <script src="<%= MainConfig.getAssetUrl(request)%>/sb/js/bootstrap.js"></script>
    </body>
</html>
