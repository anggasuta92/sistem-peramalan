/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.peramalan.controllers;

import com.peramalan.model.master.DbBarang;
import com.peramalan.model.transaksi.DbPenjualan;
import com.peramalan.services.DashboardServices;
import com.peramalan.services.JSPHandler;
import com.peramalan.services.NumberServices;
import java.io.IOException;
import java.util.Map;
import java.util.Vector;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author OxysystemPC
 */
@WebServlet(name = "HomeController", urlPatterns = {"/home"})
public class HomeController extends HttpServlet {

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
        
        String action = (request.getParameter(JSPHandler.PAGE_QUERY_REQUEST_PREFFIX)!=null ? (request.getParameter(JSPHandler.PAGE_QUERY_REQUEST_PREFFIX)) : "");
        String pageName = "";
        String pageLocation = "";
        
        if(action.equals("index")){
            pageName = "Dashboard;Home";
            pageLocation = "/WEB-INF/home/home.jsp";            
        }else{
            
            int countBarang = DbBarang.count("");
            request.setAttribute("count_barang", NumberServices.formatNumber(countBarang, "#,##0"));
            
            int countPenjualan = DbPenjualan.count("");
            request.setAttribute("count_penjualan", NumberServices.formatNumber(countPenjualan, "#,##0"));
            
            Vector<Map> chartPenjualan = DashboardServices.chartPenjualanData();
            request.setAttribute("chart_penjualan", chartPenjualan);
            
            DashboardServices.chartPenjualanData();
            
            pageName = "Dashboard;Home";
            pageLocation = "/WEB-INF/home/home.jsp";
        }
        
//        switch(action){
//            default:
//                pageName = "Dashboard;Home;Uhuy";
//                break;
//        }
        
        request.setAttribute("Page", pageName);
        request.getRequestDispatcher(pageLocation).forward(request, response);
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
