/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.peramalan.services;

import com.peramalan.conn.DbConnection;
import com.peramalan.model.master.DbRole;
import com.peramalan.model.master.DbRoleDetail;
import com.peramalan.model.master.DbPengguna;
import com.peramalan.model.master.Pengguna;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Angga Suta
 */
public class MenuServices {
    
    public static String menuPreffix = "menu_";
    public static String[] strMenu = {
        "Data Master > Kategori Barang",
        "Data Master > Barang", 
        "Data Penjualan", 
        "Peramalan > Peramalan",
        "Peramalan > Arsip Peramalan",
        "Administrator > Role Pengguna",
        "Administrator > Data Pengguna",
        "Administrator > Info Perusahaan"
    };
    
    public static int[] strMenuKode = {
        0,
        1, 
        2, 
        3,
        4,
        6,
        7,
        8
    };
    
    public static final int MENU_KATEGORI_BARANG = 0;
    public static final int MENU_BARANG = 1;
    public static final int MENU_PENJUALAN = 2;
    public static final int MENU_PERAMALAN = 3;
    public static final int MENU_ARSIP_PERAMALAN = 4;
    public static final int MENU_ADMINISTRATOR_ROLE_PENGGUNA = 6;
    public static final int MENU_ADMINISTRATOR_DATA_PENGGUNA = 7;
    public static final int MENU_ADMINISTRATOR_INFO_PERUSAHAAN = 8;

    public static void deleteRoleByRoleId(long roleId){
        String sqlDeleteRole = "delete from " + DbRole.tableName + " where " + DbRole.COL_ROLE_ID + "=?";
        String sqlDeleteRoleDetail = "delete from " + DbRoleDetail.tableName + " where " +DbRoleDetail.COL_ROLE_ID + "=?";
        
        Connection conn = null;
        PreparedStatement psRole = null;
        PreparedStatement psRoleDetail = null;
        
        try {
            conn = DbConnection.getConnection();
            conn.setAutoCommit(false);
            
            psRole = conn.prepareStatement(sqlDeleteRole);
            psRole.setLong(1, roleId);
            psRole.execute();
            
            psRoleDetail = conn.prepareStatement(sqlDeleteRoleDetail);
            psRoleDetail.setLong(1, roleId);
            psRoleDetail.execute();
            
            conn.commit();
        } catch (Exception e) {
            try {
                conn.rollback();
                conn.setAutoCommit(true);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                if(psRole!=null){
                    psRole.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            try {
                if(psRoleDetail!=null){
                    psRoleDetail.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            try {
                if(conn!=null){
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    public static void deleteRoleDetailByRoleId(long roleId){
        String sqlDeleteRoleDetail = "delete from " + DbRoleDetail.tableName + " where " +DbRoleDetail.COL_ROLE_ID + "=?";
        
        Connection conn = null;
        PreparedStatement psRoleDetail = null;
        
        try {
            conn = DbConnection.getConnection();
            conn.setAutoCommit(false);
            
            psRoleDetail = conn.prepareStatement(sqlDeleteRoleDetail);
            psRoleDetail.setLong(1, roleId);
            psRoleDetail.execute();
            
            conn.commit();
        } catch (Exception e) {
            try {
                conn.rollback();
                conn.setAutoCommit(true);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            
            try {
                if(psRoleDetail!=null){
                    psRoleDetail.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            try {
                if(conn!=null){
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
