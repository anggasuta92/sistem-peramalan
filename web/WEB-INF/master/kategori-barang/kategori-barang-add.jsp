<%@ include file="../../layout/top-page.jsp"%>

<div class="row">
    <div class="col-lg-6">
        <div class="panel panel-primary">
            <div class="panel-heading">
                <h3 class="panel-title"><i class="fa fa-archive"></i> Kategori Barang</h3>
            </div>
            <div class="panel-body">
                <form role="form" method="post" action="<%= JSPHandler.generateUrl(request, "kategori-barang", "save", "") %>">
                    <div class="row">
                        <div class="col-lg-12">
                            <div class="form-group">
                                <label for="kode">Kode</label>
                                <input type="text" class="form-control" id="kode" name="kode" placeholder="Masukkan kode kategori" required>
                            </div>
                            <div class="form-group">
                                <label for="nama">Nama</label>
                                <input type="text" class="form-control" id="nama" name="nama" placeholder="Masukkan kode kategori" required>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12 pull-left" style="padding-top: 20px; text-align: right;">
                            <button class="btn btn-primary" onclick="saveData()" ><span class="fa fa-save"></span> Simpan Data</button>
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