<%@page import="java.util.Vector"%>
<%@page import="com.peramalan.model.master.Role"%>
<%@page import="com.peramalan.model.master.DbSystemUser"%>
<%@page import="com.peramalan.services.MenuServices"%>
<%@page import="com.peramalan.config.MainConfig"%>
<%@page import="com.peramalan.services.JSPHandler"%>
<%@ include file="../../layout/top-page.jsp"%>

<%
    Vector<Role> roles = new Vector<Role>();
    try {
        roles = (Vector<Role>) request.getAttribute("roles");
    } catch (Exception e) {
    }
%>

<div class="row">
    <div class="col-lg-5">
        <div class="panel panel-primary">
            <div class="panel-heading">
                <h3 class="panel-title"><i class="fa fa-archive"></i> Tambah Role User</h3>
            </div>
            <div class="panel-body">
                <form name="roleAddFrm" action="<%= JSPHandler.generateUrl(request, "user", "save", "") %>" method="post">
                    <div class="row">
                        <div class="col-md-12">
                            <div class="form-group">
                                <label for="<%= DbSystemUser.COL_NAMA %>">Nama Lengkap</label>
                                <input type="text" name="<%= DbSystemUser.COL_NAMA %>" value="" class="form-control" />
                            </div>
                            <div class="form-group">
                                <label for="<%= DbSystemUser.COL_USERNAME %>">Username</label>
                                <input type="text" name="<%= DbSystemUser.COL_USERNAME %>" value="" class="form-control" />
                            </div>
                            <div class="form-group">
                                <label for="<%= DbSystemUser.COL_PASSWORD %>">Password</label>
                                <input type="text" name="<%= DbSystemUser.COL_PASSWORD %>" value="" class="form-control" />
                            </div>
                            <div class="form-group">
                                <label for="ulang_<%= DbSystemUser.COL_PASSWORD %>">Ketik Ulang Password</label>
                                <input type="text" name="ulang_<%= DbSystemUser.COL_PASSWORD %>" value="" class="form-control" />
                            </div>
                            <div class="form-group">
                                <label for="<%= DbSystemUser.COL_ROLE_ID %>">Role</label>
                                <select class="form-control" name="<%= DbSystemUser.COL_ROLE_ID %>">
                                    <option value="0">Pilih role...</option>
                                    <%
                                        if(roles!=null && roles.size()>0){
                                            for(Role role : roles){
                                    %>
                                    <option value="<%= role.getRoleId() %>"><%= role.getNama() %></option>
                                    <%
                                            }
                                        }
                                    %>
                                </select>
                            </div>
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
        location.href = "<%= JSPHandler.generateUrl(request, "user", "", "") %>";
    }
</script>
<%@ include file="../../layout/bottom-page.jsp"%>