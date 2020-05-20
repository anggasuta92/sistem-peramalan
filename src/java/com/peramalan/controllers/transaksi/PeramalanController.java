/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.peramalan.controllers.transaksi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.peramalan.model.master.Barang;
import com.peramalan.model.master.DbBarang;
import com.peramalan.model.master.DbKategoriBarang;
import com.peramalan.model.master.KategoriBarang;
import com.peramalan.model.transaksi.DbPeramalan;
import com.peramalan.services.JSPHandler;
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
            
            long barangId = Long.parseLong("283062973519184");
            //PeramalanServices.hitungPeramalan(2019, 12, barangId);
            
            double bobotPeramalan = PeramalanServices.tentukanBobot(2020, 12, barangId);
            
            
        }else if(action.equalsIgnoreCase("hitung-peramalan")){
            
            
            
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
