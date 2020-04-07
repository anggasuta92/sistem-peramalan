/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.peramalan.model.transaksi;

import com.peramalan.conn.DbConnection;
import com.peramalan.model.master.Barang;
import com.peramalan.model.master.DbBarang;
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
public class DbPenjualan {
    /* table name */
    private static String tableName = "penjualan";
    
    /* colName */
    public static String COL_PENJUALAN_ID = "penjualan_id";
    public static String COL_TAHUN = "tahun";
    public static String COL_BULAN = "bulan";
    public static String COL_BARANG_ID = "barang_id";
    public static String COL_QTY = "qty";
    
    
    public static void fetchObject(ResultSet rs, Penjualan object) throws SQLException{
        object.setPenjualanId(rs.getLong(COL_PENJUALAN_ID));
        object.setTahun(rs.getInt(COL_TAHUN));
        object.setBulan(rs.getInt(COL_BULAN));
        
        Barang barang = new Barang();
        try{
            barang = DbBarang.findById(rs.getLong(COL_BARANG_ID));
        }catch(Exception e){}
        object.setBarang(barang);
        
        object.setQty(rs.getDouble(COL_QTY));
    }
    
    public static int count(String where){
        int result = 0;
        Connection conn = null;
        Statement stmt = null;
        
        try{
            if(where.trim().length()>0) where = " where " + where;
            String sql = "select count("+ COL_PENJUALAN_ID +") as total from " + tableName + where;
            
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
                Penjualan object = new Penjualan();
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
    
    public static Vector listPenjualanJoinBarang(String whereBarang, int tahun, int bulan, String orderBy, int limitStart, int limitEnd){
        Vector result = new Vector();
        String limit = "";
        
        if(whereBarang.trim().length()>0) whereBarang = " where " + whereBarang;
        if(orderBy.trim().length()>0) orderBy = " order by " + orderBy;
        if(limitStart!=0 || limitEnd!=0){
            limit = " limit " + limitStart + "," + limitEnd;
        }
        
        Connection conn = null;
        Statement stmt = null;
        try{
            String sql = "select * from " + DbBarang.tableName + whereBarang + orderBy + limit;
            
            conn = DbConnection.getConnection();
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                Penjualan penjualan = findByPeriode(rs.getLong(COL_BARANG_ID), tahun, bulan);
                Penjualan object = new Penjualan();
                object.setPenjualanId(penjualan.getPenjualanId());
                object.setTahun(tahun);
                object.setBulan(bulan);

                Barang barang = new Barang();
                try{
                    barang = DbBarang.findById(rs.getLong(COL_BARANG_ID));
                }catch(Exception e){}
                object.setBarang(barang);

                object.setQty(penjualan.getQty());
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
    
    public static Penjualan findByPeriode(long barangId, int tahun, int bulan){
        Penjualan result = new Penjualan();
        
        String sql = "select * from penjualan where barang_id=? and tahun=? and bulan=?";
        
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = DbConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, barangId);
            stmt.setInt(2, tahun);
            stmt.setInt(3, bulan);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                fetchObject(rs, result);
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("err_find_penjualan_by_periode:" + e.toString());
        } finally {
            try {
                if(stmt!=null){
                    stmt.close();
                }
                
                if(conn!=null){
                    conn.close();
                }
            } catch (Exception e) {
            }
        }
        
        return result;
    }
}
