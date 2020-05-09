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
public class DbRoleDetail {
    /* table name */
    public static String tableName = "role_detail";
    
    /* colName */
    public static String COL_ROLE_DETAIL_ID = "role_detail_id";
    public static String COL_ROLE_ID = "role_id";
    public static String COL_KODE_MENU = "kode_menu";
    public static String COL_GRANTED = "granted";
    
    public static void fetchObject(ResultSet rs, RoleDetail object) throws SQLException{
        object.setRoleDetailId(rs.getLong(COL_ROLE_DETAIL_ID));
        object.setRoleId(rs.getLong(COL_ROLE_ID));
        object.setKodeMenu(rs.getInt(COL_KODE_MENU));
        object.setGranted(rs.getInt(COL_GRANTED));
    }
    
    public static int count(String where){
        int result = 0;
        Connection conn = null;
        Statement stmt = null;
        
        try{
            if(where.trim().length()>0) where = " where " + where;
            String sql = "select count("+ COL_ROLE_DETAIL_ID +") as total from " + tableName + where;
            
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
                RoleDetail object = new RoleDetail();
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
    
    public static boolean delete(RoleDetail data){
        boolean result = false;
        String sql = "delete from "+ tableName +" where " + COL_ROLE_ID + "=?";
        
        if(data.getRoleDetailId()==0){
            return false;
        }
        
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DbConnection.getConnection();
            ps = conn.prepareStatement(sql);
            
            ps.setLong(1, data.getRoleDetailId());
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
    
    public static RoleDetail findById(long id){
        RoleDetail result = new RoleDetail();
        
        Connection conn = null;
        PreparedStatement stmt = null;
        try{
            String sql = "select * from " + tableName + " where " + COL_ROLE_DETAIL_ID + "=?";
            
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
    
    public static long save(RoleDetail data){
        long result = 0;
        String sql = "insert into "+tableName+" ("+COL_ROLE_DETAIL_ID+","+COL_ROLE_ID+","+COL_KODE_MENU+","+COL_GRANTED+") values (?,?,?,?)";
        
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DbConnection.getConnection();
            ps = conn.prepareStatement(sql);
            
            data.setRoleDetailId(OIDGenerator.generateOID());
            ps.setLong(1, data.getRoleDetailId());
            ps.setLong(2, data.getRoleId());
            ps.setInt(3, data.getKodeMenu());
            ps.setInt(4, data.getGranted());
            ps.execute();
            
            result = data.getRoleDetailId();
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
    
    public static long update(RoleDetail data){
        long result = 0;
        String sql = "update "+tableName+" set "+COL_ROLE_ID+"=?,"+COL_KODE_MENU+"=?,"+COL_GRANTED+"=? where "+ COL_ROLE_DETAIL_ID + "=?";
        
        if(data.getRoleDetailId()==0){
            return 0;
        }
        
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DbConnection.getConnection();
            ps = conn.prepareStatement(sql);
            
            ps.setLong(1, data.getRoleId());
            ps.setInt(2, data.getKodeMenu());
            ps.setInt(3, data.getGranted());
            ps.setLong(4, data.getRoleDetailId());
            ps.execute();
            
            result = data.getRoleDetailId();
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
