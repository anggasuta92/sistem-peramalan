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
public class DbPeramalanDetail {
    
    /* table name */
    private static String tableName = "peramalan_detail";
    
    /* colName */
    public static String COL_PERAMALAN_DETAIL_ID = "peramalan_detail_id";
    public static String COL_PERAMALAN_ID = "peramalan_id";
    public static String COL_TAHUN = "tahun";
    public static String COL_BULAN = "bulan";
    public static String COL_BARANG_ID = "barang_id";
    public static String COL_ALPHA = "alpha";
    public static String COL_SMOOTHING_SINGLE = "smoothing_single";
    public static String COL_SMOOTHING_DOUBLE = "smoothing_double";
    public static String COL_NILAI_A = "nilai_a";
    public static String COL_NILAI_B = "nilai_b";
    public static String COL_NILAI_M = "nilai_m";
    public static String COL_PERAMALAN = "peramalan";
    public static String COL_PENJUALAN = "penjualan";
    public static String COL_TIPE = "tipe";
    public static String COL_DISARANKAN = "disarankan";
    
    public static final int DETAIL_TIPE_PENJUALAN = 0;
    public static final int DETAIL_TIPE_PERAMALAN = 1;
    
    public static void fetchObject(ResultSet rs, PeramalanDetail object) throws SQLException{
        object.setPeramalanDetailId(rs.getLong(COL_PERAMALAN_DETAIL_ID));
        object.setPeramalanId(rs.getLong(COL_PERAMALAN_ID));
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
        object.setNilaiM(rs.getDouble(COL_NILAI_M));
        object.setPeramalan(rs.getDouble(COL_PERAMALAN));
        object.setPenjualan(rs.getDouble(COL_PENJUALAN));
        object.setTipe(rs.getInt(COL_TIPE));
        object.setDisarankan(rs.getInt(COL_DISARANKAN));
    }
        
    public static int count(String where){
        int result = 0;
        Connection conn = null;
        Statement stmt = null;
        
        try{
            if(where.trim().length()>0) where = " where " + where;
            String sql = "select count("+ COL_PERAMALAN_DETAIL_ID +") as total from " + tableName + where;
            
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
                PeramalanDetail object = new PeramalanDetail();
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
    
    public static long save(PeramalanDetail data){
        long result = 0;
        String sql = "INSERT INTO "+ tableName +" ("+ COL_PERAMALAN_ID +", "+ COL_TAHUN +", "+ COL_BULAN +", "+ COL_BARANG_ID +", "
                + ""+ COL_ALPHA +", "+ COL_SMOOTHING_SINGLE +", "+ COL_SMOOTHING_DOUBLE +", "+ COL_NILAI_A +", "+ COL_NILAI_B +", "+ COL_PERAMALAN +
                ", "+ COL_PERAMALAN_DETAIL_ID +", "+ COL_PENJUALAN +", "+ COL_NILAI_M +", "+ COL_TIPE +", "+ COL_DISARANKAN +")"
                + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DbConnection.getConnection();
            ps = conn.prepareStatement(sql);
            
            data.setPeramalanDetailId(OIDGenerator.generateOID());
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
            ps.setLong(11, data.getPeramalanDetailId());
            ps.setDouble(12, data.getPenjualan());
            ps.setDouble(13, data.getNilaiM());
            ps.setInt(14, data.getTipe());
            ps.setInt(15, data.getDisarankan());
            
            ps.execute();
            
            result = data.getPeramalanDetailId();
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
    
    public static long update(PeramalanDetail data){
        long result = 0;
        String sql = "UPDATE "+ tableName +" SET "+ COL_TAHUN +"=?, "+ COL_BULAN +"=?, "+ COL_BARANG_ID +"=?, "+ COL_ALPHA +"=?, "+ COL_SMOOTHING_SINGLE +"=?, "
                + COL_SMOOTHING_DOUBLE + "=?, "+ COL_NILAI_A +"=?, "+ COL_NILAI_B +"=?, "+ COL_PERAMALAN +"=?, "+ COL_PERAMALAN_ID +"=?, "
                + COL_PENJUALAN +"=?, "+ COL_NILAI_M +"=?, "+ COL_TIPE +"=?, "+ COL_DISARANKAN +"=? WHERE "+ COL_PERAMALAN_DETAIL_ID +"=?";
        
        if(data.getPeramalanDetailId()==0){
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
            ps.setDouble(11, data.getPenjualan());
            ps.setDouble(12, data.getNilaiM());
            ps.setInt(13, data.getTipe());
            ps.setInt(14, data.getDisarankan());
            ps.setLong(15, data.getPeramalanDetailId());
            ps.execute();
            
            result = data.getPeramalanDetailId();
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
    
    public static int countPeramalanDetailJoinBarang(String paramBarang, 
            int tahun, int bulan, long peramalanId, double alpha, int tipe, int disarankan){
        
        int result = 0;
        
        if(paramBarang.trim().length()>0) paramBarang = " and (b.kode like '%"+ paramBarang +"%' or b.barcode like '%"+ paramBarang +"%' or b.nama like '%"+ paramBarang +"%')";
        
        String where = "";
        if(bulan!=0){
            where += " and bulan='"+ bulan +"'";
        }
        
        if(tahun!=0){
            where += " and bulan='"+ tahun +"'";
        }
        
        if(alpha!=0){
            where += " and alpha='"+ alpha +"' ";
        }
        
        if(tipe!=-1){
            where += " and tipe='"+ tipe +"' ";
        }
        
        if(disarankan==1){
            where += " and pd.disarankan='1' ";
        }
        
        String sql = "select count(pd.peramalan_detail_id) as total from peramalan_detail pd" +
            " inner join barang b on pd.barang_id=b.barang_id" +
            " where pd.peramalan_id='"+ peramalanId +"' " + paramBarang + where;
        
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = DbConnection.getConnection();
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            
            while(rs.next()){
                result += rs.getInt("total");
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try{if(stmt!=null) stmt.close();}catch(Exception e){}
            try{if(conn!=null) conn.close();}catch(Exception e){}
        }
        
        return result;
    }
    
    public static Vector listPeramalanDetailJoinBarang(String paramBarang, 
            int tahun, int bulan, long peramalanId, double alpha, int tipe, int disarankan,
            String orderBy, int limitStart, int limitEnd){
        Vector result = new Vector();
        String limit = "";
        
        if(orderBy.trim().length()>0) orderBy = " order by " + orderBy;
        if(limitStart!=0 || limitEnd!=0){
            limit = " limit " + limitStart + "," + limitEnd;
        }
        
        if(paramBarang.trim().length()>0) paramBarang = " and (b.kode like '%"+ paramBarang +"%' or b.barcode like '%"+ paramBarang +"%' or b.nama like '%"+ paramBarang +"%')";
        
        String where = "";
        if(bulan!=0){
            where += " and bulan='"+ bulan +"' ";
        }
        
        if(tahun!=0){
            where += " and bulan='"+ tahun +"' ";
        }
        
        if(alpha!=0){
            where += " and alpha='"+ alpha +"' ";
        }
        
        if(tipe!=-1){
            where += " and tipe='"+ tipe +"' ";
        }
        
        if(disarankan==1){
            where += " and pd.disarankan='1' ";
        }
        
        String sql = "select pd.* from peramalan_detail pd" +
            " inner join barang b on pd.barang_id=b.barang_id" +
            " where pd.peramalan_id='"+ peramalanId +"' " + paramBarang + where + orderBy + limit;
        
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = DbConnection.getConnection();
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            
            while(rs.next()){
                PeramalanDetail peramalanDetail = new PeramalanDetail();
                fetchObject(rs, peramalanDetail);
                result.add(peramalanDetail);
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try{if(stmt!=null) stmt.close();}catch(Exception e){}
            try{if(conn!=null) conn.close();}catch(Exception e){}
        }
        
        return result;
    }
    
    public static Vector<PeramalanDetail> findByPeramalanBarangTipeAlpha(long peramalanId, long barangId, int tipe, double alpha){
        
        Vector<PeramalanDetail> result = new Vector<>();
        String sql = "select * from " + tableName + " where " + COL_PERAMALAN_ID + "='"+ peramalanId +
                "' and "+ COL_BARANG_ID + "='"+ barangId +"' and "+ COL_TIPE +"='"+ tipe +"' and " + COL_ALPHA + "='"+ alpha +"'";

        System.out.println("sql:" + sql);
        Connection connection = null;
        Statement statement = null;
        
        try {
            connection = DbConnection.getConnection();
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while(rs.next()){
                PeramalanDetail peramalanDetail = new PeramalanDetail();
                fetchObject(rs, peramalanDetail);
                result.add(peramalanDetail);
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(statement!=null) statement.close();
            } catch (Exception e) {
            }
            
            try {
                if(connection!=null) connection.close();
            } catch (Exception e) {
            }
        }
        return result;
    }
}
