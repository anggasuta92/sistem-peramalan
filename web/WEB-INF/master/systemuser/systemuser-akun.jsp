<%@page import="com.peramalan.services.PaginationServices"%>
<%@page import="com.peramalan.config.MainConfig"%>
<%@page import="com.peramalan.services.JSPHandler"%>
<%@ include file="../../layout/top-page.jsp"%>

<%
    Pengguna systemUser = new Pengguna();
    try {
        systemUser = (Pengguna) request.getAttribute("data");
    } catch (Exception e) {
    }
%>

<div class="row">
    <div class="col-lg-6">
        <div class="panel panel-primary">
            <div class="panel-heading">
                <h3 class="panel-title"><i class="fa fa-archive"></i> Akun Saya</h3>
            </div>
            <div class="panel-body">
                <form role="form" method="post" action="<%= JSPHandler.generateUrl(request, "user", "update-nama", "") %>">
                    <input type="hidden" name="<%= DbPengguna.COL_PENGGUNA_ID %>" id="<%= DbPengguna.COL_PENGGUNA_ID %>" value="<%= systemUser.getPenggunaId()%>" >
                    <div class="row">
                        <div class="col-lg-12">
                            <div class="form-group">
                                <label for="<%= DbPengguna.COL_NAMA %>" >Nama</label>
                                <input type="text" class="form-control" name="<%= DbPengguna.COL_NAMA %>" value="<%= systemUser.getNama() %>" />
                            </div>
                            <div class="form-group">
                                <label for="<%= DbPengguna.COL_USERNAME %>" >Username</label>
                                <input disabled="true" type="text" class="form-control" name="<%= DbPengguna.COL_USERNAME %>" value="<%= systemUser.getUsername() %>" />
                            </div>
                            <div class="form-group">
                                <label for="<%= DbPengguna.COL_PASSWORD %>">Password</label>
                                <button type="button" id="btnOpenModalUser" class="btn btn-info form-control"> Ubah Password &nbsp; <i class="fa fa-pencil"></i> </button>
                            </div>
                            <div class="form-group pull-right">
                                <button class="btn btn-primary" type="submit" ><span class="fa fa-save"></span> Simpan Data</button>
                                <button class="btn btn-warning" onclick="back()" type="button"><span class="fa fa-chevron-circle-left"></span> Kembali</button>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div><!-- /.row -->


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
    $("#btnOpenModalUser").click(function(){
        $("#modalUserPassword").modal({backdrop: 'static', keyboard: false});
        $("#<%= DbPengguna.COL_PASSWORD %>").val("");
        $("#ulang_<%= DbPengguna.COL_PASSWORD %>").val("");
    });
    
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
                data  : { system_user_id:userId, password:newPassword},
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
    
    function back(){
        location.href = "<%= JSPHandler.generateUrl(request, "home", "", "") %>";
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