<%@page import="com.peramalan.services.PaginationServices"%>
<%@page import="com.peramalan.config.MainConfig"%>
<%@page import="com.peramalan.services.JSPHandler"%>
<%@ include file="../../layout/top-page.jsp"%>
    
<div class="row">
    <div class="col-lg-12">
        <div class="panel panel-success">
            <div class="panel-heading">
                <h3 class="panel-title"><i class="fa fa-bar-chart-o"></i> Kategori Barang</h3>
            </div>
            <div class="panel-body">
                <div class="row" style="padding-bottom: 10px">
                    <div class="col-md-5 pull-right">
                        <div class="input-group">
                            <input type="text" name="txtSearch" id="txtSearch" class="form-control" placeholder="Pencarian data...">
                            <span class="input-group-btn">
                                <button class="btn btn-success" type="button" onclick="loadSearch()">Cari data!</button>
                            </span>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <table id="tbl">
                            <thead>
                                <tr>
                                    <th width="50">#</th>
                                    <th width="170">Kode</th>
                                    <th>Kategori Barang</th>
                                    <th width="130">Aksi</th>
                                </tr>    
                            </thead>
                            <tbody id="table-body">
                                <!-- table content here -->
                            </tbody>
                        </table>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-5 pull-left" style="padding-top: 20px">
                        <button class="btn btn-success">Tambah Data</button>
                    </div>
                    <div class="col-md-5 pull-right" id="nav-button">
                        <!-- navigation button here -->
                    </div>
                </div>
            </div>
        </div>
    </div>
</div><!-- /.row -->

<script>
    var currentPage = 0;
    
    /* init data */
    $( document ).ready(function() {
        loadData(0,0, "");
    });
    
    /* search */
    $("#txtSearch").on('keyup', function (e) {
        if (e.keyCode === 13) {
            loadSearch();
        }
    });
    
    /* search method */
    function loadSearch(){
        var param = document.getElementById('txtSearch').value;
        loadData(0,0,param);
    }
    
    /* spagination */
    function loadNext(){
        var param = document.getElementById('txtSearch').value;
        loadData(<%= PaginationServices.NEXT %>, currentPage, param);
    }
    
    function loadLast(){
        var param = document.getElementById('txtSearch').value;
        loadData(<%= PaginationServices.LAST %>, currentPage, param);
    }
    
    function loadPrev(){
        var param = document.getElementById('txtSearch').value;
        loadData(<%= PaginationServices.PREV %>, currentPage, param);
    }
    
    function loadFirst(){
        var param = document.getElementById('txtSearch').value;
        loadData(<%= PaginationServices.FIRST %>, currentPage, param);
    }
    
    function loadData(cmd, curPage, searchParam){
        $.ajax({
            type  : 'get',
            data  : { command: cmd, currentPage: curPage, param: searchParam},
            url   : '<%= JSPHandler.generateUrl(request, "kategori-barang", "get-data", "") %>',
            async : true,
            dataType : 'json',
            beforeSend: function() {
              $("#loader").show();
            },
            success : function(datas){
                var paginationInfo = datas.pagination;
                var navButton = ''+
                        '<nav>'+
                        '    <ul class="pager">'+
                        '        <li><button class="btn btn-default" onClick="loadFirst()">First</button></li>'+
                        '        <li><button class="btn btn-default" onClick="loadPrev()">Prev</button></li>'+
                        '        <li>Halaman '+ paginationInfo.currentPage +' dari '+ paginationInfo.totalPage +'</li>'+
                        '        <li><button class="btn btn-default" onClick="loadNext()">Next</button></li>'+
                        '        <li><button class="btn btn-default" onClick="loadLast()">Last</button></li>'+
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
                                    '<td align="center">'+data[i].code+'</td>'+
                                    '<td>'+data[i].nama+'</td>'+
                                    '<td align="center">'+
                                        '<div class="btn-group" role="group">'+
                                            '<button class="btn btn-sm btn-warning">ubah</button>'+
                                            '<button class="btn btn-sm btn-danger">hapus</button>'+
                                        '</div>'+
                                    '</td>'+
                                '</tr>';
                    }
                }else{
                    html += '<tr>'+
                            '<td align="center" colspan="4">Data tidak ditemukan</td>'+
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