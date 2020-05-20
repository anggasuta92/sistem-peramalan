<%@page import="com.peramalan.model.master.DbSystemUser"%>
<%@page import="com.peramalan.model.master.SystemUser"%>
<%@page import="com.peramalan.services.MenuServices"%>
<%@page import="com.peramalan.services.LoginServices"%>
<%@page import="com.peramalan.config.MainConfig"%>
<%@page import="com.peramalan.services.JSPHandler"%>

<%
    HttpSession sess = request.getSession();
    long userId = Long.parseLong(sess.getAttribute(LoginServices.LOGIN_USER_ID).toString());
    
    SystemUser user = new SystemUser();
    try {
        user = DbSystemUser.findById(userId);
    } catch (Exception e) {
    }
    
    boolean mnKategoriBarang = LoginServices.isGranted(request, MenuServices.MENU_KATEGORI_BARANG, userId);
    boolean mnBarang = LoginServices.isGranted(request, MenuServices.MENU_BARANG, userId);
    boolean mnPenjualan = LoginServices.isGranted(request, MenuServices.MENU_PENJUALAN, userId);
    boolean mnPeramalan = LoginServices.isGranted(request, MenuServices.MENU_PERAMALAN, userId);
    boolean mnArsipPeramalan = LoginServices.isGranted(request, MenuServices.MENU_ARSIP_PERAMALAN, userId);
    boolean mnAnalisisPeramalan = LoginServices.isGranted(request, MenuServices.MENU_ANALISIS_PERAMALAN, userId);
    boolean mnRole = LoginServices.isGranted(request, MenuServices.MENU_ADMINISTRATOR_ROLE_PENGGUNA, userId);
    boolean mnUser = LoginServices.isGranted(request, MenuServices.MENU_ADMINISTRATOR_DATA_PENGGUNA, userId);
%>

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
                    <a class="navbar-brand" href="<%= JSPHandler.generateUrl(request, "home", "", "") %>"><i class="fa fa-fire"></i> <%= MainConfig.APP_NAME %></a>
                </div>

                <!-- Collect the nav links, forms, and other content for toggling -->
                <div class="collapse navbar-collapse navbar-ex1-collapse">
                    <ul class="nav navbar-nav side-nav">
                        <li class="" style="padding: 40px;padding-bottom: 10px;margin: auto; text-align: center">
                            <img src="<%= MainConfig.getAssetUrl(request)%>/images/avatar.png" alt="Avatar" style="width:120px;height: 120px;border-radius: 50%;">
                            <label style="color: #fff;">User Name</label>
                        </li>
                        <li class=""><a href="<%= JSPHandler.generateUrl(request, "home", "", "") %>"><i class="fa fa-dashboard"></i> Dashboard</a></li>
                        <% if(mnKategoriBarang || mnBarang){ %>
                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-gear"></i> Data Master <b class="caret"></b></a>
                            <ul class="dropdown-menu">
                                <% if(mnKategoriBarang){ %>
                                <li><a href="<%= JSPHandler.generateUrl(request, "kategori-barang", "", "") %>">Kategori Barang</a></li>
                                <% } %>
                                <% if(mnBarang){ %>
                                <li><a href="<%= JSPHandler.generateUrl(request, "barang", "", "") %>">Barang</a></li>
                                <% } %>
                            </ul>
                        </li>
                        <% } %>
                        
                        <% if(mnPenjualan){ %>
                        <li><a href="<%= JSPHandler.generateUrl(request, "penjualan", "", "") %>"><i class="fa fa-shopping-cart"></i> Data Penjualan</a></li>
                        <% } %>
                        
                        <% if(mnPeramalan || mnArsipPeramalan || mnAnalisisPeramalan){ %>
                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-bar-chart-o"></i> Peramalan <b class="caret"></b></a>
                            <ul class="dropdown-menu">
                                <!-- <li><a href="#">Penentuan Bobot</a></li> -->
                                <% if(mnPenjualan){ %>
                                <li><a href="<%= JSPHandler.generateUrl(request, "peramalan", "", "") %>">Peramalan</a></li>
                                <% } %>
                                <% if(mnArsipPeramalan){ %>
                                <li><a href="#">Arsip Peramalan</a></li>
                                <% } %>
                                <% if(mnAnalisisPeramalan){ %>
                                <li><a href="#">Analisa Peramalan</a></li>
                                <% } %>
                            </ul>
                        </li>
                        <% } %>
                        
                        <% if(mnRole || mnUser){ %>
                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-user"></i> Administrator <b class="caret"></b></a>
                            <ul class="dropdown-menu">
                                <% if(mnRole){ %>
                                <li><a href="<%= JSPHandler.generateUrl(request, "role", "", "") %>">Role Pengguna</a></li>
                                <% } %>
                                <% if(mnRole){ %>
                                <li><a href="<%= JSPHandler.generateUrl(request, "user", "", "") %>">Data Pengguna</a></li>
                                <% } %>
                            </ul>
                        </li>
                        <% } %>
                    </ul>

                    <ul class="nav navbar-nav navbar-right navbar-user">
                        <li class="dropdown user-dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-user"></i> <%= user.getNama().toUpperCase() %>&nbsp; <b class="caret"></b></a>
                            <ul class="dropdown-menu">
                                <li><a href="#"><i class="fa fa-user"></i> Profile</a></li>
                                <li><a href="#"><i class="fa fa-envelope"></i> Inbox <span class="badge">7</span></a></li>
                                <li><a href="#"><i class="fa fa-gear"></i> Settings</a></li>
                                <li class="divider"></li>
                                <li><a href="<%= JSPHandler.generateUrl(request, "login", "logout", "") %>"><i class="fa fa-power-off"></i> Log Out</a></li>
                            </ul>
                        </li>
                    </ul>
                </div><!-- /.navbar-collapse -->
            </nav>