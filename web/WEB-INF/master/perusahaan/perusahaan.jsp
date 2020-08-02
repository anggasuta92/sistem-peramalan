<%@page import="com.peramalan.model.master.DbPerusahaan"%>
<%@page import="com.peramalan.model.master.Perusahaan"%>
<%@ include file="../../layout/top-page.jsp"%>
<%
    Perusahaan perusahaan = new Perusahaan();
    try {
        perusahaan = (Perusahaan) request.getAttribute("perusahaan");
    } catch (Exception e) {
    }
%>
<div class="row">
    <div class="col-lg-7">
        <div class="panel panel-primary">
            <div class="panel-heading">
                <h3 class="panel-title"><i class="fa fa-archive"></i> Informasi Perusahaan</h3>
            </div>
            <div class="panel-body">
                <form name="roleAddFrm" action="<%= JSPHandler.generateUrl(request, "perusahaan", "update", "") %>" method="post">
                    <input type="hidden" name="<%= DbPerusahaan.COL_PERUSAHAAN_ID %>" value="<%= perusahaan.getPerusahaanId() %>" />
                    <div class="row">
                        <div class="col-md-12">
                            <div class="form-group">
                                <label for="<%= DbPerusahaan.COL_NAMA %>">Nama Perusahaan</label>
                                <input type="text" name="<%= DbPerusahaan.COL_NAMA %>" value="<%= perusahaan.getNama()%>" class="form-control" />
                            </div>
                        </div>
                        <div class="col-md-12">
                            <div class="form-group">
                                <label for="<%= DbPerusahaan.COL_ALAMAT %>">Alamat</label>
                                <input type="text" name="<%= DbPerusahaan.COL_ALAMAT %>" value="<%= perusahaan.getAlamat() %>" class="form-control" />
                            </div>
                        </div>
                        <div class="col-md-12">
                            <div class="form-group">
                                <label for="<%= DbPerusahaan.COL_TELEPON %>">Telepon</label>
                                <input type="text" name="<%= DbPerusahaan.COL_TELEPON %>" value="<%= perusahaan.getTelepon() %>" class="form-control" />
                            </div>
                        </div>
                        <div class="col-md-12">
                            <div class="form-group">
                                <label for="<%= DbPerusahaan.COL_NAMA_PEMILIK %>">Nama Pemilik</label>
                                <input type="text" name="<%= DbPerusahaan.COL_NAMA_PEMILIK %>" value="<%= perusahaan.getNamaPemilik()%>" class="form-control" />
                            </div>
                        </div>
                        <div class="col-md-12">
                            <div class="col-md-12 pull-left" style="text-align: right;">
                                <button class="btn btn-primary" type="submit" ><span class="fa fa-save"></span> Simpan Data</button>
                            <button class="btn btn-warning" type="button" onclick="back()" ><span class="fa fa-chevron-circle-left"></span> Kembali</button>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<script>
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