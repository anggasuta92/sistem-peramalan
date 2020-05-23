<%@page import="java.util.Map"%>
<%@page import="java.util.Vector"%>
<%@page import="com.peramalan.services.NumberServices"%>
<%@page import="com.peramalan.config.MainConfig"%>
<%@page import="com.peramalan.services.JSPHandler"%>
<%@ include file="../../layout/top-page.jsp"%>

<div class="row">
    <div class="col-md-6">
        <div class="panel panel-primary">
            <div class="panel-heading">
                Import data penjualan
            </div>
            <div class="panel-body">
                <form action="#" method="post" enctype="multipart/form-data">
                    <div class="form-group">
                        <label >Pilih dokumen penjualan yang akan diimport</label>
                        <input type="file" class="form-control-file" id="filePenjualan">
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
</div>

<script>
    function downloadTemplate(){  
       window.open("<%= JSPHandler.generateUrl(request, "penjualan", "download-template", "") %>","","toolbar=no,status=no,menubar=no,location=center,scrollbars=no,resizable=no,height=500,width=657");  
    } 
</script>

<%@ include file="../../layout/bottom-page.jsp"%>