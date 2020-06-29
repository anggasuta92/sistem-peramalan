/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.peramalan.model.transaksi;

import com.peramalan.conn.DbConnection;
import com.peramalan.model.OIDGenerator;
import com.peramalan.services.JSPHandler;
import com.peramalan.services.TransaksiService;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Vector;

/**
 *
 * @author AnggaSuta
 */
public class DbPeramalan {
    /* table name */
    private static String tableName = "peramalan";
    
    /* colName */
    public static String COL_PERAMALAN_ID = "peramalan_id";
    public static String COL_START_TAHUN = "start_tahun";
    public static String COL_START_BULAN = "start_bulan";
    public static String COL_END_TAHUN = "end_tahun";
    public static String COL_END_BULAN = "end_bulan";
    public static String COL_ALPHA_TERBAIK = "alpha_terbaik";
    public static String COL_NOMOR = "nomor";
    public static String COL_TANGGAL = "tanggal";
    
    public static void fetchObject(ResultSet rs, Peramalan o) throws SQLException{
        o.setPeramalanId(rs.getLong(COL_PERAMALAN_ID));
        o.setPenjualanTahun(rs.getInt(COL_START_TAHUN));
        o.setPenjualanBulan(rs.getInt(COL_START_BULAN));
        o.setPeramalanTahun(rs.getInt(COL_END_TAHUN));
        o.setPeramalanBulan(rs.getInt(COL_END_BULAN));
        o.setAlphaTerbaik(rs.getDouble(COL_ALPHA_TERBAIK));
        o.setTanggal(rs.getTimestamp(COL_TANGGAL));
        o.setNomor(rs.getString(COL_NOMOR));
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
        String sql = "INSERT INTO " + tableName + " "
                + "("+COL_PERAMALAN_ID+", "+COL_START_BULAN+", "+COL_START_TAHUN+", "+COL_END_BULAN+", "
                +COL_END_TAHUN+", "+COL_ALPHA_TERBAIK+", "+COL_TANGGAL+", "+ COL_NOMOR +") "
                + "values (?,?,?,?,?,?,?,?)";
        
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DbConnection.getConnection();
            ps = conn.prepareStatement(sql);
            
            data.setPeramalanId(OIDGenerator.generateOID());
            ps.setLong(1, data.getPeramalanId());
            ps.setInt(2, data.getPenjualanBulan());
            ps.setInt(3, data.getPenjualanTahun());
            ps.setInt(4, data.getPeramalanBulan());
            ps.setInt(5, data.getPeramalanTahun());
            ps.setDouble(6, data.getAlphaTerbaik());
            ps.setTimestamp(7, new java.sql.Timestamp(new Date().getTime()));
            ps.setString(8, data.getNomor());
            
            ps.execute();
            
            result = data.getPeramalanId();
        } catch (Exception e) {
            e.printStackTrace();
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
        String sql = "update "+ tableName +" set "
                + COL_START_BULAN + "=?, "+COL_START_TAHUN+"=?, "+COL_END_BULAN+"=?, "+ COL_END_TAHUN +"=?, "
                + COL_ALPHA_TERBAIK +"=?, " + COL_TANGGAL + "=?, "+COL_NOMOR+"=? "
                + "where "+ COL_PERAMALAN_ID +"=?";
        
        if(data.getPeramalanId()==0){
            return 0;
        }
        
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DbConnection.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, data.getPenjualanBulan());
            ps.setInt(2, data.getPenjualanTahun());
            ps.setInt(3, data.getPeramalanBulan());
            ps.setInt(4, data.getPeramalanTahun());
            ps.setDouble(5, data.getAlphaTerbaik());
            ps.setTimestamp(6, new java.sql.Timestamp(data.getTanggal().getTime()));
            ps.setString(7, data.getNomor());
            ps.setLong(8, data.getPeramalanId());
            
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
    
    public static String nomorOtomatis(String pattern){
        String result= "";
        
        String where = COL_NOMOR + " like '%"+ pattern +"%'";
        Vector<Peramalan> listPeramalan = new Vector<>();
        try {
            listPeramalan = DbPeramalan.list("", COL_TANGGAL + " desc", 0, 1);
        } catch (Exception e) {
        }
        
        int number = 0;
        if(listPeramalan.size()>0){
            Peramalan peramalan = (Peramalan) listPeramalan.get(0);
            System.out.println("number: "+ peramalan.getNomor());
            System.out.println("number: "+ peramalan.getNomor().replace(pattern, "").trim());
            number = Integer.parseInt(peramalan.getNomor().replace(pattern, "").trim());
            number +=1;
            for(int i = 0; i < (5 - String.valueOf(number).length()); i++){
                result += "0";
            }
            result = pattern + result + number;
        }else{
            result = pattern + "00001";
        }
        
        return result;
    }
    
    public static Peramalan findById(long id){
        Peramalan result = new Peramalan();
        
        Connection conn = null;
        PreparedStatement stmt = null;
        try{
            String sql = "select * from " + tableName + " where " + COL_PERAMALAN_ID + "=?";
            
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
    
}
