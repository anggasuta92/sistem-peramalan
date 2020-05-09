/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.peramalan.controllers.master;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.peramalan.model.master.DbKategoriBarang;
import com.peramalan.model.master.DbRole;
import com.peramalan.model.master.KategoriBarang;
import com.peramalan.services.JSPHandler;
import com.peramalan.services.MenuServices;
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
import javax.servlet.http.HttpSession;

/**
 *
 * @author OxysystemPC
 */
@WebServlet(name = "RoleController", urlPatterns = {"/role"})
public class RoleController extends HttpServlet {

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
  
        if(action.equals("get-data")){
            response.setContentType("application/json;charset=UTF-8");
            
            /* persiapan data untuk pagination */
            int currentPage = JSPHandler.requestInt(request, "currentPage")==0 ? 1:JSPHandler.requestInt(request, "currentPage");
            int command = JSPHandler.requestInt(request, "command");
            String param = JSPHandler.requestString(request, "param");
            String where = "";
            if(param!=null && param.length()>0){
                where = DbRole.COL_NAMA + " like '%"+ param +"%'";
            }
            
            int totalData = DbRole.count(where);
            PaginationServices pagination = new PaginationServices(totalData, limitData, currentPage, command);            
            Vector datas = DbRole.list(where, DbRole.COL_NAMA, pagination.getStart(), pagination.getRecordToGet());
            
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
            
        }else if(action.equals("add")){
            
            pageLocation = "/WEB-INF/master/role/role-add.jsp";
            pageName = "Administrator;Role User;Add";
        }else{
            pageLocation = "/WEB-INF/master/role/role.jsp";
            pageName = "Administrator;Role User";
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
