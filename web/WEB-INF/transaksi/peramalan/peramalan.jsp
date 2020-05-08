<%@page import="com.peramalan.model.transaksi.DbPenjualan"%>
<%@page import="com.peramalan.services.TransaksiService"%>
<%@page import="com.peramalan.services.PaginationServices"%>
<%@page import="com.peramalan.config.MainConfig"%>
<%@page import="com.peramalan.services.JSPHandler"%>
<%@ include file="../../layout/top-page.jsp"%>

<div class="row">
    <div class="col-lg-6">
        <div class="panel panel-primary">
            <div class="panel-heading">
                <h3 class="panel-title"><i class="fa fa-archive"></i> Perhitungan peramalan</h3>
            </div>
            <div class="panel-body">
                <div class="row">
                    <div class="col-lg-12">
                        <div class="form-group">
                            <label>Tahun</label>
                            <select class="form-control" id="tahun" name="tahun">
                                <option value="0">Pilih tahun..</option>
                                <% for(int tahun = 0; tahun < TransaksiService.periodeTahun().length; tahun++){ %>
                                <option value="<%= TransaksiService.periodeTahun()[tahun] %>"> <%= TransaksiService.periodeTahun()[tahun] %> </option>
                                <% } %>
                            </select>
                        </div>
                        <div class="form-group">
                            <label>Bulan</label>
                            <select class="form-control" id="bulan" name="bulan">
                                <option value="0">Pilih bulan..</option>
                                <% for(int bulan = 1; bulan < TransaksiService.periodeBulan.length; bulan++){ %>
                                <option value="<%= bulan %>"> <%= TransaksiService.periodeBulan[bulan] %> </option>
                                <% } %>
                            </select>
                        </div>
                        <div class="form-group pull-right" id="btn-peramalan">
                            <button class="btn btn-primary" onClick="hitungPeramalan()">Hitung Peramalan</button>
                            <button class="btn btn-warning" onclick="back()">Kembali</button>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-lg-12">
                        <div class="form-group" id="loader-peramalan" style="display:none;text-align: center;">
                            <div class="alert alert-info">
                                <img width="30px" height="30px" src="<%= MainConfig.getAssetUrl(request)%>/images/loader-classic.gif"/>
                                &nbsp;
                                <i>menghitung peramalan...</i>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script>
    function back(){
        location.href = "<%= JSPHandler.generateUrl(request, "home", "", "") %>";
    }
    
    function hitungPeramalan(){
        
        var bulan = document.getElementById('bulan').value;
        var tahun = document.getElementById('tahun').value;
        
        if(bulan!=0 && tahun!=null){
            $('#btn-peramalan').hide();
            $('#loader-peramalan').show();
            startHitungPeramalan();
        }else{
            swal('Perhatian!','Tahun atau bulan belum dipilih', 'warning');
        }
    }
    
    function startHitungPeramalan(){
        
    }
</script>

<%@ include file="../../layout/bottom-page.jsp"%>