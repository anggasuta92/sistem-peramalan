<%@page import="java.util.Vector"%>
<%@page import="com.peramalan.model.master.KategoriBarang"%>
<%@page import="com.peramalan.model.master.DbBarang"%>
<%@page import="com.peramalan.model.master.DbKategoriBarang"%>
<%@ include file="../../layout/top-page.jsp"%>

<%
    Vector<KategoriBarang> kategories = new Vector<KategoriBarang>();
    try{
        kategories = DbKategoriBarang.list("", DbKategoriBarang.COL_NAMA, 0, 0);
    }catch(Exception e){}
%>

<div class="row">
    <div class="col-lg-6">
        <div class="panel panel-primary">
            <div class="panel-heading">
                <h3 class="panel-title"><i class="fa fa-archive"></i> Kategori Barang</h3>
            </div>
            <div class="panel-body">
                <form role="form" method="post" action="<%= JSPHandler.generateUrl(request, "barang", "save", "") %>">
                    <div class="row">
                        <div class="col-lg-12">
                            <div class="form-group">
                                <label for="<%= DbBarang.COL_KATEGORI_BARANG_ID %>">Kategori</label>
                                <select class="form-control" name="<%= DbBarang.COL_KATEGORI_BARANG_ID %>">
                                    <% for(KategoriBarang kategori : kategories){ %>
                                    <option value="<%= kategori.getKategoriBarangId() %>"> <%= kategori.getNama() %> </option>
                                    <% } %>
                                </select>
                            </div>
                            <div class="form-group">
                                <label for="<%= DbBarang.COL_KODE %>">Kode</label>
                                <input type="text" class="form-control" id="kode" name="<%= DbBarang.COL_KODE %>" placeholder="Masukkan kode" required>
                            </div>
                            <div class="form-group">
                                <label for="<%= DbBarang.COL_BARCODE %>">Barcode</label>
                                <input type="text" class="form-control" id="kode" name="<%= DbBarang.COL_BARCODE %>" placeholder="Masukkan barcode" required>
                            </div>
                            <div class="form-group">
                                <label for="<%= DbBarang.COL_NAMA %>">Nama</label>
                                <input type="text" class="form-control" id="nama" name="<%= DbBarang.COL_NAMA %>" placeholder="Masukkan nama barang" required>
                            </div>
                            <div class="form-group">
                                <label for="<%= DbBarang.COL_SATUAN %>">Satuan</label>
                                <input type="text" class="form-control" id="nama" name="<%= DbBarang.COL_SATUAN %>" placeholder="Masukkan Satuan" required>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12 pull-left" style="padding-top: 20px; text-align: right;">
                            <button class="btn btn-primary" type="submit" ><span class="fa fa-save"></span> Simpan Data</button>
                            <button class="btn btn-warning" onclick="back()" ><span class="fa fa-chevron-circle-left"></span> Kembali</button>
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
</script>
                
<%@ include file="../../layout/bottom-page.jsp"%>