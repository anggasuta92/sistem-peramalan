<%@page import="com.peramalan.config.MainConfig"%>
<%@page import="com.peramalan.services.JSPHandler"%>
<%@ include file="../../layout/top-page.jsp"%>
    
<div class="row">
    <div class="col-lg-12">
        <div class="panel panel-default">
            <div class="panel-heading">
                <h3 class="panel-title"><i class="fa fa-bar-chart-o"></i> Kategori Barang</h3>
            </div>
            <div class="panel-body">
                <div class="row" style="padding-bottom: 10px">
                    <div class="col-md-5 pull-right">
                        <div class="input-group">
                            <input type="text" class="form-control" placeholder="Pencarian data...">
                            <span class="input-group-btn">
                              <button class="btn btn-primary" type="button">Cari!</button>
                            </span>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12" id="tabel"></div>
                </div>
                <div class="row">
                    <div class="col-md-5 pull-left" style="padding-top: 20px">
                        <button class="btn btn-primary">Tambah Data</button>
                    </div>
                    <div class="col-md-5 pull-right">
                        <nav>
                            <ul class="pager">
                                <li><a href="#">First</a></li>
                                <li><a href="#">Previous</a></li>
                                <li>page <%= (String)request.getAttribute("current") %> of <%= (String)request.getAttribute("total") %></li>
                                <li><a href="#">Next</a></li>
                                <li><a href="#">Last</a></li>
                            </ul>
                        </nav>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div><!-- /.row -->

<script>
    $( document ).ready(function() {
        $('#tabel').load("<%= JSPHandler.generateUrl(request, "kategori-barang", "get-data", "") %>");
    });
</script>

<%@ include file="../../layout/bottom-page.jsp"%>