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
                <h3 class="panel-title"><i class="fa fa-archive"></i> Arsip Peramalan</h3>
            </div>
            <div class="panel-body">
                <div class="row" style="padding-bottom: 10px">
                    <div class="col-md-7 pull-left">
                        <div class="col-md-1" style="padding: 5px"><label for="">Bulan</label></div>
                        <div class="col-md-3">
                            <div class="form-group">
                                <select name="param_bulan" id="param_bulan" class="form-control">
                                    <option value="0"> semua</option>
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
                                    <option value="0"> semua</option>
                                    <% for(int tahun = 0; tahun < TransaksiService.periodeTahun().length; tahun++){ %>
                                    <option value="<%= TransaksiService.periodeTahun()[tahun] %>"> <%= TransaksiService.periodeTahun()[tahun] %> </option>
                                    <% } %>
                                </select>
                            </div>
                        </div>    
                    </div>
                    <div class="col-md-5 pull-right">
                        <div class="input-group">
                            <input type="text" name="txtSearch" id="txtSearch" class="form-control" placeholder="Nomor peramalan">
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
                                    <th width="120">Nomor Peramalan</th>
                                    <th width="150">Periode Peramalan</th>
                                    <th width="150">Periode Penjualan</th>
                                    <th width="90">Detail</th>
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

<script>
    function back(){
        location.href = "<%= JSPHandler.generateUrl(request, "home", "", "") %>";
    }
    
    function genPeriodeName(tahun, bulan){
        var strBulans = ["","Januari","Februari","Maret","April","Mei","Juni","Juli","Agustus","September","Oktober","Nopember","Desember"];
        return strBulans[bulan] + " " + tahun;
    }
    
    function loadSearch(){
        var param = document.getElementById('txtSearch').value;
        var bulan = document.getElementById('param_bulan').value;
        var tahun = document.getElementById('param_tahun').value;
        
        loadData(0,0,param,bulan,tahun);
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
    
    function loadData(cmd, curPage, searchParam, paramBulan, paramTahun){
        $.ajax({
            type  : 'get',
            data  : { command: cmd, currentPage: curPage, param: searchParam, bulan: paramBulan, tahun: paramTahun},
            url   : '<%= JSPHandler.generateUrl(request, "peramalan", "arsip-get", "") %>',
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
                                    '<td align="center">'+ data[i].nomor +'</td>'+
                                    '<td align="center">'+ genPeriodeName(data[i].peramalanTahun, data[i].peramalanBulan) +'</td>'+
                                    '<td align="center">'+ genPeriodeName(data[i].penjualanTahun, data[i].penjualanBulan) +'</td>'+
                                    '<td align="center" class="margin">'+
                                        '<button class="btn btn-sm btn-default" onClick="detail(\''+ data[i].peramalanId +'\')"><span class="fa fa-eye text-primary"></span></button>&nbsp;'+
                                    '</td>'+
                                '</tr>';
                    }
                }else{
                    html += '<tr>'+
                            '<td align="center" colspan="6">Data tidak ditemukan</td>'+
                            '</tr>';                    
                }
                $('#table-body').html(html);
                $("#loader").hide();
                currentPage = paginationInfo.currentPage;
            }
        });
    }
    
    function detail(id){
        location.href = "<%= JSPHandler.generateUrl(request, "peramalan", "detail", "") %>&id="+ id;
    }
</script>
<%@ include file="../../layout/bottom-page.jsp"%>