<%@page import="com.peramalan.model.transaksi.DbPenjualan"%>
<%@page import="com.peramalan.services.TransaksiService"%>
<%@page import="com.peramalan.services.PaginationServices"%>
<%@page import="com.peramalan.config.MainConfig"%>
<%@page import="com.peramalan.services.JSPHandler"%>
<%@ include file="../../layout/top-page.jsp"%>

<div class="row">
    <div class="col-lg-12">
        <div class="panel panel-primary">
            <div class="panel-heading">
                <h3 class="panel-title"><i class="fa fa-archive"></i> Perhitungan Peramalan</h3>
            </div>
            <div class="panel-body">
                <div class="panel panel-default">
                    <div class="panel-body">
                        <div class="row">
                            <div class="col-lg-2" style="text-align: right">
                                Meramalkan penjualan periode ke:
                            </div>
                            <div class="col-lg-2">
                                <div class="form-group">
                                    <select class="form-control" name="peramalan_bulan" id="peramalan_bulan">
                                        <option value="0">Pilih bulan..</option>
                                        <% for(int bulan = 1; bulan < TransaksiService.periodeBulan.length; bulan++){ %>
                                        <option value="<%= bulan %>"> <%= TransaksiService.periodeBulan[bulan] %> </option>
                                        <% } %>
                                    </select>
                                </div>
                            </div>
                            <div class="col-lg-2">
                                <div class="form-group">
                                    <select class="form-control" name="peramalan_tahun" id="peramalan_tahun">
                                        <option value="0">Pilih tahun..</option>
                                        <% for(int tahun = 0; tahun < TransaksiService.periodeTahun().length; tahun++){ %>
                                        <option value="<%= TransaksiService.periodeTahun()[tahun] %>"> <%= TransaksiService.periodeTahun()[tahun] %> </option>
                                        <% } %>
                                    </select>
                                </div>
                            </div>
                            <div class="col-lg-2" style="text-align: right">
                                Berdasarkan periode penjualan dari:
                            </div>
                            <div class="col-lg-2">
                                <div class="form-group">
                                    <select class="form-control" name="penjualan_bulan" id="penjualan_bulan">
                                        <option value="0">Pilih bulan..</option>
                                        <% for(int bulan = 1; bulan < TransaksiService.periodeBulan.length; bulan++){ %>
                                        <option value="<%= bulan %>"> <%= TransaksiService.periodeBulan[bulan] %> </option>
                                        <% } %>
                                    </select>
                                </div>
                            </div>
                            <div class="col-lg-2">
                                <div class="form-group">
                                    <select class="form-control" name="penjualan_tahun" id="penjualan_tahun">
                                        <option value="0">Pilih tahun..</option>
                                        <% for(int tahun = 0; tahun < TransaksiService.periodeTahun().length; tahun++){ %>
                                        <option value="<%= TransaksiService.periodeTahun()[tahun] %>"> <%= TransaksiService.periodeTahun()[tahun] %> </option>
                                        <% } %>
                                    </select>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                        
                <div class="row">
                    <div class="col-lg-12">
                        <div class="form-group pull-right" id="btn-peramalan">
                            <button class="btn btn-primary" onClick="hitungPeramalan()">Hitung Peramalan</button>
                            <button class="btn btn-warning" onclick="back()">Kembali</button>
                        </div>
                        
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
        
        var peramalan_bulan = document.getElementById('peramalan_bulan').value;
        var peramalan_tahun = document.getElementById('peramalan_tahun').value;
        var penjualan_bulan = document.getElementById('penjualan_bulan').value;
        var penjualan_tahun = document.getElementById('penjualan_tahun').value;
        
        if(peramalan_bulan!=0 && peramalan_tahun!=null && penjualan_bulan!=null && penjualan_tahun!=null){
            startHitungPeramalan(peramalan_bulan, peramalan_tahun, penjualan_bulan, penjualan_tahun);
        }else{
            swal('Perhatian!','periode peramalan atau penjualan belum dipilih', 'warning');
        }
    }
    
    function startHitungPeramalan(peramalanBulan, peramalanTahun, penjualanBulan, penjualanTahun){
        $.ajax({
            type  : 'post',
            data  : { peramalan_bulan:peramalanBulan, peramalan_tahun:peramalanTahun, penjualan_bulan:penjualanBulan, penjualan_tahun:penjualanTahun },
            url   : '<%= JSPHandler.generateUrl(request, "peramalan", "hitung-peramalan", "") %>',
            async : true,
            dataType : 'json',
            beforeSend: function() {
                $('#btn-peramalan').hide();
                $('#loader-peramalan').show();
            },
            success : function(datas){
                // redirect here
                $('#btn-peramalan').show();
                $('#loader-peramalan').hide();
            }
        });
    }
</script>

<%@ include file="../../layout/bottom-page.jsp"%>