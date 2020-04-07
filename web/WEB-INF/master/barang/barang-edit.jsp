<%@page import="com.peramalan.model.master.KategoriBarang"%>
<%@page import="com.peramalan.model.master.DbKategoriBarang"%>
<%@page import="java.util.Vector"%>
<%@page import="com.peramalan.model.master.DbBarang"%>
<%@page import="com.peramalan.model.master.Barang"%>
<%@ include file="../../layout/top-page.jsp"%>

<%
    Barang data = new Barang();
    try {
        data = (Barang) request.getAttribute("data");
    } catch (Exception e) {
        e.printStackTrace();
    }
    
    Vector<KategoriBarang> kategories = new Vector<KategoriBarang>();
    try{
        kategories = DbKategoriBarang.list("", DbKategoriBarang.COL_NAMA, 0, 0);
    }catch(Exception e){}
%>

<div class="row">
    <div class="col-lg-6">
        <div class="panel panel-primary">
            <div class="panel-heading">
                <h3 class="panel-title"><i class="fa fa-archive"></i> Barang</h3>
            </div>
            <div class="panel-body">
                <form role="form" method="post" action="<%= JSPHandler.generateUrl(request, "barang", "update", "") %>">
                    <input type="hidden" name="<%= DbBarang.COL_BARANG_ID %>" value="<%= data.getBarangId()%>">
                    <div class="row">
                        <div class="col-lg-12">
                            <div class="form-group">
                                <label for="<%= DbBarang.COL_KATEGORI_BARANG_ID %>">Kategori</label>
                                <select class="form-control" name="<%= DbBarang.COL_KATEGORI_BARANG_ID %>">
                                    <% for(KategoriBarang kategori : kategories){ %>
                                    <option value="<%= kategori.getKategoriBarangId() %>" <%= data.getKategoriBarang().getKategoriBarangId()==kategori.getKategoriBarangId() ? "selected":"" %> > <%= kategori.getNama() %> </option>
                                    <% } %>
                                </select>
                            </div>
                            <div class="form-group">
                                <label for="<%= DbBarang.COL_KODE %>">Kode</label>
                                <input type="text" class="form-control" id="kode" name="<%= DbBarang.COL_KODE %>" placeholder="Masukkan kode barang" value="<%= data.getKode()%>" required>
                            </div>
                            <div class="form-group">
                                <label for="<%= DbBarang.COL_BARCODE %>">Barcode</label>
                                <input type="text" class="form-control" id="kode" name="<%= DbBarang.COL_BARCODE %>" placeholder="Masukkan barcode" value="<%= data.getBarcode()%>" required>
                            </div>
                            <div class="form-group">
                                <label for="<%= DbBarang.COL_NAMA %>">Nama</label>
                                <input type="text" class="form-control" id="nama" name="<%= DbBarang.COL_NAMA %>" placeholder="Masukkan nama barang" value="<%= data.getNama()%>" required>
                            </div>
                            <div class="form-group">
                                <label for="<%= DbBarang.COL_SATUAN %>">Satuan</label>
                                <input type="text" class="form-control" id="nama" name="<%= DbBarang.COL_SATUAN %>" placeholder="Masukkan satuan barang" value="<%= data.getSatuan()%>" required>
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
        location.href = "<%= JSPHandler.generateUrl(request, "barang", "", "") %>";
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