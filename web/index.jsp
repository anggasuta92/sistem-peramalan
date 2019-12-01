<%-- 
    Document   : index
    Created on : Dec 1, 2019, 10:55:04 PM
    Author     : AnggaSutaDharmawan
--%>
<!DOCTYPE html>
<html lang="en">
    <%@page import="com.peramalan.config.MainConfig"%>
    <%@page contentType="text/html" pageEncoding="UTF-8"%>
    <!DOCTYPE html>
    <html>
        <head>
            <meta charset="utf-8">
            <meta http-equiv="X-UA-Compatible" content="IE=edge">
            <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
            <meta name="description" content="">
            <meta name="author" content="">
            <title><%= MainConfig.APP_NAME%></title>
            <link rel="icon" type="image/x-icon" class="js-site-favicon" href="<%= MainConfig.getAssetUrl(request)%>/img/favicon.ico">
            
            <!-- Custom fonts for this template-->
            <link href="<%= MainConfig.getAssetUrl(request)%>/vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">

            <!-- Custom styles for this template-->
            <link href="<%= MainConfig.getAssetUrl(request)%>/css/sb-admin-2.min.css" rel="stylesheet">

        </head>
        <body class="bg-secondary">

            <div class="container">
                <!-- Outer Row -->
                <div class="row justify-content-center">
                    <div class="col-xl-5 col-lg-12 col-md-9">
                        <div class="card o-hidden border-0 shadow-lg my-5">
                            <div class="card-body p-0">
                                <!-- Nested Row within Card Body -->
                                <div class="row">
                                    <div class="col-lg-12">
                                        <div class="p-5">
                                            <div class="text-center">
                                                <h1 class="h4 text-gray-900 mb-4"><strong><%= MainConfig.APP_NAME %></strong></h1>
                                            </div>
                                                <form class="user" action="<%= MainConfig.getRootApplicationUrl(request) %>">
                                                <div class="form-group">
                                                    <input type="email" class="form-control form-control-user" id="exampleInputEmail" aria-describedby="emailHelp" placeholder="Enter Email Address...">
                                                </div>
                                                <div class="form-group">
                                                    <input type="password" class="form-control form-control-user" id="exampleInputPassword" placeholder="Password">
                                                </div>
                                                <div class="form-group">
                                                    <div class="custom-control custom-checkbox small">
                                                        <input type="checkbox" class="custom-control-input" id="customCheck">
                                                        <label class="custom-control-label" for="customCheck">Remember Me</label>
                                                    </div>
                                                </div>
                                                <input type="submit" value="Login" class="btn btn-primary btn-user btn-block">
                                            </form>
                                            <hr>
                                            <div class="text-center">
                                                <a class="small" href="forgot-password.html">Forgot Password?</a>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <script src="<%= MainConfig.getAssetUrl(request)%>/vendor/jquery/jquery.min.js"></script>
            <script src="<%= MainConfig.getAssetUrl(request)%>/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
            <script src="<%= MainConfig.getAssetUrl(request)%>/vendor/jquery-easing/jquery.easing.min.js"></script>
            <script src="<%= MainConfig.getAssetUrl(request)%>/js/sb-admin-2.min.js"></script>
        </body>
    </html>
