/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.peramalan.model.transaksi;

import com.peramalan.conn.DbConnection;
import com.peramalan.model.OIDGenerator;
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
public class DbPeramalan {
        /* table name */
    private static String tableName = "peramalan";
    
    /* colName */
    public static String COL_PERAMALAN_ID = "peramalan_id";
    public static String COL_TAHUN = "tahun";
    public static String COL_BULAN = "bulan";
    public static String COL_BARANG_ID = "barang_id";
    public static String COL_ALPHA = "alpha";
    public static String COL_SMOOTHING_SINGLE = "smoothing_single";
    public static String COL_SMOOTHING_DOUBLE = "smoothing_double";
    public static String COL_NILAI_A = "nilai_a";
    public static String COL_NILAI_B = "nilai_b";
    public static String COL_PERAMALAN = "peramalan";
    
    public DbPeramalan (){
    }
    
    public DbPeramalan (String tableName){
        this.tableName = tableName;
    }
    
    public static void fetchObject(ResultSet rs, Peramalan object) throws SQLException{
        object.setPeramalan(rs.getLong(COL_PERAMALAN_ID));
        object.setTahun(rs.getInt(COL_TAHUN));
        object.setBulan(rs.getInt(COL_BULAN));
        
        Barang barang = new Barang();
        try{
            barang = DbBarang.findById(rs.getLong(COL_BARANG_ID));
        }catch(Exception e){}
        object.setBarang(barang);
        object.setAlpha(rs.getDouble(COL_ALPHA));
        object.setSmoothingSingle(rs.getDouble(COL_SMOOTHING_SINGLE));
        object.setSmoothingDouble(rs.getDouble(COL_SMOOTHING_DOUBLE));
        object.setNilaiA(rs.getDouble(COL_NILAI_A));
        object.setNilaiB(rs.getDouble(COL_NILAI_B));
        object.setPeramalan(rs.getDouble(COL_PERAMALAN));
    }
        
    public static int count(String where){
        int result = 0;
        Connection conn = null;
        Statement stmt = null;
        
        try{
            if(where.trim().length()>0) where = " where " + where;
            String sql = "select count("+ COL_PERAMALAN_ID +") as total from " + tableName + where;
            
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
                Peramalan object = new Peramalan();
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
    
    public static long save(Peramalan data){
        long result = 0;
        String sql = "INSERT INTO "+ tableName +" ("+ COL_PERAMALAN_ID +", "+ COL_TAHUN +", "+ COL_BULAN +", "+ COL_BARANG_ID +", "
                + ""+ COL_ALPHA +", "+ COL_SMOOTHING_SINGLE +", "+ COL_SMOOTHING_DOUBLE +", "+ COL_NILAI_A +", "+ COL_NILAI_B +", "+ COL_PERAMALAN +")"
                + " VALUES (?,?,?,?,?,?,?,?,?,?)";
        
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DbConnection.getConnection();
            ps = conn.prepareStatement(sql);
            
            data.setPeramalanId(OIDGenerator.generateOID());
            ps.setLong(1, data.getPeramalanId());
            ps.setInt(2, data.getTahun());
            ps.setInt(3, data.getBulan());
            ps.setLong(4, data.getBarang().getBarangId());
            ps.setDouble(5, data.getAlpha());
            ps.setDouble(6, data.getSmoothingSingle());
            ps.setDouble(7, data.getSmoothingDouble());
            ps.setDouble(8, data.getNilaiA());
            ps.setDouble(9, data.getNilaiB());
            ps.setDouble(10, data.getPeramalan());
            
            ps.execute();
            
            result = data.getPeramalanId();
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
    
    public static long update(Peramalan data){
        long result = 0;
        String sql = "UPDATE peramalan SET tahun=?, bulan=?, barang_id=?, alpha=?, smoothing_single=?, "
                + "smoothing_double=?, nilai_a=?, nilai_b=?, peramalan=? WHERE peramalan_id=?";
        
        if(data.getPeramalanId()==0){
            return 0;
        }
        
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DbConnection.getConnection();
            ps = conn.prepareStatement(sql);
            
            ps.setInt(1, data.getTahun());
            ps.setInt(2, data.getBulan());
            ps.setLong(3, data.getBarang().getBarangId());
            ps.setDouble(4, data.getAlpha());
            ps.setDouble(5, data.getSmoothingSingle());
            ps.setDouble(6, data.getSmoothingDouble());
            ps.setDouble(7, data.getNilaiA());
            ps.setDouble(8, data.getNilaiB());
            ps.setDouble(9, data.getPeramalan());
            ps.setLong(10, data.getPeramalanId());
            ps.execute();
            
            result = data.getPeramalanId();
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
