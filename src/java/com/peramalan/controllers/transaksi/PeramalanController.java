/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.peramalan.controllers.transaksi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.peramalan.model.master.Barang;
import com.peramalan.model.master.DbBarang;
import com.peramalan.model.transaksi.DbPenjualan;
import com.peramalan.model.transaksi.DbPeramalan;
import com.peramalan.model.transaksi.DbPeramalanDetail;
import com.peramalan.model.transaksi.Penjualan;
import com.peramalan.model.transaksi.Peramalan;
import com.peramalan.model.transaksi.PeramalanDetail;
import com.peramalan.services.JSPHandler;
import com.peramalan.services.LoginServices;
import com.peramalan.services.PaginationServices;
import com.peramalan.services.PeramalanServices;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.TimeUnit;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author OxysystemPC
 */
@WebServlet(name = "PeramalanController", urlPatterns = {"/peramalan"})
public class PeramalanController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession(true);
        
        String action = (request.getParameter(JSPHandler.PAGE_QUERY_REQUEST_PREFFIX)!=null ? (request.getParameter(JSPHandler.PAGE_QUERY_REQUEST_PREFFIX)) : "");
        String pageName = "";
        String pageLocation = "";
        int limitData = 10;
        boolean isSuccess = false;
        
        if(action.equalsIgnoreCase("")){
            pageLocation = "/WEB-INF/transaksi/peramalan/peramalan.jsp";
            pageName = "Peramalan";
            
        }else if(action.equalsIgnoreCase("hitung-peramalan")){
            
            int peramalanBulan = JSPHandler.requestInt(request, "peramalan_bulan");
            int peramalanTahun = JSPHandler.requestInt(request, "peramalan_tahun");
            int penjualanBulan= JSPHandler.requestInt(request, "penjualan_bulan");
            int penjualanTahun = JSPHandler.requestInt(request, "penjualan_tahun");
            
            long userId = Long.parseLong(session.getAttribute(LoginServices.LOGIN_USER_ID).toString());
            long oidPeramlan = PeramalanServices.hitungPeramalan(penjualanBulan, penjualanTahun, peramalanBulan, peramalanTahun, userId);
            
            Map result = new HashMap();
            result.put("status", oidPeramlan!=0 ? "OK":"GAGAL");
            result.put("peramalan_id", oidPeramlan);
            
            try {
                out.print(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(result));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return;
            
            /* lakukan peramalan disini */
        }else if(action.equalsIgnoreCase("arsip-get")){
            response.setContentType("application/json;charset=UTF-8");
            
            /* persiapan data untuk pagination */
            int currentPage = JSPHandler.requestInt(request, "currentPage")==0 ? 1:JSPHandler.requestInt(request, "currentPage");
            int command = JSPHandler.requestInt(request, "command");
            String param = JSPHandler.requestString(request, "param");
            int tahun = JSPHandler.requestInt(request, "tahun");
            int bulan = JSPHandler.requestInt(request, "bulan");
            
            String where = "" + DbPeramalan.COL_NOMOR + " like '%"+ param +"%'";
            
            if(tahun!=0){
                where += " and " + DbPeramalan.COL_END_TAHUN + "='"+ tahun +"'";
            }
            
            if(bulan!=0){
                where += " and " + DbPeramalan.COL_END_BULAN + "='"+ bulan +"'";
            }
            
            int totalData = DbPeramalan.count(where);
            PaginationServices pagination = new PaginationServices(totalData, limitData, currentPage, command);            
            Vector datas = DbPeramalan.list(where, "", pagination.getStart(), pagination.getRecordToGet());
            
            Map res = new HashMap();
            res.put("pagination", pagination);
            res.put("data", datas);
            
            /* agar loader terlihat */
            try{
                TimeUnit.MILLISECONDS.sleep(100);
            }catch(Exception e){}
            
            try {
                out.print(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(res));
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            return;
            
        }else if(action.equalsIgnoreCase("arsip")){
            pageLocation = "/WEB-INF/transaksi/peramalan/peramalan-arsip.jsp";
            pageName = "Peramalan;Arsip";
            
        }else if(action.equalsIgnoreCase("detail-get")){    
            response.setContentType("application/json;charset=UTF-8");
            
            /* persiapan data untuk pagination */
            int currentPage = JSPHandler.requestInt(request, "currentPage")==0 ? 1:JSPHandler.requestInt(request, "currentPage");
            int command = JSPHandler.requestInt(request, "command");
            String param = JSPHandler.requestString(request, "param");
            int tahun = JSPHandler.requestInt(request, "tahun");
            int bulan = JSPHandler.requestInt(request, "bulan");
            long id = JSPHandler.requestLong(request, "id");
            double alpha = JSPHandler.requestDouble(request, "alpha");
            int disarankan = JSPHandler.requestInt(request, "disarankan");
            
            
            int totalData = DbPeramalanDetail.countPeramalanDetailJoinBarang(param, tahun, bulan, id, alpha, DbPeramalanDetail.DETAIL_TIPE_PERAMALAN, disarankan);
            PaginationServices pagination = new PaginationServices(totalData, limitData, currentPage, command);            
            Vector datas = DbPeramalanDetail.listPeramalanDetailJoinBarang(param, tahun, bulan, id, alpha, DbPeramalanDetail.DETAIL_TIPE_PERAMALAN, disarankan, "", pagination.getStart(), pagination.getRecordToGet());
            
            Map res = new HashMap();
            res.put("pagination", pagination);
            res.put("data", datas);
            
            /* agar loader terlihat */
            try{
                TimeUnit.MILLISECONDS.sleep(100);
            }catch(Exception e){}
            
            try {
                out.print(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(res));
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            return;
            
        }else if(action.equalsIgnoreCase("analisa")){
            int tahun = JSPHandler.requestInt(request, "tahun");
            int bulan = JSPHandler.requestInt(request, "bulan");
            long id = JSPHandler.requestLong(request, "id");
            long barangId = JSPHandler.requestLong(request, "barangId");
            double alpha = JSPHandler.requestDouble(request, "alpha");
            
            Peramalan peramalan = new Peramalan();
            try {
                peramalan = DbPeramalan.findById(id);
            } catch (Exception e) {
            }
            
            Barang barang = new Barang();
            try {
                barang = DbBarang.findById(barangId);
            } catch (Exception e) {
            }
            
            String where = "peramalan_id='"+ id +"' and barang_id='"+ barangId +"' and alpha='"+ alpha +"' and ((tahun*12)+bulan)<='"+ ((tahun * 12)+bulan) +"'";
            Vector<PeramalanDetail> peramalanDetails = new Vector<PeramalanDetail>();
            try {
                peramalanDetails = DbPeramalanDetail.list(where, "barang_id asc, tahun asc, bulan asc", 0, 0);
            } catch (Exception e) {
            }
            
            Penjualan penjualanTerakhir = new Penjualan();
            try {
                penjualanTerakhir = DbPenjualan.findByLastPeriode(barangId);
            } catch (Exception e) {
            }

            int periodeTerakhirPenjualan = (penjualanTerakhir.getTahun() * 12) + penjualanTerakhir.getBulan();
            
            request.setAttribute("peramalan", peramalan);
            request.setAttribute("peramalanDetails", peramalanDetails);
            request.setAttribute("barang", barang);
            request.setAttribute("alpha", alpha);
            request.setAttribute("periodeTerakhirPenjualan", periodeTerakhirPenjualan);
            
            pageLocation = "/WEB-INF/transaksi/peramalan/peramalan-analisa.jsp";
            pageName = "Peramalan;Detail;Analisa";
            
        }else if(action.equalsIgnoreCase("detail")){
            long id = JSPHandler.requestLong(request, "id");
            
            Peramalan peramalan = new Peramalan();
            try {
                peramalan = DbPeramalan.findById(id);
            } catch (Exception e) {
            }
            
            request.setAttribute("peramalan", peramalan);
            
            pageLocation = "/WEB-INF/transaksi/peramalan/peramalan-detail.jsp";
            pageName = "Peramalan;Detail";
            
        }else if(action.equals("export-detail-pdf")){
            
            long id = JSPHandler.requestLong(request, "id");
            double alpha = JSPHandler.requestDouble(request, "alpha");
            
            Peramalan peramalan = new Peramalan();
            try {
                peramalan = DbPeramalan.findById(id);
            } catch (Exception e) {
            }
            
            Vector<PeramalanDetail> peramalanDetails = new Vector<PeramalanDetail>();
            try{
                peramalanDetails = DbPeramalanDetail.listPeramalanDetailJoinBarang("", 0, 0, id, alpha, DbPeramalanDetail.DETAIL_TIPE_PERAMALAN,0, "", 0, 0);
            }catch(Exception e){}
            
            request.setAttribute("peramalan", peramalan);
            request.setAttribute("peramalanDetails", peramalanDetails);
            
            pageLocation = "/WEB-INF/transaksi/peramalan/peramalan-detail-pdf.jsp";
            
        }else{
            pageLocation = "/WEB-INF/transaksi/peramalan/peramalan.jsp";
            pageName = "Peramalan";
        }
        
        if(pageLocation.length()>0){
            request.setAttribute("Page", pageName);
            request.getRequestDispatcher(pageLocation).forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
