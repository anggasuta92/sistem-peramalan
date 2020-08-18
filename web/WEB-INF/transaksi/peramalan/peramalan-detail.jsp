<%@page import="com.peramalan.services.PeramalanServices"%>
<%@page import="com.peramalan.model.transaksi.Peramalan"%>
<%@page import="com.peramalan.model.transaksi.DbPenjualan"%>
<%@page import="com.peramalan.services.TransaksiService"%>
<%@page import="com.peramalan.services.PaginationServices"%>
<%@page import="com.peramalan.config.MainConfig"%>
<%@page import="com.peramalan.services.JSPHandler"%>
<%@ include file="../../layout/top-page.jsp"%>

<%
    Peramalan peramalan = new Peramalan();
    try {
        peramalan = (Peramalan) request.getAttribute("peramalan");
    } catch (Exception e) {
    }
    
%>

<div class="row">
    <div class="col-lg-12">
        <div class="panel panel-primary">
            <div class="panel-heading">
                <h3 class="panel-title"><i class="fa fa-archive"></i> Detail Peramalan</h3>
            </div>
            <div class="panel-body">
                <div class="row">
                    <div class="col-sm-1">Nomor</div>
                    <div class="col-sm-3">: <%= peramalan.getNomor() %></div>
                    <div class="col-sm-1"></div>
                    <div class="col-sm-2">Periode Peramlan</div>
                    <div class="col-sm-3">: <%= TransaksiService.periodeBulan[peramalan.getPeramalanBulan()] + " " + peramalan.getPeramalanTahun() %></div>
                    <div class="col-sm-1"></div>
                    <div class="col-sm-1" style="text-align: center"></div>
                </div>
                <div class="row">
                    <div class="col-sm-1">Tanggal</div>
                    <div class="col-sm-3">: <%= peramalan.getTanggal() %></div>
                    <div class="col-sm-1"></div>
                    <div class="col-sm-2">Periode Penjualan</div>
                    <div class="col-sm-3">: <%= TransaksiService.periodeBulan[peramalan.getPenjualanBulan()] + " " + peramalan.getPenjualanTahun() %></div>
                    <div class="col-sm-1"></div>
                    <div class="col-sm-1" style="text-align: center; font-size: 14pt"><strong></strong></div>
                </div>
                <hr/>
                <div class="row" style="padding-bottom: 10px">
                    <div class="col-md-8 pull-left">
                        <div class="col-md-1" style="padding: 5px"><label for=""><small>Bulan</small></label></div>
                        <div class="col-md-2">
                            <div class="form-group">
                                <select name="param_bulan" id="param_bulan" class="form-control">
                                    <option value="0">semua</option>
                                    <% for(int bulan = 1; bulan < TransaksiService.periodeBulan.length; bulan++){ %>
                                    <option value="<%= bulan %>"> <%= TransaksiService.periodeBulan[bulan] %> </option>
                                    <% } %>
                                </select>
                            </div>
                        </div>
                        <div class="col-md-1" style="padding: 5px"><label for=""><small>Tahun</small></label></div>
                        <div class="col-md-2">
                            <div class="form-group">
                                <select name="param_tahun" id="param_tahun" class="form-control">
                                    <option value="0">semua</option>
                                    <% for(int tahun = 0; tahun < TransaksiService.periodeTahun().length; tahun++){ %>
                                    <option value="<%= TransaksiService.periodeTahun()[tahun] %>"> <%= TransaksiService.periodeTahun()[tahun] %> </option>
                                    <% } %>
                                </select>
                            </div>
                        </div>
                        <div class="col-md-1" style="padding: 5px"><label for=""><small>Alpha</small></label></div>
                        <div class="col-md-2">
                            <div class="form-group">
                                <select name="param_alpha" id="param_alpha" class="form-control">
                                    <option value="0">semua</option>
                                    <% for(int x = 1; x < 10; x++){ %>
                                    <% double alpha = x/10.0; %>
                                    <option value="<%= alpha %>"> <%= alpha %> </option>
                                    <% } %>
                                </select>
                            </div>
                        </div>
                        <div class="col-md-2" style="padding: 5px"><label for=""><small>Disarankan</small></label></div>
                        <div class="col-md-1" style="padding: 5px">
                            <input type="checkbox" name="disarankan" id="disarankan" value="1" />
                        </div>
                    </div>
                    <div class="col-md-4 pull-right">
                        <div class="input-group">
                            <input type="text" name="txtSearch" id="txtSearch" class="form-control" placeholder="Cari Code / Barcode / Nama...">
                            <span class="input-group-btn">
                                <button class="btn btn-primary" type="button" onclick="loadSearch()">Cari data!</button>
                            </span>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-lg-12">
                        <table id="tblx" class="table table-bordered table-striped table-hover">
                            <thead>
                                <tr>
                                    <th width="50">#</th>
                                    <th width="150">Periode</th>
                                    <th width="300">Barang</th>
                                    <th width="30">Alpha</th>
                                    <th width="90">Peramalan</th>
                                    <th width="90">Keterangan</th>
                                    <th width="30">Analisa</th>
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
                        <button class="btn btn-success" id="btn-pdf" onclick="printPDF('<%= peramalan.getPeramalanId() %>')" ><span class="fa fa-print"></span> Cetak Laporan</button>
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
    function printPDF(id){  
       var alpha = (document.getElementById('param_alpha')!==null ? document.getElementById('param_alpha').value : 0);
       var disarankan = (document.getElementById('disarankan').checked ? 1:0);
       var bulan = document.getElementById('param_bulan').value;
       var tahun = document.getElementById('param_tahun').value;
       window.open("<%= JSPHandler.generateUrl(request, "peramalan", "export-detail-pdf", "") %>&id="+id+"&bl="+bulan+"&th="+tahun+"&saran="+disarankan+"&alpha="+alpha,"_blank");
    } 
    
    function genPeriodeName(tahun, bulan){
        var strBulans = ["","Januari","Februari","Maret","April","Mei","Juni","Juli","Agustus","September","Oktober","Nopember","Desember"];
        return strBulans[bulan] + " " + tahun;
    }
    
    function back(){
        location.href = "<%= JSPHandler.generateUrl(request, "peramalan", "arsip", "") %>";
    }
    
    function loadSearch(){
        var param = document.getElementById('txtSearch').value;
        var bulan = document.getElementById('param_bulan').value;
        var tahun = document.getElementById('param_tahun').value;
        var alpha = (document.getElementById('param_alpha')!==null ? document.getElementById('param_alpha').value : 0);
        var disarankan = (document.getElementById('disarankan').checked == true ? 1 : 0);
        
        var id = '<%= peramalan.getPeramalanId() %>';
        
        loadData(0,0,param,bulan,tahun, id, alpha, disarankan);
    }
    
    function loadData(cmd, curPage, searchParam, paramBulan, paramTahun, idx, alphax, disarankan){
        $.ajax({
            type  : 'get',
            data  : { disarankan:disarankan, alpha:alphax, id:idx, command: cmd, currentPage: curPage, param: searchParam, bulan: paramBulan, tahun: paramTahun},
            url   : '<%= JSPHandler.generateUrl(request, "peramalan", "detail-get", "") %>',
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
                    $("#btn-pdf").attr('disabled', false);
                    for(i=0; i<data.length; i++){
                        html += '<tr>'+
                                    '<td align="center">'+(startNumber+i)+'</td>'+
                                    '<td align="center">'+ genPeriodeName(data[i].tahun, data[i].bulan) +'</td>'+
                                    '<td align="left"> '+ data[i].barang.kode +'/' + data[i].barang.nama + '</td>'+
                                    '<td align="right">'+ data[i].alpha + '</td>'+
                                    '<td align="right">'+ data[i].peramalan + '</td>'+
                                    '<td align="left">'+ (data[i].disarankan==1 ? " <strong><i> *Disarankan</i></strong>" : "") + '</td>'+
                                    '<td align="center" class="margin">'+
                                        '<button class="btn btn-sm btn-default" onClick="analisa(\''+ data[i].peramalanId +'\', \''+ data[i].tahun +'\', \''+ data[i].bulan +'\', \''+ data[i].alpha +'\', \''+ data[i].barang.barangId +'\')"><span class="fa fa-eye text-primary"></span></button>&nbsp;'+
                                    '</td>'+
                                '</tr>';
                    }
                }else{
                    html += '<tr>'+
                            '<td align="center" colspan="6">Data tidak ditemukan</td>'+
                            '</tr>';
                    $("#btn-pdf").attr('disabled', true);
                }
                $('#table-body').html(html);
                $("#loader").hide();
                currentPage = paginationInfo.currentPage;
            }
        });
    }
    
    $(document).ready(function(){
        loadSearch();
    });
    
    /* spagination */
    function loadNext(){
        var param = document.getElementById('txtSearch').value;
        var bulan = document.getElementById('param_bulan').value;
        var tahun = document.getElementById('param_tahun').value;
        var alpha = (document.getElementById('param_alpha')!==null ? document.getElementById('param_alpha').value : 0);
        var id = '<%= peramalan.getPeramalanId() %>';
        var disarankan = (document.getElementById('disarankan').checked == true ? 1 : 0);
        loadData(<%= PaginationServices.NEXT %>,currentPage,param,bulan,tahun, id, alpha, disarankan);
    }
    
    function loadLast(){
        var param = document.getElementById('txtSearch').value;
        var bulan = document.getElementById('param_bulan').value;
        var tahun = document.getElementById('param_tahun').value;
        var id = '<%= peramalan.getPeramalanId() %>';
        var alpha = (document.getElementById('param_alpha')!==null ? document.getElementById('param_alpha').value : 0);
        var disarankan = (document.getElementById('disarankan').checked == true ? 1 : 0);
        loadData(<%= PaginationServices.LAST %>,currentPage,param,bulan,tahun, id, alpha, disarankan);
    }
    
    function loadPrev(){
        var param = document.getElementById('txtSearch').value;
        var bulan = document.getElementById('param_bulan').value;
        var tahun = document.getElementById('param_tahun').value;
        var id = '<%= peramalan.getPeramalanId() %>';
        var alpha = (document.getElementById('param_alpha')!==null ? document.getElementById('param_alpha').value : 0);
        var disarankan = (document.getElementById('disarankan').checked == true ? 1 : 0);
        loadData(<%= PaginationServices.PREV %>,currentPage,param,bulan,tahun, id, alpha, disarankan);
    }
    
    function loadFirst(){
        var param = document.getElementById('txtSearch').value;
        var bulan = document.getElementById('param_bulan').value;
        var tahun = document.getElementById('param_tahun').value;
        var id = '<%= peramalan.getPeramalanId() %>';
        var alpha = (document.getElementById('param_alpha')!==null ? document.getElementById('param_alpha').value : 0);
        var disarankan = (document.getElementById('disarankan').checked == true ? 1 : 0);
        loadData(<%= PaginationServices.FIRST %>,currentPage,param,bulan,tahun, id, alpha, disarankan);
    }
    
    function analisa(id, tahun, bulan, alpha, barangId){
        var query = "&id="+id+"&tahun="+tahun+"&bulan="+bulan+"&alpha="+alpha+"&barangId="+barangId;
        location.href = "<%= JSPHandler.generateUrl(request, "peramalan", "analisa", "") %>" + query;
    }
</script>

<%@ include file="../../layout/bottom-page.jsp"%>