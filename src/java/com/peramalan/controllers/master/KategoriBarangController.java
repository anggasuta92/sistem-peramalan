/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.peramalan.controllers.master;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.peramalan.model.master.DbKategoriBarang;
import com.peramalan.services.DataTablesService;
import com.peramalan.services.JSPHandler;
import com.peramalan.services.PaginationServices;
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

/**
 *
 * @author OxysystemPC
 */
@WebServlet(name = "KategoriBarangController", urlPatterns = {"/kategori-barang"})
public class KategoriBarangController extends HttpServlet {

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
        
        String action = (request.getParameter(JSPHandler.PAGE_QUERY_REQUEST_PREFFIX)!=null ? (request.getParameter(JSPHandler.PAGE_QUERY_REQUEST_PREFFIX)) : "");
        String pageLocation = "";
        String pageName = "";
        int limitData = 5;
  
        if(action.equals("get-data")){
            response.setContentType("application/json;charset=UTF-8");
            
            /* data preparation */
            int currentPage = JSPHandler.requestInt(request, "currentPage")==0 ? 1:JSPHandler.requestInt(request, "currentPage");
            int command = JSPHandler.requestInt(request, "command");
            String param = JSPHandler.requestString(request, "param");
            String where = "";
            if(param!=null && param.length()>0){
                where = DbKategoriBarang.COL_CODE + " like '%"+ param +"%' or " + DbKategoriBarang.COL_NAME + " like '%"+ param +"%'";
            }
            
            int totalData = DbKategoriBarang.count(where);
            PaginationServices pagination = new PaginationServices(totalData, limitData, currentPage, command);            
            Vector datas = DbKategoriBarang.list(where, DbKategoriBarang.COL_CODE, pagination.getStart(), pagination.getRecordToGet());
            
            Map res = new HashMap();
            res.put("pagination", pagination);
            res.put("data", datas);
            
            try{
                TimeUnit.MILLISECONDS.sleep(500);
            }catch(Exception e){}
            
            try {
                out.print(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(res));
            } catch (Exception e) {
            }
            
            //request.setAttribute("current", ""+pagination.getCurrentPage());
            //request.setAttribute("total", "ini total");
            //request.setAttribute("data", datas);
            
            //pageLocation = "/WEB-INF/master/kategori-barang/kategori-barang-table.jsp";
            pageName = "Data Master;Kategori Barang";
        }else{
            pageLocation = "/WEB-INF/master/kategori-barang/kategori-barang.jsp";
            pageName = "Data Master;Kategori Barang";
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
