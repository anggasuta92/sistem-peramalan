<%@page import="com.peramalan.model.master.Role"%>
<%@page import="java.util.Vector"%>
<%@page import="com.peramalan.model.master.DbRole"%>
<%@page import="com.peramalan.services.MenuServices"%>
<%@page import="com.peramalan.config.MainConfig"%>
<%@page import="com.peramalan.services.JSPHandler"%>
<%@ include file="../../layout/top-page.jsp"%>
    
<div class="row">
    <div class="col-lg-12">
        <div class="panel panel-primary">
            <div class="panel-heading">
                <h3 class="panel-title"><i class="fa fa-archive"></i> Tambah Role User</h3>
            </div>
            <div class="panel-body">
                <form name="roleAddFrm" action="<%= JSPHandler.generateUrl(request, "role", "save", "") %>" method="post">
                    <div class="row">
                        <div class="col-md-12">
                            <div class="form-group">
                                <label for="<%= DbRole.COL_NAMA %>">Nama Role</label>
                                <input type="text" name="<%= DbRole.COL_NAMA %>" value="" class="form-control" />
                            </div>
                        </div>
                    </div>
                    
                    <div class="row">
                        <div class="col-md-12">
                            <table id="tblx" class="table table-bordered table-striped table-hover">
                                <thead>
                                    <tr>
                                        <th>Nama Menu</th>
                                        <th width="20">Akses?</th>
                                    </tr>
                                </thead>
                                <tbody id="table-body">
                                    <%
                                        for (int i = 0; i < MenuServices.strMenu.length; i++) {
                                    %>
                                    <tr>
                                        <td><%= MenuServices.strMenu[i]%></td>
                                        <td>
                                            <input type="checkbox" name="<%= MenuServices.menuPreffix + i %>" value="1" class="form-control" />
                                        </td>
                                    </tr>
                                    <%
                                        }
                                    %>
                                </tbody>
                            </table>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12 pull-left" style="text-align: right;">
                            <button class="btn btn-primary" type="submit" ><span class="fa fa-save"></span> Simpan Data</button>
                            <button class="btn btn-warning" type="button" onclick="back()" ><span class="fa fa-chevron-circle-left"></span> Kembali</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<script>
    function back(){
        location.href = "<%= JSPHandler.generateUrl(request, "role", "", "") %>";
    }
</script>
<%@ include file="../../layout/bottom-page.jsp"%>