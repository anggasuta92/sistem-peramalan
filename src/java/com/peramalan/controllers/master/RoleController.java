/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.peramalan.controllers.master;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.peramalan.model.master.DbKategoriBarang;
import com.peramalan.model.master.DbRole;
import com.peramalan.model.master.DbRoleDetail;
import com.peramalan.model.master.KategoriBarang;
import com.peramalan.model.master.Role;
import com.peramalan.model.master.RoleDetail;
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
            pageName = "Administrator;Role User;Tambah";
            
        }else if(action.equals("update")){
            
            long id = JSPHandler.requestLong(request, DbRole.COL_ROLE_ID);
            boolean allDetailSuccess = true;
            boolean mainSuccess = false;
            
            Role role = new Role();
            try {
                role = DbRole.findById(id);
            } catch (Exception e) {
            }
            
            if(role.getRoleId()!=0){
                role.setNama(JSPHandler.requestString(request, DbRole.COL_NAMA));
                
                try {
                    long oid = DbRole.update(role);
                    if(oid!=0){
                        mainSuccess = true;
                    }else{
                        mainSuccess = false;
                    }
                } catch (Exception e) {
                    mainSuccess = false;
                }
                
                if(mainSuccess){
                    /* hapus dan simpan ulang detail */
                    MenuServices.deleteRoleDetailByRoleId(role.getRoleId());

                    /* simpan ulang */
                    for(int i = 0; i < MenuServices.strMenu.length; i++){
                        int checked = JSPHandler.requestInt(request, MenuServices.menuPreffix + MenuServices.strMenuKode[i]);
                        RoleDetail roleDetail = new RoleDetail();
                        roleDetail.setRoleId(role.getRoleId());
                        roleDetail.setKodeMenu(MenuServices.strMenuKode[i]);
                        roleDetail.setGranted(checked);

                        try {
                            DbRoleDetail.save(roleDetail);
                        } catch (Exception e) {
                            allDetailSuccess = false;
                            e.printStackTrace();
                        }
                    }
                }else{
                    allDetailSuccess = false;
                }
                    
            }else{
                mainSuccess = false;
            }
            /* untuk kepentingan perpesanan di halaman jsp */
            if(mainSuccess && allDetailSuccess){
                session.setAttribute(JSPHandler.SESSION_MESSAGING, "Data telah tersimpan");
            }else{
                session.setAttribute(JSPHandler.SESSION_MESSAGING, "Data gagal tersimpan");
            }
            
            /* redirect setelah simpan */
            response.sendRedirect(JSPHandler.generateUrl(request, "role", "edit", "id="+role.getRoleId()));
            return;
            
        }else if(action.equals("save")){
            
            long oid = 0;
            boolean allDetailSuccess = true;
            
            Role role = new Role();
            role.setNama(JSPHandler.requestString(request, DbRole.COL_NAMA));
            
            try {
                oid = DbRole.save(role);
            } catch (Exception e) {
                allDetailSuccess = false;
                e.printStackTrace();
            }
            
            /* simpan detail */
            if(oid!=0){
                for(int i = 0; i < MenuServices.strMenu.length; i++){
                    int checked = JSPHandler.requestInt(request, MenuServices.menuPreffix + MenuServices.strMenuKode[i]);
                    RoleDetail roleDetail = new RoleDetail();
                    roleDetail.setRoleId(oid);
                    roleDetail.setKodeMenu(MenuServices.strMenuKode[i]);
                    roleDetail.setGranted(checked);
                    
                    try {
                        DbRoleDetail.save(roleDetail);
                    } catch (Exception e) {
                        allDetailSuccess = false;
                        e.printStackTrace();
                    }
                }
            }
            
            if(allDetailSuccess){
                isSuccess = true;
            }else{
                isSuccess = false;
                
                /* kalau gagal hapus saja semua */
            }
            
            /* untuk kepentingan perpesanan di halaman jsp */
            if(isSuccess){
                session.setAttribute(JSPHandler.SESSION_MESSAGING, "Data telah tersimpan");
            }else{
                session.setAttribute(JSPHandler.SESSION_MESSAGING, "Data gagal tersimpan");
            }
            
            /* redirect setelah simpan */
            response.sendRedirect(JSPHandler.generateUrl(request, "role", "edit", "id="+oid));
            return;
            
        }else if(action.equals("edit")){
            
            long id = JSPHandler.requestLong(request, "id");
            Role role = new Role();
            try {
                role = DbRole.findById(id);
            } catch (Exception e) {
            }
            
            Map details = new HashMap();
            if(role.getRoleId()!=0){
                Vector<RoleDetail> list  = DbRoleDetail.list(DbRoleDetail.COL_ROLE_ID + "='"+ role.getRoleId() +"'", "", 0, 0);
                for(RoleDetail roleDetail : list){
                    details.put(roleDetail.getKodeMenu(), roleDetail.getGranted());
                }
            }
            
            request.setAttribute("role", role);
            request.setAttribute("role_detail", details);
            
            pageLocation = "/WEB-INF/master/role/role-edit.jsp";
            pageName = "Administrator;Role User;Ubah";
        
        }else if(action.equals("delete")){
            response.setContentType("application/json;charset=UTF-8");
            
            long id = JSPHandler.requestLong(request, "id");
            
            Role role = new Role();
            try{
                role = DbRole.findById(id);
            }catch(Exception e){
                System.out.println("err_delete_controller: findById " + e.toString());
            }
            
            if(role.getRoleId()!=0){
                MenuServices.deleteRoleByRoleId(role.getRoleId());
                boolean success = false;
                
                Role checkRole = new Role();
                try {
                    checkRole = DbRole.findById(role.getRoleId());
                } catch (Exception e) {
                }
                
                if(checkRole.getRoleId()==0){
                    success = true;
                }
                
                if(success){
                    out.println(JSPHandler.generateJsonMessage(0, "Data telah terhapus"));
                }else{
                    out.println(JSPHandler.generateJsonMessage(0, "Data gagal dihapus"));
                }
            }else{
                out.println(JSPHandler.generateJsonMessage(0, "Data gagal dihapus"));
            }
            return;
           
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
