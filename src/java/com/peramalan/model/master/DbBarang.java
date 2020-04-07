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
public class DbBarang {
    /* table name */
    public static String tableName = "barang";
    
    /* colName */
    public static String COL_BARANG_ID = "barang_id";
    public static String COL_KATEGORI_BARANG_ID = "kategori_barang_id";
    public static String COL_KODE = "kode";
    public static String COL_BARCODE = "barcode";
    public static String COL_NAMA = "nama";
    public static String COL_SATUAN = "satuan";
    public static String COL_ALPHA = "alpha";
    
    public static void fetchObject(ResultSet rs, Barang object) throws SQLException{
        object.setBarangId(rs.getLong(COL_BARANG_ID));
        
        KategoriBarang kategoriBarang = new KategoriBarang();
        try{
            kategoriBarang = DbKategoriBarang.findById(rs.getLong(COL_KATEGORI_BARANG_ID));
        }catch(Exception e){
            System.out.println("err_fetch_kategori_barang:" + e.toString());
        }
        object.setKategoriBarang(kategoriBarang);
        
        object.setKode(rs.getString(COL_KODE));
        object.setBarcode(rs.getString(COL_BARCODE));
        object.setNama(rs.getString(COL_NAMA));
        object.setSatuan(rs.getString(COL_SATUAN));
        object.setAlpha(rs.getDouble(COL_ALPHA));
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
                Barang object = new Barang();
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
    
    /* crud */
    public static boolean delete(Barang data){
        boolean result = false;
        String sql = "delete from "+ tableName +" where " + COL_BARANG_ID + "=?";
        
        if(data.getBarangId()==0){
            return false;
        }
        
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DbConnection.getConnection();
            ps = conn.prepareStatement(sql);
            
            ps.setLong(1, data.getBarangId());
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
    
    public static Barang findById(long id){
        Barang result = new Barang();
        
        Connection conn = null;
        PreparedStatement stmt = null;
        try{
            String sql = "select * from " + tableName + " where " + COL_BARANG_ID + "=?";
            
            conn = DbConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
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

    public static long save(Barang data){
        long result = 0;
        String sql = "insert into "+tableName+" ("+COL_KATEGORI_BARANG_ID+", "+ COL_KODE +", "+ COL_BARCODE +", "+ COL_NAMA +", "+ COL_SATUAN +", "+ COL_ALPHA +", "+ COL_BARANG_ID +") values (?,?,?,?,?,?,?)";
        
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DbConnection.getConnection();
            ps = conn.prepareStatement(sql);
            
            data.setBarangId(OIDGenerator.generateOID());
            ps.setLong(1, data.getKategoriBarang().getKategoriBarangId());
            ps.setString(2, data.getKode());
            ps.setString(3, data.getBarcode());
            ps.setString(4, data.getNama());
            ps.setString(5, data.getSatuan());
            ps.setDouble(6, data.getAlpha());
            ps.setLong(7, data.getBarangId());
            ps.execute();
            
            result = data.getBarangId();
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
    
    public static long update(Barang data){
        long result = 0;
        String sql = "update "+ tableName +" set "+ COL_KATEGORI_BARANG_ID +"=?, "+ COL_KODE +"=?, "+ COL_BARCODE +"=?, "+ COL_NAMA +"=?, "+ COL_SATUAN +"=?, "+ COL_ALPHA +"=? where " + COL_BARANG_ID + "=?";
        
        if(data.getBarangId()==0){
            return 0;
        }
        
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DbConnection.getConnection();
            ps = conn.prepareStatement(sql);
            
            ps.setLong(1, data.getKategoriBarang().getKategoriBarangId());
            ps.setString(2, data.getKode());
            ps.setString(3, data.getBarcode());
            ps.setString(4, data.getNama());
            ps.setString(5, data.getSatuan());
            ps.setDouble(6, data.getAlpha());
            ps.setLong(7, data.getBarangId());
            ps.execute();
            
            result = data.getBarangId();
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
}
