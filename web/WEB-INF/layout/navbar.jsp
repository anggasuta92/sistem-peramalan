<%@page import="com.peramalan.services.JSPHandler"%>
<%@page import="com.peramalan.config.MainConfig"%>
<nav class="navbar navbar-dark fixed-top bg-info flex-md-nowrap p-0 shadow">
    <a class="navbar-brand col-sm-3 col-md-2 mr-0" href="<%= JSPHandler.generateUrl(request, "home", 0, "") %>"><text class="text-center" style="font-size: 14px;"><%= MainConfig.APP_NAME %></text></a>
    <ul class="navbar-nav px-3">
        <li class="nav-item text-nowrap">
            <a class="nav-link" href="#">Sign out</a>
        </li>
    </ul>
</nav>