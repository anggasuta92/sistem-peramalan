<%@page import="com.peramalan.services.NumberServices"%>
<%@page import="com.peramalan.model.master.Barang"%>
<%@page import="com.peramalan.model.transaksi.PeramalanDetail"%>
<%@page import="java.util.Vector"%>
<%@page import="com.peramalan.services.PeramalanServices"%>
<%@page import="com.peramalan.model.transaksi.Peramalan"%>
<%@page import="com.peramalan.model.transaksi.DbPenjualan"%>
<%@page import="com.peramalan.services.TransaksiService"%>
<%@page import="com.peramalan.services.PaginationServices"%>
<%@page import="com.peramalan.config.MainConfig"%>
<%@page import="com.peramalan.services.JSPHandler"%>
<%@ include file="../../layout/top-page.jsp"%>

<%!
    public static String addString(String strOld, String strAdd){
        if(strOld.length()>0){
            strOld += ",";
        }

        return strOld + strAdd;
    }
%>

<%
    Peramalan peramalan = new Peramalan();
    try {
        peramalan = (Peramalan) request.getAttribute("peramalan");
    } catch (Exception e) {
    }
    
    Vector<PeramalanDetail> peramalanDetails = new Vector<PeramalanDetail>();
    try {
        peramalanDetails = (Vector<PeramalanDetail>) request.getAttribute("peramalanDetails");
    } catch (Exception e) {
    }
    
    Barang barang = new Barang();
    try {
        barang = (Barang) request.getAttribute("barang");
    } catch (Exception e) {
    }
    
    double alpha = 0;
    try {
        alpha = Double.parseDouble(request.getAttribute("alpha").toString());
    } catch (Exception e) {
    }
    
    int periodeTerakhirPenjualan = 0;
    try{
        periodeTerakhirPenjualan = Integer.parseInt(request.getAttribute("periodeTerakhirPenjualan").toString());
    }catch(Exception e){}
    
%>

<div class="row">
    <div class="col-lg-12">
        <div class="panel panel-primary">
            <div class="panel-heading">
                <h3 class="panel-title"><i class="fa fa-archive"></i> Detail Peramalan</h3>
            </div>
            <div class="panel-body">
                <div class="row">
                    <div class="col-sm-2">Kode</div>
                    <div class="col-sm-3">: <%= barang.getKode() + " / " + barang.getBarcode() %></div>
                    <div class="col-sm-1"></div>
                    <div class="col-sm-1">Alpha</div>
                    <div class="col-sm-2">: <%= alpha %></div>
                    
                </div>
                <div class="row">
                    <div class="col-sm-2">Nama Barang</div>
                    <div class="col-sm-3">: <%= barang.getNama() %></div>
                    <div class="col-sm-1"></div>

                </div>
                <hr/>
                
                <div class="row">
                    <div class="col-lg-8">
                        <canvas id="chartPeramalan"></canvas>
                    </div>
                </div>
                <div class="row">
                    <div class="col-lg-12">
                        <table id="tblx" class="table table-bordered table-striped table-hover">
                            <thead>
                                <tr>
                                    <th width="50">#</th>
                                    <th width="200">Periode</th>
                                    <th width="80">Penjualan</th>
                                    <th width="80">Peramalan</th>
                                    <th width="80">Selisih</th>
                                    <th width="80">Persen Selisih</th>
                                    <th>Keterangan</th>
                                </tr>    
                            </thead>
                            <tbody id="table-body">
                                <%
                                    int countPeramalan= 0;
                                    double totalPeramalan = 0;
                                    double totalPenjualan = 0;
                                    double totalAPE = 0;
                                    
                                    String strPeriode = "";
                                    String strPeramalanQty = "";
                                    String strPenjualanQty = "";
                                    
                                    for(int i = 0; i < peramalanDetails.size(); i++){
                                        PeramalanDetail peramalanDetail = (PeramalanDetail) peramalanDetails.get(i);
                                        
                                        String periodeName = TransaksiService.periodeBulan[peramalanDetail.getBulan()] + " " + peramalanDetail.getTahun();
                                        
                                        boolean hitungError = true;
                                        if(periodeTerakhirPenjualan<=((peramalanDetail.getTahun()*12)+peramalanDetail.getBulan()) && peramalanDetail.getPenjualan()==0){
                                            hitungError = false;
                                        }
                                        
                                        double selisih = 0; 
                                        double error = 0;
                                        
                                        String strKeterangan = "-";
                                        
                                        if(i>=2){
                                            if(hitungError){
                                                selisih = peramalanDetail.getPenjualan() - peramalanDetail.getPeramalan();
                                                error = selisih / peramalanDetail.getPenjualan() * 100;
                                                error = Math.abs(error);

                                                strPeriode = addString(strPeriode, "'"+ periodeName +"'");
                                                strPeramalanQty = addString(strPeramalanQty, ""+ NumberServices.formatNumber(peramalanDetail.getPeramalan(), "###0.00") +"");
                                                strPenjualanQty = addString(strPenjualanQty, ""+ NumberServices.formatNumber(peramalanDetail.getPenjualan(), "###0.00") +"");

                                                countPeramalan++;

                                                strKeterangan = "Persentase kesalahan : "+ NumberServices.formatNumber(error, "#,##0.00") + "%";
                                            }else{
                                                strKeterangan = "Hasil peramalan periode " + periodeName;
                                            }
                                        }   
                                        
                                        totalPeramalan += peramalanDetail.getPeramalan();
                                        totalPenjualan += peramalanDetail.getPenjualan();
                                        totalAPE += error;
                                %>
                                <tr>
                                    <td align="center" style="font-size: smaller"><%= (i+1) %></td>
                                    <td style="font-size: smaller"><%= periodeName %></td>
                                    <td align="right" style="font-size: smaller"><%= NumberServices.formatNumber(peramalanDetail.getPenjualan(), "#,##0.00") %></td>
                                    <td align="right" style="font-size: smaller"><%= NumberServices.formatNumber(peramalanDetail.getPeramalan(), "#,##0.00") %></td>
                                    <td align="right" style="font-size: smaller"><%= NumberServices.formatNumber(selisih, "#,##0.00") %></td>
                                    <td align="right" style="font-size: smaller"><%= NumberServices.formatNumber(error, "#,##0.00") %></td>
                                    <td align="left"><small><%= strKeterangan %></small></td>
                                </tr>
                                <%
                                    }
                                %>
                                <tr>
                                    <td style="background-color: silver; font-weight: bolder" colspan="2" align="right">TOTAL </td>
                                    <td style="background-color: silver; font-weight: bolder" align="right"><%= NumberServices.formatNumber(totalPeramalan, "#,##0.00") %></td>
                                    <td style="background-color: silver; font-weight: bolder" align="right"><%= NumberServices.formatNumber(totalPenjualan, "#,##0.00") %></td>
                                    <td style="background-color: silver; font-weight: bolder" align="right"><%= NumberServices.formatNumber((totalPenjualan - totalPeramalan), "#,##0.00") %></td>
                                    <td style="background-color: silver; font-weight: bolder" align="right"><%= NumberServices.formatNumber(totalAPE, "#,##0.00") %></td>
                                    <td style="background-color: silver; font-weight: bolder" align="right"></td>
                                </tr>
                                <tr>
                                    <td style="background-color: silver; font-weight: bolder" align="right" colspan="3">Rata rata persentase kesalahan </td>
                                    <td style="background-color: silver; font-weight: bolder" align="right" colspan="2">
                                        <%= NumberServices.formatNumber(totalAPE, "#,##0.00") %> / <%= NumberServices.formatNumber(countPeramalan, "#,##0.00") %>  
                                    </td>
                                    <td style="background-color: silver; font-weight: bolder" align="right"><%= NumberServices.formatNumber(totalAPE / countPeramalan, "#,##0.00") %></td>
                                    <td style="background-color: silver; font-weight: bolder" align="right"></td>
                                </tr>
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
    var chartPeramalan = document.getElementById('chartPeramalan').getContext('2d');
    var chartPr = new Chart(chartPeramalan, {
        type: 'line',
        data: {
            labels: [<%= strPeriode %>],
            datasets: [{
                label: 'Peramalan',
                data: [<%= strPeramalanQty %>],
                fill:false,
                borderColor: 'rgba(0,0,0,1)' ,
                borderWidth: 1
            },
            {
                label: 'Penjualan',
                data: [<%= strPenjualanQty %>],
                fill:false,
                borderColor: 'rgba(255, 99, 132, 1)',
                borderWidth: 1
            }]
        },
        options: {
            scales: {
                yAxes: [{
                    ticks: {
                        beginAtZero: true
                    }
                }]
            }
        }
    });
    
    function back(){
        location.href = "<%= JSPHandler.generateUrl(request, "peramalan", "detail", "id="+ peramalan.getPeramalanId() +"") %>";
    }
</script>
<%@ include file="../../layout/bottom-page.jsp"%>