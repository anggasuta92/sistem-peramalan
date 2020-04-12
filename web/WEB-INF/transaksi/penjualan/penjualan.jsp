<%@page import="com.peramalan.model.transaksi.DbPenjualan"%>
<%@page import="com.peramalan.services.TransaksiService"%>
<%@page import="com.peramalan.services.PaginationServices"%>
<%@page import="com.peramalan.config.MainConfig"%>
<%@page import="com.peramalan.services.JSPHandler"%>
<%@ include file="../../layout/top-page.jsp"%>

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
    <div class="col-lg-12">
        <div class="panel panel-primary">
            <div class="panel-heading">
                <h3 class="panel-title"><i class="fa fa-archive"></i> Barang</h3>
            </div>
            <div class="panel-body">
                <div class="row" style="padding-bottom: 10px">
                    <div class="col-md-7 pull-left">
                        <div class="col-md-1" style="padding: 5px"><label for="">Bulan</label></div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <select name="param_bulan" id="param_bulan" class="form-control">
                                    <option value="0"> pilih bulan... </option>
                                    <% for(int bulan = 1; bulan < TransaksiService.periodeBulan.length; bulan++){ %>
                                    <option value="<%= bulan %>"> <%= TransaksiService.periodeBulan[bulan] %> </option>
                                    <% } %>
                                </select>
                            </div>
                        </div>
                        <div class="col-md-1" style="padding: 5px"><label for="">Tahun</label></div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <select name="param_tahun" id="param_tahun" class="form-control">
                                    <option value="0"> pilih tahun... </option>
                                    <% for(int tahun = 0; tahun < TransaksiService.periodeTahun().length; tahun++){ %>
                                    <option value="<%= TransaksiService.periodeTahun()[tahun] %>"> <%= TransaksiService.periodeTahun()[tahun] %> </option>
                                    <% } %>
                                </select>
                            </div>
                        </div>    
                    </div>
                    <div class="col-md-5 pull-right">
                        <div class="input-group">
                            <input type="text" name="txtSearch" id="txtSearch" class="form-control" placeholder="Cari kode / barcode / nama...">
                            <span class="input-group-btn">
                                <button class="btn btn-primary" type="button" onclick="loadSearch()">Cari data!</button>
                            </span>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <table id="tblx" class="table table-bordered table-striped table-hover">
                            <thead>
                                <tr>
                                    <th width="50">#</th>
                                    <th width="170">Periode</th>
                                    <th width="120">Kode</th>
                                    <th width="150">Barcode</th>
                                    <th>Nama Barang</th>
                                    <th width="150">Satuan</th>
                                    <th width="90">Terjual</th>
                                    <th width="90">Aksi</th>
                                </tr>    
                            </thead>
                            <tbody id="table-body">
                                <!-- table content here -->
                            </tbody>
                        </table>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-5 pull-right" style="padding-top: 20px" align="right">
                        <button class="btn btn-primary"><span class="fa fa-sign-in"></span> Import</button>
                        <button class="btn btn-primary"><span class="fa fa-sign-out"></span> Export</button>
                        <button class="btn btn-primary" id="btnOpenEditor"><span class="fa fa-plus-circle"></span> Editor Penjualan</button>
                        <button class="btn btn-warning" onclick="back()" ><span class="fa fa-chevron-circle-left"></span> Kembali</button>
                    </div>
                    <div class="col-md-5 pull-left" id="nav-button">
                        <!-- navigation button here -->
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- Modal -->
<div class="modal fade" id="modalPenjualan" role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header" style="padding:5px;">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                Editor Penjualan
            </div>
            <div class="modal-body" style="padding:10px">
                <div class="row">
                    <div class="col-sm-6">
                        <div class="form-group">
                            <label for="bulan">Bulan</label>
                            <select name="param_bulan_editor" id="param_bulan" class="form-control">
                                <option value="0"> pilih bulan... </option>
                                <% for(int bulan = 1; bulan < TransaksiService.periodeBulan.length; bulan++){ %>
                                <option value="<%= bulan %>"> <%= TransaksiService.periodeBulan[bulan] %> </option>
                                <% } %>
                            </select>
                        </div>
                    </div>
                    <div class="col-sm-6">
                        <div class="form-group">
                            <label for="bulan">Tahun</label>
                            <select name="param_tahun_editor" id="param_tahun" class="form-control">
                                <option value="0"> pilih tahun... </option>
                                <% for(int tahun = 0; tahun < TransaksiService.periodeTahun().length; tahun++){ %>
                                <option value="<%= TransaksiService.periodeTahun()[tahun] %>"> <%= TransaksiService.periodeTahun()[tahun] %> </option>
                                <% } %>
                            </select>
                        </div>
                    </div>
                </div>
                    <div class="row">
                        <div class="col-sm-12">
                            <div class="form-group ui-widget">
                                <label for="tags"><span class="fa fa-barcode"></span> Pencarian Barang</label>
                                <input type="text" class="form-control" id="itemSearch" placeholder="Masukkan kode / barcode / nama barang...">
                            </div>
                            <div class="form-group">
                                <label for=""><span class="fa fa-barcode"></span> Code</label>
                                <input type="text" class="form-control" id="" placeholder="" disabled>
                            </div>
                            <div class="form-group">
                                <label for=""><span class=""></span> Nama Barang</label>
                                <input type="text" class="form-control" id="" placeholder="" disabled>
                            </div>
                            <div class="form-group">
                                <label for=""><span class=""></span> QTY</label>
                                <input type="text" class="form-control" id="" placeholder="" disabled>
                            </div>
                        </div>
                    </div>
            </div>
            <div class="modal-footer">
                <button type="submit" class="btn btn-danger btn-default pull-left" data-dismiss="modal"><span class="fa fa-chevron-circle-left"></span> Keluar</button>
            </div>
        </div>
    </div>
</div> 
                            
<script>
  $( function() {
    $( "#itemSearch" ).autocomplete({
        source: function (request, response) {
            $.ajax({
                url: "<%= JSPHandler.generateUrl(request, "barang", "get-data", "") %>",
                data: { param: request.term },
                dataType: "json",
                contentType: "application/json; charset=utf-8",
                //success: response,
                success: function(data) {
                    var datas = data.data;
                    if(datas.length<=0){
                        datas = [{"kode":"Data not found","nama":"Data not found", "id":"0"}];
                    }else if(datas.length==1){
                        /* langsung munculkan di bawahnya */
                    }
                    response(datas);
                },
                error: function () {
                    var obj = [{"kode":"Data not found","nama":"Data not found", "id":"0"}];
                    response(obj);
                }
            });
        }
    }).autocomplete('instance')._renderItem = function( ul, item ) {
        return $('<li>')
            .append('<div class="row" style="padding:10px; border:1px;"><div class="col-md-3">'+ item.kode +'</div><div class="col-md-9">'+ item.nama +'</div></div>')
            .appendTo(ul);
    };;
  } );
  </script>
                            
<script>    
    function back(){
        location.href = "<%= JSPHandler.generateUrl(request, "home", "", "") %>";
    }
      
    $("#btnOpenEditor").click(function(){
        $("#modalPenjualan").modal({backdrop: 'static', keyboard: false});
        $( "#itemSearch" ).autocomplete( "option", "appendTo", ".eventInsForm" );
    });

    $("#txtSearch").on('keyup', function (e) {
        if (e.keyCode === 13) {
            loadSearch();
        }
    });
    
    function loadSearch(){
        var param = document.getElementById('txtSearch').value;
        var bulan = document.getElementById('param_bulan').value;
        var tahun = document.getElementById('param_tahun').value;
        
        if(bulan==0 || tahun==0){
            swal("Perhatian","Periode Bulan atau Tahun belum dipilih","warning");
        }else{
            loadData(0,0,param,bulan,tahun);
        }        
    }
    
    /* spagination */
    function loadNext(){
        var param = document.getElementById('txtSearch').value;
        var bulan = document.getElementById('param_bulan').value;
        var tahun = document.getElementById('param_tahun').value;
        loadData(<%= PaginationServices.NEXT %>, currentPage, param, bulan, tahun);
    }
    
    function loadLast(){
        var param = document.getElementById('txtSearch').value;
        var bulan = document.getElementById('param_bulan').value;
        var tahun = document.getElementById('param_tahun').value;
        loadData(<%= PaginationServices.LAST %>, currentPage, param, bulan, tahun);
    }
    
    function loadPrev(){
        var param = document.getElementById('txtSearch').value;
        var bulan = document.getElementById('param_bulan').value;
        var tahun = document.getElementById('param_tahun').value;
        loadData(<%= PaginationServices.PREV %>, currentPage, param, bulan, tahun);
    }
    
    function loadFirst(){
        var param = document.getElementById('txtSearch').value;
        var bulan = document.getElementById('param_bulan').value;
        var tahun = document.getElementById('param_tahun').value;
        loadData(<%= PaginationServices.FIRST %>, currentPage, param, bulan, tahun);
    }
    
    function genPeriodeName(tahun, bulan){
        var strBulans = ["","Januari","Februari","Maret","April","Mei","Juni","Juli","Agustus","September","Oktober","Nopember","Desember"];
        return strBulans[bulan] + " " + tahun;
    }
    
    function loadData(cmd, curPage, searchParam, paramBulan, paramTahun){
        $.ajax({
            type  : 'get',
            data  : { command: cmd, currentPage: curPage, param: searchParam, bulan: paramBulan, tahun: paramTahun},
            url   : '<%= JSPHandler.generateUrl(request, "penjualan", "get-data", "") %>',
            async : true,
            dataType : 'json',
            beforeSend: function() {
              $("#loader").show();
            },
            success : function(datas){
                var paginationInfo = datas.pagination;
                var navButton = ''+
                        '<nav>'+
                        '    <ul class="pager" style="text-align:left;">'+
                        '        <li><button class="btn btn-default btn-flat" onClick="loadFirst()"><span class="fa fa-fast-backward"></span></button></li>'+
                        '        <li><button class="btn btn-default" onClick="loadPrev()"><span class="fa fa-backward"></span></button></li>'+
                        '        <li>Halaman '+ paginationInfo.currentPage +' dari '+ paginationInfo.totalPage +'</li>'+
                        '        <li><button class="btn btn-default" onClick="loadNext()"><span class="fa fa-forward"></button></li>'+
                        '        <li><button class="btn btn-default" onClick="loadLast()"><span class="fa fa-fast-forward"></button></li>'+
                        '    </ul>'+
                        '</nav>';
                if(paginationInfo.totalPage>1){
                    $('#nav-button').show();
                    $('#nav-button').html(navButton);
                }else{
                    $('#nav-button').hide();
                }
                
                var html = '';
                var i;
                var data = datas.data;
                var startNumber = ((paginationInfo.currentPage - 1) * paginationInfo.recordToGet)+1;
                if(data.length>0){
                    for(i=0; i<data.length; i++){
                        html += '<tr>'+
                                    '<td align="center">'+(startNumber+i)+'</td>'+
                                    '<td>'+ genPeriodeName(data[i].tahun, data[i].bulan) +'</td>'+
                                    '<td align="center">'+data[i].barang.kode+'</td>'+
                                    '<td align="center">'+data[i].barang.barcode+'</td>'+
                                    '<td>'+data[i].barang.nama+'</td>'+
                                    '<td>'+data[i].barang.satuan+'</td>'+
                                    '<td align="right">'+ numeral(data[i].qty).format('0,00.00') +'&nbsp;</td>'+
                                    '<td align="center" class="margin">'+
                                        '<button class="btn btn-sm btn-default" onClick="view(\''+data[i].penjualanId+'\')"><span class="fa fa-pencil text-primary"></span></button>&nbsp;'+
                                    '</td>'+
                                '</tr>';
                    }
                }else{
                    html += '<tr>'+
                            '<td align="center" colspan="8">Data tidak ditemukan</td>'+
                            '</tr>';                    
                }
                $('#table-body').html(html);
                $("#loader").hide();
                currentPage = paginationInfo.currentPage;
            }
        });
    }
</script>

<%@ include file="../../layout/bottom-page.jsp"%>