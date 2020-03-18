            <%@page import="com.peramalan.config.MainConfig"%>
<%@page import="com.peramalan.services.JSPHandler"%>
<!-- Sidebar -->
            <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
                <!-- Brand and toggle get grouped for better mobile display -->
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-ex1-collapse">
                        <span class="sr-only">Toggle navigation</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <a class="navbar-brand" href="<%= JSPHandler.generateUrl(request, "home", "", "") %>"><%= MainConfig.APP_NAME %></a>
                </div>

                <!-- Collect the nav links, forms, and other content for toggling -->
                <div class="collapse navbar-collapse navbar-ex1-collapse">
                    <ul class="nav navbar-nav side-nav">
                        <li class="active"><a href="<%= JSPHandler.generateUrl(request, "home", "", "") %>"><i class="fa fa-dashboard"></i> Dashboard</a></li>
                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-gear"></i> Data Master <b class="caret"></b></a>
                            <ul class="dropdown-menu">
                                <li><a href="<%= JSPHandler.generateUrl(request, "kategori-barang", "", "") %>">Kategori Barang</a></li>
                                <li><a href="#">Barang</a></li>
                            </ul>
                        </li>
                        <li><a href="charts.html"><i class="fa fa-shopping-cart"></i> Data Penjualan</a></li>
                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-bar-chart-o"></i> Peramalan <b class="caret"></b></a>
                            <ul class="dropdown-menu">
                                <li><a href="#">Penentuan Bobot</a></li>
                                <li><a href="#">Peramalan</a></li>
                                <li><a href="#">Arsip Peramalan</a></li>
                                <li><a href="#">Analisa Peramalan</a></li>
                            </ul>
                        </li>
                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-user"></i> Administrator <b class="caret"></b></a>
                            <ul class="dropdown-menu">
                                <li><a href="#">Role Pengguna</a></li>
                                <li><a href="#">Data Pengguna</a></li>
                            </ul>
                        </li>
                    </ul>

                    <ul class="nav navbar-nav navbar-right navbar-user">
                        <li class="dropdown user-dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-user"></i> John Smith <b class="caret"></b></a>
                            <ul class="dropdown-menu">
                                <li><a href="#"><i class="fa fa-user"></i> Profile</a></li>
                                <li><a href="#"><i class="fa fa-envelope"></i> Inbox <span class="badge">7</span></a></li>
                                <li><a href="#"><i class="fa fa-gear"></i> Settings</a></li>
                                <li class="divider"></li>
                                <li><a href="#"><i class="fa fa-power-off"></i> Log Out</a></li>
                            </ul>
                        </li>
                    </ul>
                </div><!-- /.navbar-collapse -->
            </nav>