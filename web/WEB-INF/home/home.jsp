<%@page import="java.util.Map"%>
<%@page import="java.util.Vector"%>
<%@page import="com.peramalan.services.NumberServices"%>
<%@page import="com.peramalan.config.MainConfig"%>
<%@page import="com.peramalan.services.JSPHandler"%>
<%@ include file="../layout/top-page.jsp"%>

<%
    Vector<Map> result = (Vector<Map>) request.getAttribute("chart_penjualan");
    
    String periode = "";
    String valueCurrent = "";
    String valueLast = "";
    
    for(Map map : result){
        periode += (periode.length()>0 ?",":"") + "'" + map.get("periode").toString() + "'";
        valueCurrent += (valueCurrent.length()>0 ?",":"") + map.get("qty_current").toString();
        valueLast += (valueLast.length()>0 ?",":"") + map.get("qty_last").toString();
    }
%>

<div class="row">
    <div class="col-lg-4 mb-4">
        <div class="card border-left-primary shadow h-100 py-2">
            <div class="card-body" style="text-align: center">
                Total Data Barang : <span style="font-size: x-large; font-weight: bolder"><%= request.getAttribute("count_barang") %></span>
            </div>
        </div>
    </div>
    
    <div class="col-lg-4 mb-4">
        <div class="card border-left-primary shadow h-100 py-2">
            <div class="card-body" style="text-align: center">
                Total Data Penjualan : <span style="font-size: x-large; font-weight: bolder"><%= request.getAttribute("count_penjualan") %></span>
            </div>
        </div>
    </div>
    
    <div class="col-lg-4 mb-4">
        <div class="card border-left-primary shadow h-100 py-2">
            <div class="card-body" style="text-align: center">
                Total Data Peramalan : <span style="font-size: x-large; font-weight: bolder">0</span>
            </div>
        </div>
    </div>
    
</div>

<div class="row" style="margin-top: 30px">
    <div class="col-lg-6">
        <div class="card shadow mb-4">
            <div class="card-header py-3">
                <strong>Perbandingan Penjualan</strong>
            </div>
            <div class="card-body">
                <div class="chart-container" style="position: inherit;">
                    <canvas id="chartPenjualan"></canvas>
                </div>
            </div>
        </div>
    </div>    
    <div class="col-lg-6">
        <div class="card shadow mb-4">
            <div class="card-header py-3">
                <strong>Perbandingan Penjualan</strong>
            </div>
            <div class="card-body">
                <div class="chart-container" style="position: inherit;">
                    <canvas id="chartPeramalan"></canvas>
                </div>
            </div>
        </div>
    </div>
</div>

<script>
    
    /* chart */
    var chartPenjualan = document.getElementById('chartPenjualan').getContext('2d');
    var chartPj = new Chart(chartPenjualan, {
        type: 'line',
        data: {
            labels: [<%= periode %>],
            datasets: [{
                label: 'Tahun Ini',
                data: [<%= valueCurrent %>],
                fill:false,
                borderColor: 'rgba(0,0,0,1)' ,
                borderWidth: 1
            },
            {
                label: 'Tahun Lalu',
                data: [<%= valueLast %>],
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
    
    var chartPeramalan = document.getElementById('chartPeramalan').getContext('2d');
    var chartPr = new Chart(chartPeramalan, {
        type: 'line',
        data: {
            labels: [<%= periode %>],
            datasets: [{
                label: 'Tahun Ini',
                data: [<%= valueCurrent %>],
                fill:false,
                borderColor: 'rgba(0,0,0,1)' ,
                borderWidth: 1
            },
            {
                label: 'Tahun Lalu',
                data: [<%= valueLast %>],
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
</script>

<%@ include file="../layout/bottom-page.jsp"%>