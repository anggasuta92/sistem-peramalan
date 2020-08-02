/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.peramalan.services;

import com.peramalan.conn.DbConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

/**
 *
 * @author AnggaSuta
 */
public class DashboardServices {
    
    public static int getTotalPenjualan(int tahun, int bulan){
        int result = 0;
        
        String sql = "select sum(qty) as total from penjualan where tahun=? and bulan=? group by bulan, tahun";
        
        Connection conn = null;
        PreparedStatement ps = null;
        
        try {
            conn = DbConnection.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, tahun);
            ps.setInt(2, bulan);
            
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                result += rs.getDouble("total");
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(ps!=null) ps.close();
            } catch (Exception e) {
            }
            
            try {
                if(conn!=null) conn.close();
            } catch (Exception e) {
            }
        }
        
        return result;
    }
    
    public static Vector<Map> getPenjualanTerbaik(){
        Vector<Map> result = new Vector();
        
        String sql = "SELECT " +
                "	b.kode, b.barcode, b.nama, IFNULL(SUM(p.qty), 0) AS terjual " +
                "FROM barang b " +
                "LEFT JOIN penjualan p ON b.barang_id=p.barang_id " +
//                "WHERE p.tahun=2019 " +
                "GROUP BY b.barang_id order by terjual desc limit 0,5";
        
        Connection connection = null;
        Statement statement = null;
        
        try {
            connection = DbConnection.getConnection();
            statement = connection.createStatement();
            ResultSet rs =  statement.executeQuery(sql);
            while(rs.next()){
                
                Map map = new HashMap();
                map.put("kode", rs.getString("kode"));
                map.put("barcode", rs.getString("barcode"));
                map.put("nama", rs.getString("nama"));
                map.put("qty", rs.getDouble("terjual"));
                result.add(map);
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
                if(connection!=null){
                    connection.close();
                }
            } catch (Exception e) {
            }
        }
        
        return result;
    }
    
    public static Vector<Map> chartPenjualanData(){
        Vector<Map> result = new Vector<Map>();
        
        Calendar cal = Calendar.getInstance();
        int currentYear = cal.get(Calendar.YEAR);
        
        for(int i = 1; i <= 12; i++){
            int totalPenjualanCurrent = getTotalPenjualan(currentYear, i);
            int totalPenjualanLast = getTotalPenjualan((currentYear-1), i);
            
            Map detail = new HashMap();
            detail.put("periode", TransaksiService.periodeBulan[i]);
            detail.put("qty_current", totalPenjualanCurrent);
            detail.put("qty_last", totalPenjualanLast);
            result.add(detail);
        }
        
        return result;
    }
}
