<%@page import="java.util.Map"%>
<%@page import="java.util.Vector"%>
<%@page import="com.peramalan.services.NumberServices"%>
<%@page import="com.peramalan.config.MainConfig"%>
<%@page import="com.peramalan.services.JSPHandler"%>
<%@ include file="../../layout/top-page.jsp"%>

<%    
    Vector results = new Vector();
    try {
        results = (Vector) session.getAttribute("result");
    } catch (Exception e) {
        e.printStackTrace();
    }
%>

<div class="row">
    
    <% if(results==null){ %>
    <div class="col-md-6">
        <div class="panel panel-primary">
            <div class="panel-heading">
                Import data penjualan
            </div>
            <div class="panel-body">
                <form action="<%= JSPHandler.generateUrl(request, "penjualan", "upload", "") %>" method="post" enctype="multipart/form-data">
                    <div class="form-group">
                        <label class="label label-success">Pilih dokumen penjualan yang akan diimport</label>
                        <hr>
                        <input type="file" class="form-control-file" name="filePenjualan" id="filePenjualan" accept=".xls">
                        <hr>
                    </div>
                    <div class="col-md-12 pull-right" id="nav-btn">
                        <input type="submit" class="btn btn-primary" name="btnSubmit" value="Import sekarang" onClick="hideAndLoading()" />
                        <button name="btnKembali" type="button" class="btn btn-warning" onClick="back()">Kembali</button>
                    </div>
                    <div class="form-group" id="loader-peramalan" style="display:none;text-align: center;">
                        <div class="alert alert-info">
                            <img width="30px" height="30px" src="<%= MainConfig.getAssetUrl(request)%>/images/loader-classic.gif"/>
                            &nbsp;
                            <i>Sedang import data penjualan...</i>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <div class="col-md-6">
        <div class="form-group">
            <button class="btn btn-default btn-lg btn-block" onClick="downloadTemplate()">
                <span class="fa fa-cloud"></span>
                Unduh format import data
            </button>
        </div>
    </div>

    <% }else{ %>
    <div class="col-md-6">
        <table id="tblx" class="table table-bordered table-striped table-hover">
            <thead>
                <tr>
                    <th width="170">#</th>
                    <th width="170">Periode</th>
                    <th width="120">Kode Barang</th>
                    <th width="90">Qty</th>
                    <th width="90">Status</th>
                </tr>    
            </thead>
            <tbody id="table-body">
                <% if(results.size()>0 && results!=null){ %>
                <%
                    for(int i = 0; i < results.size(); i++){
                        String[] data = (String[]) results.get(i);
                        String strColor = data[4].equalsIgnoreCase("sukses") ? "text-success":"text-danger";
                %>
                <tr>
                    <td><%= data[0] %></td>
                    <td align="center"><%= data[1] %></td>
                    <td><%= data[2] %></td>
                    <td align="right"><%= data[3] %></td>
                    <td><span class="<%= strColor %>"><%= data[4] %></span></td>
                </tr>
                <%
                    }
                %>
                <% }else{ %>
                <tr>
                    <td colspan="5"><strong>Tidak ada data...</strong></td>
                </tr>
                <% } %>
            </tbody>
        </table>
            
        <div class="col-md-12 pull-right">
            <button name="btnKembali" type="button" class="btn btn-warning" onClick="backImport()">Kembali ke menu import</button>
        </div>
    </div>
    <% } %>
                    
</div>

<script>
    function downloadTemplate(){  
       window.open("<%= JSPHandler.generateUrl(request, "penjualan", "download-template", "") %>","_blank");
    } 
    
    function back(){
        location.href = "<%= JSPHandler.generateUrl(request, "penjualan", "", "") %>";
    }
    
    function backImport(){
        location.href = "<%= JSPHandler.generateUrl(request, "penjualan", "import", "") %>";
    }
    
    function hideAndLoading(){
        $('#loader-peramalan').show();
        $('#nav-btn').hide();
    }
</script>

<%
    if(session.getAttribute("result")!=null){
        session.removeAttribute("result");
    }
%>
<%@ include file="../../layout/bottom-page.jsp"%>