/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.peramalan.controllers;

import com.peramalan.model.master.DbPengguna;
import com.peramalan.model.master.DbPerusahaan;
import com.peramalan.model.master.Pengguna;
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
@WebServlet(name = "LoginController", urlPatterns = {"/login"})
public class LoginController extends HttpServlet {

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
  
        if(action.equals("login")){
            pageLocation = "/index.jsp";
            pageName = "Login";
            
        }else if(action.equals("auth")){
            
            String username = JSPHandler.requestString(request, "user");
            String password = JSPHandler.requestString(request, "pass");
            
            Pengguna user = new Pengguna();
            try {
                user = DbPengguna.findByUsernamePassword(username, password);
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            
            LoginServices.removeAllLoginInformation(session);
            
            if(user.getPenggunaId()!=0){
                
                /* login berhasil */
                if(user.getStatus()==1){
                    LoginServices.setUserPriv(request, user.getRoleId(), user.getPenggunaId());
                    session.setAttribute(LoginServices.LOGIN_STATUS, LoginServices.LOGIN_STATUS_TRUE);
                    session.setAttribute(LoginServices.LOGIN_USER_ID, user.getPenggunaId());

                    Perusahaan perusahaan = new Perusahaan();
                    try {
                        perusahaan = DbPerusahaan.getPerusahaan();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    session.setAttribute(LoginServices.PERUSAHAN_NAMA, perusahaan.getNama());
                    session.setAttribute(LoginServices.PERUSAHAN_ALAMAT, perusahaan.getAlamat());

                    response.sendRedirect(JSPHandler.generateUrl(request, "home", "", ""));
                }else{
                    session.setAttribute(LoginServices.LOGIN_STATUS, LoginServices.LOGIN_STATUS_FALSE);
                    session.setAttribute(JSPHandler.SESSION_MESSAGING, "Maaf user anda sudah tidak aktif");
                    response.sendRedirect(JSPHandler.generateUrl(request, "login", "", ""));
                }
            }else{
                session.setAttribute(LoginServices.LOGIN_STATUS, LoginServices.LOGIN_STATUS_FALSE);
                session.setAttribute(JSPHandler.SESSION_MESSAGING, "Username atau password anda tidak sesuai");
                response.sendRedirect(JSPHandler.generateUrl(request, "login", "", ""));
            }
            
        }else if(action.equals("logout")){
            
            LoginServices.removeAllLoginInformation(session);
            response.sendRedirect(JSPHandler.generateUrl(request, "login", "", ""));
            
        }else{
            pageLocation = "/index.jsp";
            pageName = "Login";
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
