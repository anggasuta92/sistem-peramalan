<%@page import="com.peramalan.services.PaginationServices"%>
<%@page import="com.peramalan.config.MainConfig"%>
<%@page import="com.peramalan.services.JSPHandler"%>
<%@ include file="../../layout/top-page.jsp"%>
    
<div class="row">
    <div class="col-lg-12">
        <div class="panel panel-primary">
            <div class="panel-heading">
                <h3 class="panel-title"><i class="fa fa-archive"></i> Pengguna</h3>
            </div>
            <div class="panel-body">
                <div class="row" style="padding-bottom: 10px">
                    <div class="col-md-7 pull-right">
                        <div class="input-group">
                            <input type="text" name="txtSearch" id="txtSearch" class="form-control" placeholder="Pencarian data...">
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
                                    <th>Nama</th>
                                    <th width="250">Username</th>
                                    <th width="250">Role</th>
                                    <th width="100">Status</th>
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
                    <div class="col-md-5 pull-right" style="padding-top: 20px" align="right">
                        <button class="btn btn-primary" onclick="addNew()" ><span class="fa fa-plus-circle"></span> Tambah Data</button>
                        <button class="btn btn-warning" onclick="back()" ><span class="fa fa-chevron-circle-left"></span> Kembali</button>
                    </div>
                    <div class="col-md-5 pull-left" id="nav-button">
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
    
    function addNew(){
        location.href = "<%= JSPHandler.generateUrl(request, "user", "add", "") %>";
    }
    
    function back(){
        location.href = "<%= JSPHandler.generateUrl(request, "home", "", "") %>";
    }
    
    function view(id){
        location.href = "<%= JSPHandler.generateUrl(request, "user", "edit", "") %>&id="+id;
    }
    
    function loadData(cmd, curPage, searchParam){
        $.ajax({
            type  : 'get',
            data  : { command: cmd, currentPage: curPage, param: searchParam},
            url   : '<%= JSPHandler.generateUrl(request, "user", "get-data", "") %>',
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
                        var status = 'Aktif';
                        if(data[i].user.status=='0'){
                            status = 'Tidak Aktif';
                        }
                        html += '<tr>'+
                                    '<td align="center">'+(startNumber+i)+'</td>'+
                                    '<td>'+data[i].user.nama+'</td>'+
                                    '<td>'+data[i].user.username+'</td>'+
                                    '<td>'+data[i].role.nama+'</td>'+
                                    '<td>'+status+'</td>'+
                                    '<td align="center" class="margin">'+
                                        '<button class="btn btn-sm btn-default" onClick="view(\''+data[i].user.penggunaId.toString()+'\')"><span class="fa fa-pencil text-primary"></span></button>&nbsp;'+
//                                        '<button class="btn btn-sm btn-default" onClick="confirmDelete(\''+data[i].user.penggunaId+'\', \'Nama: '+ data[i].user.nama +'\')"><span class="fa fa-trash-o text-danger"></span></button>'+
                                    '</td>'+
                                '</tr>';
                    }
                }else{
                    html += '<tr>'+
                            '<td align="center" colspan="5">Data tidak ditemukan</td>'+
                            '</tr>';                    
                }
                $('#table-body').html(html);
                $("#loader").hide();
                currentPage = paginationInfo.currentPage;
            }
        });
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
    
    function confirmDelete(idUser, note){
        swal({
        title: "Konfirmasi",
        text: "Apakah anda ingin menghapus data user: \n" + note + "?",
        icon: "info",
        buttons: true,
        dangerMode: true,
        })
        .then((deleteSaja) => {
            if (deleteSaja) {
                $.ajax({
                    type  : 'post',
                    data  : { id: idUser},
                    url   : '<%= JSPHandler.generateUrl(request, "user", "delete", "") %>',
                    async : false,
                    dataType : 'json',
                    success : function(datas){
                        var messages = datas.message;
                        var code = datas.code;
                        swal(messages);
                        if(code==0){
                            loadData("", 1, "");
                        }
                    }
                });
            }
        });
    }
</script>

<%@ include file="../../layout/bottom-page.jsp"%>