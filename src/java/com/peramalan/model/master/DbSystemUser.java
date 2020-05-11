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
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

/**
 *
 * @author Angga Suta
 */
public class DbSystemUser {
    /* table name */
    public static String tableName = "system_user";
    
    /* colName */
    public static String COL_SYSTEM_USER_ID = "system_user_id";
    public static String COL_NAMA = "nama";
    public static String COL_USERNAME = "username";
    public static String COL_PASSWORD = "password";
    public static String COL_ROLE_ID = "role_id";
    public static String COL_STATUS = "status";
 
    public static void fetchObject(ResultSet rs, SystemUser object) throws SQLException{
        object.setSystemUserId(rs.getLong(COL_SYSTEM_USER_ID));
        object.setNama(rs.getString(COL_NAMA));
        object.setUsername(rs.getString(COL_USERNAME));
        object.setPassword(rs.getString(COL_PASSWORD));
        object.setRoleId(rs.getLong(COL_ROLE_ID));
        object.setStatus(rs.getInt(COL_STATUS));
    }

    public static int count(String where){
        int result = 0;
        Connection conn = null;
        Statement stmt = null;
        
        try{
            if(where.trim().length()>0) where = " where " + where;
            String sql = "select count("+ COL_SYSTEM_USER_ID +") as total from " + tableName + where;
            
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
                SystemUser object = new SystemUser();
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
    
    public static Vector listJoinRole(String where, String orderBy, int limitStart, int limitEnd){
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
                SystemUser object = new SystemUser();
                fetchObject(rs, object);
                
                Role role = new Role();
                try {
                    role = DbRole.findById(object.getRoleId());
                } catch (Exception e) {
                }
                
                Map data = new HashMap();
                data.put("user", object);
                data.put("role", role);
                
                result.add(data);
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
    
    public static boolean delete(SystemUser data){
        boolean result = false;
        String sql = "delete from "+ tableName +" where " + COL_SYSTEM_USER_ID + "=?";
        
        if(data.getSystemUserId()==0){
            return false;
        }
        
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DbConnection.getConnection();
            ps = conn.prepareStatement(sql);
            
            ps.setLong(1, data.getSystemUserId());
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
    
    public static SystemUser findById(long id){
        SystemUser result = new SystemUser();
        
        Connection conn = null;
        PreparedStatement stmt = null;
        try{
            String sql = "select * from " + tableName + " where " + COL_SYSTEM_USER_ID + "=?";
            
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
    
    public static long save(SystemUser data){
        long result = 0;
        String sql = "insert into "+ tableName +" ("+COL_SYSTEM_USER_ID+", "+COL_NAMA+", "
                + ""+COL_USERNAME+", "+COL_PASSWORD+", "+COL_ROLE_ID+", "+COL_STATUS+") values (?,?,?,?,?,?)";

        
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DbConnection.getConnection();
            ps = conn.prepareStatement(sql);
            
            data.setSystemUserId(OIDGenerator.generateOID());
            ps.setLong(1, data.getSystemUserId());
            ps.setString(2, data.getNama());
            ps.setString(3, data.getUsername());
            ps.setString(4,  data.getPassword());
            ps.setLong(5, data.getRoleId());
            ps.setInt(6, data.getStatus());
            ps.execute();
            
            result = data.getSystemUserId();
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
    
    public static long update(SystemUser data){
        long result = 0;
        String sql = "update "+ tableName +" set "+COL_NAMA+"=?, "
                + ""+COL_USERNAME+"=?, "+COL_PASSWORD+"=?, "+COL_ROLE_ID+"=?, "+COL_STATUS+"=? where " + COL_SYSTEM_USER_ID + "=?";
        
        if(data.getSystemUserId()==0){
            return 0;
        }
        
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DbConnection.getConnection();
            ps = conn.prepareStatement(sql);
            
            ps.setString(1, data.getNama());
            ps.setString(2, data.getUsername());
            ps.setString(3, data.getPassword());
            ps.setLong(4, data.getRoleId());
            ps.setInt(5, data.getStatus());
            ps.setLong(6, data.getSystemUserId());
            ps.execute();
            
            result = data.getSystemUserId();
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
