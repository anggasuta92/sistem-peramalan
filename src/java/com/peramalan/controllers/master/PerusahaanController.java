/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.peramalan.controllers.master;

import com.peramalan.model.master.DbPerusahaan;
import com.peramalan.model.master.Perusahaan;
import com.peramalan.services.JSPHandler;
import com.peramalan.services.LoginServices;
import java.io.IOException;
import java.io.PrintWriter;
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
@WebServlet(name = "PerusahaanController", urlPatterns = {"/perusahaan"})
public class PerusahaanController extends HttpServlet {

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
        String pageLocation = "";
        String pageName = "";
        int limitData = 5;
        boolean isSuccess = false;
  
        if(action.equals("")){
            
            Perusahaan perusahaan = new Perusahaan();
            try {
                perusahaan = DbPerusahaan.getPerusahaan();
            } catch (Exception e) {
            }
            
            request.setAttribute("perusahaan", perusahaan);
            
            pageLocation = "/WEB-INF/master/perusahaan/perusahaan.jsp";
            pageName = "Administrator;Perusahaan";
            
        }else if(action.equals("update")){
            
            Perusahaan data = new Perusahaan();
            data.setPerusahaanId(JSPHandler.requestLong(request, DbPerusahaan.COL_PERUSAHAAN_ID));
            data.setNama(JSPHandler.requestString(request, DbPerusahaan.COL_NAMA));
            data.setAlamat(JSPHandler.requestString(request, DbPerusahaan.COL_ALAMAT));
            data.setTelepon(JSPHandler.requestString(request, DbPerusahaan.COL_TELEPON));
            data.setNamaPemilik(JSPHandler.requestString(request, DbPerusahaan.COL_NAMA_PEMILIK));
            
            try {
                long oid = DbPerusahaan.update(data);
                session.setAttribute(LoginServices.PERUSAHAN_NAMA, data.getNama());
                session.setAttribute(LoginServices.PERUSAHAN_ALAMAT, data.getAlamat());
                
                isSuccess = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            /* untuk kepentingan perpesanan di halaman jsp */
            if(isSuccess){
                session.setAttribute(JSPHandler.SESSION_MESSAGING, "Data telah tersimpan");
            }else{
                session.setAttribute(JSPHandler.SESSION_MESSAGING, "Data gagal tersimpan");
            }
            
            response.sendRedirect(JSPHandler.generateUrl(request, "perusahaan", "", ""));
            return;
            
        }else{
            pageLocation = "/WEB-INF/master/perusahaan/perusahaan.jsp";
            pageName = "Administrator;Perusahaan";
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
