/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.peramalan.controllers.master;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.peramalan.model.master.DbKategoriBarang;
import com.peramalan.model.master.KategoriBarang;
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
import javax.servlet.http.HttpSession;

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
        HttpSession session = request.getSession(true);
        
        String action = (request.getParameter(JSPHandler.PAGE_QUERY_REQUEST_PREFFIX)!=null ? (request.getParameter(JSPHandler.PAGE_QUERY_REQUEST_PREFFIX)) : "");
        String pageLocation = "";
        String pageName = "";
        int limitData = 5;
        boolean isSuccess = false;
        
        if(session.getAttribute(JSPHandler.SESSION_MESSAGING)!=null){
            session.removeAttribute(JSPHandler.SESSION_MESSAGING);
        }
  
        if(action.equals("get-data")){
            response.setContentType("application/json;charset=UTF-8");
            
            /* persiapan data untuk pagination */
            int currentPage = JSPHandler.requestInt(request, "currentPage")==0 ? 1:JSPHandler.requestInt(request, "currentPage");
            int command = JSPHandler.requestInt(request, "command");
            String param = JSPHandler.requestString(request, "param");
            String where = "";
            if(param!=null && param.length()>0){
                where = DbKategoriBarang.COL_KODE + " like '%"+ param +"%' or " + DbKategoriBarang.COL_NAMA + " like '%"+ param +"%'";
            }
            
            int totalData = DbKategoriBarang.count(where);
            PaginationServices pagination = new PaginationServices(totalData, limitData, currentPage, command);            
            Vector datas = DbKategoriBarang.list(where, DbKategoriBarang.COL_KODE, pagination.getStart(), pagination.getRecordToGet());
            
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
            pageName = "Data Master;Kategori Barang";
            
        }else if(action.equals("add")){
            pageLocation = "/WEB-INF/master/kategori-barang/kategori-barang-add.jsp";
            pageName = "Data Master;Kategori Barang;Tambah";
            
        }else if(action.equals("edit")){
            long id = JSPHandler.requestLong(request, "id");
            
            KategoriBarang data = new KategoriBarang();
            try{
                data = DbKategoriBarang.findById(id);
            }catch(Exception e){
                System.out.println("err_findById: " + e.toString());
            }
            
            System.out.println("REQ: "+request.getAttribute(JSPHandler.SESSION_MESSAGING));
            
            if(data.getKategoriBarangId()!=0){
                request.setAttribute("data", data);
                pageLocation = "/WEB-INF/master/kategori-barang/kategori-barang-edit.jsp";
                pageName = "Data Master;Kategori Barang;Ubah";
            }else{
                response.sendRedirect(JSPHandler.generateUrl(request, "kategori-barang", "", ""));
            }
        
        }else if(action.equals("update")){
            long oid = 0;
            KategoriBarang data = new KategoriBarang();
            data.setKategoriBarangId(JSPHandler.requestLong(request, DbKategoriBarang.COL_KATEGORI_BARANG_ID));
            data.setKode(JSPHandler.requestString(request, DbKategoriBarang.COL_KODE));
            data.setNama(JSPHandler.requestString(request, DbKategoriBarang.COL_NAMA));
            
            try {
                oid = DbKategoriBarang.update(data);
                isSuccess = true;
            } catch (Exception e) {
                System.out.println("err_insert_controller:" + e.toString());
            }
            
            /* untuk kepentingan perpesanan di halaman jsp, tapi belum berhasil */
            if(isSuccess){
                session.setAttribute(JSPHandler.SESSION_MESSAGING, "Data telah tersimpan");
            }else{
                session.setAttribute(JSPHandler.SESSION_MESSAGING, "Data gagal tersimpan");
            }
            
            response.sendRedirect(JSPHandler.generateUrl(request, "kategori-barang", "edit", "id="+oid));
            return;
            
        }else if(action.equals("save")){
            long oid = 0;
            KategoriBarang data = new KategoriBarang();
            data.setKode(JSPHandler.requestString(request, DbKategoriBarang.COL_KODE));
            data.setNama(JSPHandler.requestString(request, DbKategoriBarang.COL_NAMA));
            
            try {
                oid = DbKategoriBarang.save(data);
            } catch (Exception e) {
                System.out.println("err_insert_controller:" + e.toString());
            }
            
            /* redirect setelah simpan */
            response.sendRedirect(JSPHandler.generateUrl(request, "kategori-barang", "edit", "id="+oid));
            return;
            
        }else if(action.equals("delete")){
            response.setContentType("application/json;charset=UTF-8");
            
            long id = JSPHandler.requestLong(request, "id");
            
            KategoriBarang kategoriBarang = new KategoriBarang();
            try{
                kategoriBarang = DbKategoriBarang.findById(id);
            }catch(Exception e){
                System.out.println("err_delete_controller: findById " + e.toString());
            }
            
            if(kategoriBarang.getKategoriBarangId()!=0){
                boolean success = DbKategoriBarang.delete(kategoriBarang);
                if(success){
                    out.println(JSPHandler.generateJsonMessage(0, "Data telah terhapus"));
                }else{
                    out.println(JSPHandler.generateJsonMessage(0, "Data gagal dihapus"));
                }
            }else{
                out.println(JSPHandler.generateJsonMessage(0, "Data gagal dihapus"));
            }
            
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
