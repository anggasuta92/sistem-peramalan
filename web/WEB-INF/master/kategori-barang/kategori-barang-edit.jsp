<%@page import="com.peramalan.model.master.DbKategoriBarang"%>
<%@page import="com.peramalan.model.master.KategoriBarang"%>
<%@ include file="../../layout/top-page.jsp"%>

<%
    KategoriBarang data = new KategoriBarang();
    try {
        data = (KategoriBarang) request.getAttribute("data");
    } catch (Exception e) {
        e.printStackTrace();
    }
%>

<div class="row">
    <div class="col-lg-6">
        <div class="panel panel-primary">
            <div class="panel-heading">
                <h3 class="panel-title"><i class="fa fa-archive"></i> Kategori Barang</h3>
            </div>
            <div class="panel-body">
                <form role="form" method="post" action="<%= JSPHandler.generateUrl(request, "kategori-barang", "update", "") %>">
                    <input type="hidden" name="<%= DbKategoriBarang.COL_KATEGORI_BARANG_ID %>" value="<%= data.getKategoriBarangId() %>">
                    <div class="row">
                        <div class="col-lg-12">
                            <div class="form-group">
                                <label for="kode">Kode</label>
                                <input type="text" class="form-control" id="kode" name="kode" placeholder="Masukkan kode kategori" value="<%= data.getKode()%>" required>
                            </div>
                            <div class="form-group">
                                <label for="nama">Nama</label>
                                <input type="text" class="form-control" id="nama" name="nama" placeholder="Masukkan nama kategori" value="<%= data.getNama()%>" required>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12 pull-left" style="padding-top: 20px; text-align: right;">
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
        location.href = "<%= JSPHandler.generateUrl(request, "kategori-barang", "", "") %>";
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