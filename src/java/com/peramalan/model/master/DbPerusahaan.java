/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.peramalan.model.master;

import com.peramalan.conn.DbConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

/**
 *
 * @author AnggaSuta
 */
public class DbPerusahaan {
    /* table name */
    public static String tableName = "perusahaan";
    
    /* colName */
    public static String COL_PERUSAHAAN_ID = "perusahaan_id";
    public static String COL_NAMA = "nama";
    public static String COL_ALAMAT = "alamat";
    public static String COL_TELEPON = "telepon";
    public static String COL_NAMA_PEMILIK = "nama_pemilik";
    
    public static void fetchObject(ResultSet rs, Perusahaan object) throws SQLException{
        object.setPerusahaanId(rs.getLong(COL_PERUSAHAAN_ID));
        object.setNama(rs.getString(COL_NAMA));
        object.setAlamat(rs.getString(COL_ALAMAT));
        object.setTelepon(rs.getString(COL_TELEPON));
        object.setNamaPemilik(rs.getString(COL_NAMA_PEMILIK));
    }
    
    public static Perusahaan getPerusahaan(){
        Perusahaan perusahaan = new Perusahaan();
        
        Vector listPerusahaan = new Vector();
        try {
            listPerusahaan = list("", "", 0, 1);
            if(listPerusahaan!=null && listPerusahaan.size()>0){
                perusahaan = (Perusahaan) listPerusahaan.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return perusahaan;
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
                Perusahaan object = new Perusahaan();
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
    
    public static long update(Perusahaan data){
        long result = 0;
        String sql = "update perusahaan set "+ COL_NAMA +"=?, "+ COL_ALAMAT +"=?, "
                + ""+ COL_TELEPON +"=?, "+COL_NAMA_PEMILIK+"=? where "+ COL_PERUSAHAAN_ID +"=?";
        
        if(data.getPerusahaanId()==0){
            return 0;
        }
        
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DbConnection.getConnection();
            ps = conn.prepareStatement(sql);
            
            ps.setString(1, data.getNama());
            ps.setString(2, data.getAlamat());
            ps.setString(3, data.getTelepon());
            ps.setString(4, data.getNamaPemilik());
            ps.setLong(5, data.getPerusahaanId());
            ps.execute();
            
            result = data.getPerusahaanId();
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
