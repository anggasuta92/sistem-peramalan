/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.peramalan.model.master;

import com.peramalan.conn.DbConnection;
import com.peramalan.model.OIDGenerator;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

/**
 *
 * @author Angga Suta
 */
public class DbKategoriBarang {
    
    /* table name */
    private static String tableName = "kategori_barang";
    
    /* colName */
    public static String COL_KATEGORI_BARANG_ID = "kategori_barang_id";
    public static String COL_KODE = "kode";
    public static String COL_NAMA = "nama";
    
    /* crud */
    public static boolean delete(KategoriBarang data){
        boolean result = false;
        String sql = "delete from kategori_barang where " + COL_KATEGORI_BARANG_ID + "=?";
        
        if(data.getKategoriBarangId()==0){
            return false;
        }
        
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DbConnection.getConnection();
            ps = conn.prepareStatement(sql);
            
            ps.setLong(1, data.getKategoriBarangId());
            ps.execute();
            
            result = true;
        } catch (Exception e) {
            System.out.println("err_delete_data: " + e.toString() + "/" + e.toString());
        } finally {
            if(ps!=null){
                try{
                    ps.close();
                }catch(Exception e){}
            }
            if(conn!=null){
                try{
                    conn.close();
                }catch(Exception e){}
            }
        }
        return result;
    }
    
    public static long update(KategoriBarang data){
        long result = 0;
        String sql = "update kategori_barang set "+ COL_KODE +"=?, "+ COL_NAMA +"=? where " + COL_KATEGORI_BARANG_ID + "=?";
        
        if(data.getKategoriBarangId()==0){
            return 0;
        }
        
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DbConnection.getConnection();
            ps = conn.prepareStatement(sql);
            
            ps.setString(1, data.getKode());
            ps.setString(2, data.getNama());
            ps.setLong(3, data.getKategoriBarangId());
            ps.execute();
            
            result = data.getKategoriBarangId();
        } catch (Exception e) {
            System.out.println("err_save_data: " + e.toString() + "/" + e.toString());
        } finally {
            if(ps!=null){
                try{
                    ps.close();
                }catch(Exception e){}
            }
            if(conn!=null){
                try{
                    conn.close();
                }catch(Exception e){}
            }
        }
        return result;
    }
    
    public static long save(KategoriBarang data){
        long result = 0;
        String sql = "insert into kategori_barang ("+ COL_KATEGORI_BARANG_ID +", "+ COL_KODE +", "+ COL_NAMA +") values (?,?,?)";
        
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DbConnection.getConnection();
            ps = conn.prepareStatement(sql);
            
            data.setKategoriBarangId(OIDGenerator.generateOID());
            ps.setLong(1, data.getKategoriBarangId());
            ps.setString(2, data.getKode());
            ps.setString(3, data.getNama());
            ps.execute();
            
            result = data.getKategoriBarangId();
        } catch (Exception e) {
            System.out.println("err_save_data: " + e.toString() + "/" + e.toString());
        } finally {
            if(ps!=null){
                try{
                    ps.close();
                }catch(Exception e){}
            }
            if(conn!=null){
                try{
                    conn.close();
                }catch(Exception e){}
            }
        }
        return result;
    }
    
    public static void fetchObject(ResultSet rs, KategoriBarang object) throws SQLException{
        object.setKategoriBarangId(rs.getLong(COL_KATEGORI_BARANG_ID));
        object.setKode(rs.getString(COL_KODE));
        object.setNama(rs.getString(COL_NAMA));
    }
    
    
    public static int count(String where){
        int result = 0;
        Connection conn = null;
        Statement stmt = null;
        
        try{
            if(where.trim().length()>0) where = " where " + where;
            String sql = "select count("+ COL_KATEGORI_BARANG_ID +") as total from " + tableName + where;
            
            conn = DbConnection.getConnection();
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                result = rs.getInt("total");
            }
            rs.close();            
        }catch(Exception e){
            System.out.println("err_count_data: " + e.toString() + "/" + e.toString());
        }finally{
            try{if(stmt!=null) stmt.close();}catch(Exception e){}
            try{if(conn!=null) conn.close();}catch(Exception e){}
        }
        return result;
    }
    
    public static Vector list(String where, String orderBy, int limitStart, int limitEnd){
        Vector result = new Vector();
        String limit = "";
        
        if(where.trim().length()>0) where = " where " + where;
        if(orderBy.trim().length()>0) orderBy = " order by " + orderBy;
        if(limitStart!=0 || limitEnd!=0){
            limit = " limit " + limitStart + "," + limitEnd;
        }
        
        Connection conn = null;
        Statement stmt = null;
        try{
            String sql = "select * from " + tableName + where + orderBy + limit;
            
            conn = DbConnection.getConnection();
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                KategoriBarang object = new KategoriBarang();
                fetchObject(rs, object);
                result.add(object);
            }
            rs.close();
        }catch(Exception e){
            System.out.println("err_select_data: " + e.toString() + "/" + e.toString());
        }finally{
            try{if(stmt!=null) stmt.close();}catch(Exception e){}
            try{if(conn!=null) conn.close();}catch(Exception e){}
        }
        
        return result;
    }
    
    public static KategoriBarang findById(long id){
        KategoriBarang result = new KategoriBarang();
        
        Connection conn = null;
        PreparedStatement stmt = null;
        try{
            String sql = "select * from " + tableName + " where " + COL_KATEGORI_BARANG_ID + "=?";
            
            conn = DbConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            System.out.println("sql: " + stmt.toString());
            
            while(rs.next()){
                fetchObject(rs, result);
            }
            rs.close();
        }catch(Exception e){
            System.out.println("err_select_data: " + e.toString() + "/" + e.toString());
        }finally{
            try{if(stmt!=null) stmt.close();}catch(Exception e){}
            try{if(conn!=null) conn.close();}catch(Exception e){}
        }
        
        return result;
    }
}
