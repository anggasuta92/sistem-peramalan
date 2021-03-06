<%@page import="com.peramalan.model.master.Pengguna"%>
<%@page import="java.util.Vector"%>
<%@page import="com.peramalan.model.master.Role"%>
<%@page import="com.peramalan.model.master.DbPengguna"%>
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
    
    Pengguna user = new Pengguna();
    try {
        user = (Pengguna) request.getAttribute("data");
    } catch (Exception e) {
    }
    

%>

<style>
    /*.modal-header, h4, .close { */
    .modal-header, h4 {
        background-color: #5cb85c;
        color:white !important;
        text-align: center;
        font-size: 30px;
        height: 50px;
    }
    .modal-footer {
        background-color: #f9f9f9;
    }
</style>

<div class="row">
    <div class="col-lg-5">
        <div class="panel panel-primary">
            <div class="panel-heading">
                <h3 class="panel-title"><i class="fa fa-archive"></i> Ubah Pengguna</h3>
            </div>
            <div class="panel-body">
                <form name="roleAddFrm" action="<%= JSPHandler.generateUrl(request, "user", "update", "") %>" method="post">
                    <input type="hidden" name="<%= DbPengguna.COL_PENGGUNA_ID %>" id="<%= DbPengguna.COL_PENGGUNA_ID %>" value="<%= user.getPenggunaId()%>" >
                    <div class="row">
                        <div class="col-md-12">
                            <div class="form-group">
                                <label for="<%= DbPengguna.COL_NAMA %>">Nama Lengkap</label>
                                <input type="text" name="<%= DbPengguna.COL_NAMA %>" value="<%= user.getNama() %>" class="form-control" />
                            </div>
                            <div class="form-group">
                                <label for="<%= DbPengguna.COL_USERNAME %>">Username</label>
                                <input type="text" name="<%= DbPengguna.COL_USERNAME %>" value="<%= user.getUsername()%>" class="form-control" readonly="true" />
                            </div>
                            <div class="form-group">
                                <label for="<%= DbPengguna.COL_PASSWORD %>">Password</label>
                                <button type="button" id="btnOpenModalUser" class="btn btn-info form-control"> Ubah Password &nbsp; <i class="fa fa-pencil"></i> </button>
                            </div>
                            <div class="form-group">
                                <label for="<%= DbPengguna.COL_ROLE_ID %>">Role</label>
                                <select class="form-control" name="<%= DbPengguna.COL_ROLE_ID %>">
                                    <option value="0">Pilih role...</option>
                                    <%
                                        if(roles!=null && roles.size()>0){
                                            for(Role role : roles){
                                    %>
                                    <option value="<%= role.getRoleId() %>" <%= (role.getRoleId()==user.getRoleId() ? "selected":"") %> ><%= role.getNama() %></option>
                                    <%
                                            }
                                        }
                                    %>
                                </select>
                            </div>
                            <div class="form-group">
                                <label for="<%= DbPengguna.COL_STATUS %>">Status</label>
                                <select class="form-control" name="<%= DbPengguna.COL_STATUS %>">
                                    <option value="1" <%= user.getStatus()==1 ? "selected":"" %> >Aktif</option>
                                    <option value="0" <%= user.getStatus()==0 ? "selected":"" %> >Tidak aktif</option>
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

<!-- Modal -->
<div class="modal fade" id="modalUserPassword" role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header" style="padding:5px;">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                Ubah Password
            </div>
            <div class="modal-body" style="padding:10px">
                <form name="frmUbahPassword" method="post" action="">
                    <div class="row">
                        <div class="col-sm-12">
                            <div class="form-group">
                                <label>Password baru</label>
                                <input type="password" name="<%= DbPengguna.COL_PASSWORD %>" id="<%= DbPengguna.COL_PASSWORD %>" value="" class="form-control" placeholder="Password" />
                            </div>
                            <div class="form-group">
                                <label>Ketik ulang password baru</label>
                                <input type="password" name="ulang_<%= DbPengguna.COL_PASSWORD %>" id="ulang_<%= DbPengguna.COL_PASSWORD %>" value="" class="form-control" placeholder="Ketik ulang password" />
                            </div>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <div class="row">
                    <div class="col-md-12 pull-right" style="padding-top: 20px" align="right">
                        <button type="submit" id="btnSave" onclick="savePassword()" class="btn btn-success btn-default"><span class="fa fa-save"></span> Simpan Data</button>
                        <button type="submit" id="btnClose" class="btn btn-danger btn-default" data-dismiss="modal"><span class="fa fa-chevron-circle-left"></span> Keluar</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
            
<script>
    
    function setErrMessage(old, newMessage){
        if(old.length>0){
            old = old + ", ";
        }
        return old + newMessage;
    }
    
    function savePassword(){
        var newPassword = $("#<%= DbPengguna.COL_PASSWORD %>").val();
        var retypePassword = $("#ulang_<%= DbPengguna.COL_PASSWORD %>").val();
        var userId = $("#<%= DbPengguna.COL_PENGGUNA_ID %>").val();
        var isValid = true;
        var errMessages = "";
        
        if(newPassword.length==0){
            errMessages = setErrMessage(errMessages, "Password tidak boleh kosong");
            isValid = false;
        }
        
        if(retypePassword.length==0){
            errMessages = setErrMessage(errMessages, "Ketik ulang password tidak boleh kosong");
            isValid = false;
        }
        
        if(newPassword.localeCompare(retypePassword)!=0){
            errMessages = setErrMessage(errMessages, "Password tidak sama");
            isValid = false;            
        }

        if(isValid){
            $.ajax({
                type  : 'post',
                data  : { pengguna_id:userId, password:newPassword},
                url   : '<%= JSPHandler.generateUrl(request, "user", "update-pass", "") %>',
                async : false,
                dataType : 'json',
                beforeSend: function() {
                    $("#btnClose").prop('disabled', true);
                    
                    $("#btnSave").html('<span class="fa fa-hourglass"></span> Menyimpan Data...');
                    $("#btnSave").prop('disabled', true);
                },
                success : function(datas){
                    $("#btnClose").prop('disabled', false);
                    
                    $("#btnSave").html('<span class="fa fa-save"></span> Simpan Data');
                    $("#btnSave").prop('disabled', false);
                    
                    swal(datas.message);
                }
            });
           
        }else{
            swal("Perhatian",errMessages,"warning");
        }
    }
    
    $("#btnOpenModalUser").click(function(){
        $("#modalUserPassword").modal({backdrop: 'static', keyboard: false});
        $("#<%= DbPengguna.COL_PASSWORD %>").val("");
        $("#ulang_<%= DbPengguna.COL_PASSWORD %>").val("");
    });
    
    function back(){
        location.href = "<%= JSPHandler.generateUrl(request, "user", "", "") %>";
    }
    
    <%
        if(session.getAttribute(JSPHandler.SESSION_MESSAGING)!=null){ 
    %>
    swal('<%= session.getAttribute(JSPHandler.SESSION_MESSAGING) %>');
    <%
            session.removeAttribute(JSPHandler.SESSION_MESSAGING);
        }
    %>
</script>
<%@ include file="../../layout/bottom-page.jsp"%>