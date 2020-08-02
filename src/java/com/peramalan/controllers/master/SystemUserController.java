/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.peramalan.controllers.master;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.peramalan.model.master.DbRole;
import com.peramalan.model.master.DbPengguna;
import com.peramalan.model.master.Role;
import com.peramalan.model.master.Pengguna;
import com.peramalan.services.JSPHandler;
import com.peramalan.services.LoginServices;
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
@WebServlet(name = "SystemUserController", urlPatterns = {"/user"})
public class SystemUserController extends HttpServlet {

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
            
            int totalData = DbPengguna.count(where);
            PaginationServices pagination = new PaginationServices(totalData, limitData, currentPage, command);            
            Vector datas = DbPengguna.listJoinRole(where, DbPengguna.COL_NAMA, pagination.getStart(), pagination.getRecordToGet());
            
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
            
            Vector<Role> roles = new Vector<Role>();
            try {
                roles = DbRole.list("", "", 0, 0);
            } catch (Exception e) {
            }
            
            request.setAttribute("roles", roles);
            pageLocation = "/WEB-INF/master/systemuser/systemuser-add.jsp";
            pageName = "Administrator;Data User;Tambah";
            
        }else if(action.equals("update-nama")){    
            long oid = 0;
            Pengguna user = new Pengguna();
            try {
                user = DbPengguna.findById(JSPHandler.requestLong(request, DbPengguna.COL_PENGGUNA_ID));
            } catch (Exception e) {
            }
            if(user.getPenggunaId()!=0){
                user.setNama(JSPHandler.requestString(request, DbPengguna.COL_NAMA));
                DbPengguna.update(user);
                isSuccess = true;
            }else{
                oid = 0;
                isSuccess = false;
            }
            
            /* untuk kepentingan perpesanan di halaman jsp */
            if(isSuccess){
                session.setAttribute(JSPHandler.SESSION_MESSAGING, "Data telah tersimpan");
            }else{
                session.setAttribute(JSPHandler.SESSION_MESSAGING, "Data gagal tersimpan");
            }
            
            /* redirect setelah simpan */
            response.sendRedirect(JSPHandler.generateUrl(request, "user", "akun", ""));
            return;
            
        }else if(action.equals("save")){
            
            long oid = 0;
            
            Pengguna user = new Pengguna();
            user.setNama(JSPHandler.requestString(request, DbPengguna.COL_NAMA));
            user.setStatus(1);
            user.setPassword(JSPHandler.requestString(request, DbPengguna.COL_PASSWORD));
            user.setUsername(JSPHandler.requestString(request, DbPengguna.COL_USERNAME));
            user.setRoleId(JSPHandler.requestLong(request, DbPengguna.COL_ROLE_ID));
            
            /* md5 */
            user.setPassword(LoginServices.generateMD5(user.getPassword()));
            
            try {
                oid = DbPengguna.save(user);
                isSuccess = true;
            } catch (Exception e) {
                System.out.println("err_insert_controller:" + e.toString());
            }
            
            /* untuk kepentingan perpesanan di halaman jsp */
            if(isSuccess){
                session.setAttribute(JSPHandler.SESSION_MESSAGING, "Data telah tersimpan");
            }else{
                session.setAttribute(JSPHandler.SESSION_MESSAGING, "Data gagal tersimpan");
            }
            
            /* redirect setelah simpan */
            response.sendRedirect(JSPHandler.generateUrl(request, "user", "edit", "id="+oid));
            return;
        
        }else if(action.equals("update-pass")){

            long oid = 0;
            Pengguna data = new Pengguna();
            try {
                data = DbPengguna.findById(JSPHandler.requestLong(request, DbPengguna.COL_PENGGUNA_ID));
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            if(data.getPenggunaId()!=0){
                Pengguna user = new Pengguna();
                user = data;
                String pass = JSPHandler.requestString(request, ""+DbPengguna.COL_PASSWORD);
                user.setPassword(LoginServices.generateMD5(pass));
                
                try {
                    oid = DbPengguna.update(user);
                } catch (Exception e) {
                    System.out.println("err_insert_controller:" + e.toString());
                }
            }else{
                oid = 0;
            }

            Map res = new HashMap();
            res.put("oid", oid);
            res.put("message", oid!=0 ? "Password berhasil disimpan":"Password gagal tersimpan");
            
            response.setContentType("application/json;charset=UTF-8");
            try {
                out.print(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(res));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return;
            
        }else if(action.equals("update")){
            
            long oid = 0;
            
            Pengguna data = new Pengguna();
            try {
                data = DbPengguna.findById(JSPHandler.requestLong(request, DbPengguna.COL_PENGGUNA_ID));
            } catch (Exception e) {
            }
            
            if(data.getPenggunaId()!=0){
                
                Pengguna user = new Pengguna();
                user = data;
                user.setNama(JSPHandler.requestString(request, DbPengguna.COL_NAMA));
                user.setStatus(JSPHandler.requestInt(request, DbPengguna.COL_STATUS));
                user.setUsername(JSPHandler.requestString(request, DbPengguna.COL_USERNAME));
                user.setRoleId(JSPHandler.requestLong(request, DbPengguna.COL_ROLE_ID));

                /* md5 */
                /*
                String pass = JSPHandler.requestString(request, "new_"+DbSystemUser.COL_PASSWORD);
                if(pass.trim().length()>0){
                    user.setPassword(LoginServices.generateMD5(user.getPassword()));
                }
                */
                
                try {
                    oid = DbPengguna.update(user);
                    isSuccess = true;
                } catch (Exception e) {
                    System.out.println("err_insert_controller:" + e.toString());
                }
            }else{
                isSuccess = false;
            }

            /* untuk kepentingan perpesanan di halaman jsp */
            if(isSuccess){
                session.setAttribute(JSPHandler.SESSION_MESSAGING, "Data telah tersimpan");
            }else{
                session.setAttribute(JSPHandler.SESSION_MESSAGING, "Data gagal tersimpan");
            }
            
            /* redirect setelah simpan */
            response.sendRedirect(JSPHandler.generateUrl(request, "user", "edit", "id="+oid));
            return;
            
        }else if(action.equals("edit")){
            
            long id = JSPHandler.requestLong(request, "id");
            
            Pengguna data = new Pengguna();
            try{
                data = DbPengguna.findById(id);
            }catch(Exception e){
                System.out.println("err_findById: " + e.toString());
            }
            
            Vector<Role> roles = new Vector<Role>();
            try {
                roles = DbRole.list("", "", 0, 0);
            } catch (Exception e) {
            }
            
            request.setAttribute("roles", roles);
            
            if(data.getPenggunaId()!=0){
                request.setAttribute("data", data);
                pageLocation = "/WEB-INF/master/systemuser/systemuser-edit.jsp";
                pageName = "Data Master;Barang;Ubah";
                
            }else{
                response.sendRedirect(JSPHandler.generateUrl(request, "user", "", ""));
                return;
            }
        }else if(action.equals("delete")){
            response.setContentType("application/json;charset=UTF-8");
            
            long id = JSPHandler.requestLong(request, "id");
            
            Pengguna sysuser = new Pengguna();
            try{
                sysuser = DbPengguna.findById(id);
            }catch(Exception e){
                System.out.println("err_delete_controller: findById " + e.toString());
            }
            
            if(sysuser.getPenggunaId()!=0){
                boolean success = DbPengguna.delete(sysuser);
                if(success){
                    out.println(JSPHandler.generateJsonMessage(0, "Data telah terhapus"));
                }else{
                    out.println(JSPHandler.generateJsonMessage(0, "Data gagal dihapus"));
                }
            }else{
                out.println(JSPHandler.generateJsonMessage(0, "Data gagal dihapus"));
            }
            return;
            
        }else if(action.equals("akun")){
            
            long id = Long.parseLong(session.getAttribute(LoginServices.LOGIN_USER_ID).toString());
            
            Pengguna systemUser = new Pengguna();
            try {
                systemUser = DbPengguna.findById(id);
            } catch (Exception e) {
            }
            
            request.setAttribute("data", systemUser);
            
            pageLocation = "/WEB-INF/master/systemuser/systemuser-akun.jsp";
            pageName = "Akun Saya";
        }else{
            pageLocation = "/WEB-INF/master/systemuser/systemuser.jsp";
            pageName = "Administrator;Data User";
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
