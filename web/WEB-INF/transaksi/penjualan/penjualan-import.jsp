<%@page import="java.util.Map"%>
<%@page import="java.util.Vector"%>
<%@page import="com.peramalan.services.NumberServices"%>
<%@page import="com.peramalan.config.MainConfig"%>
<%@page import="com.peramalan.services.JSPHandler"%>
<%@ include file="../../layout/top-page.jsp"%>

<%
    int uploaded = 0;
    try {
        uploaded = Integer.parseInt(request.getAttribute("uploaded").toString());
    } catch (Exception e) {
    }
    
    out.println("uploaded: " + uploaded);
%>

<div class="row">
    
    <% if(uploaded==0){ %>
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
                    <div class="form-group">
                        <input type="submit" class="form-control btn btn-primary" name="btnSubmit" value="Import sekarang" />
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
    <% } %>
                    
</div>

<script>
    function downloadTemplate(){  
       window.open("<%= JSPHandler.generateUrl(request, "penjualan", "download-template", "") %>","_blank");
    } 
</script>

<%@ include file="../../layout/bottom-page.jsp"%>