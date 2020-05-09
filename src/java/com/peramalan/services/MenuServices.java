/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.peramalan.services;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Angga Suta
 */
public class MenuServices {
    
    public static String[] strMenu = {
        "Data Master > Kategori Barang",
        "Data Master > Barang", 
        "Data Penjualan", 
        "Peramalan > Peramalan",
        "Peramalan > Arsip Peramalan",
        "Peramalan > Analisis Peramalan",
        "Administrator > Role Pengguna",
        "Administrator > Data Pengguna"
    };
    public static final int MENU_KATEGORI_BARANG = 0;
    public static final int MENU_BARANG = 1;
    public static final int MENU_PENJUALAN = 2;
    public static final int MENU_PERAMALAN = 3;
    public static final int MENU_ARSIP_PERAMALAN = 4;
    public static final int MENU_ANALISIS_PERAMALAN = 5;
    public static final int MENU_ADMINISTRATOR_ROLE_PENGGUNA = 6;
    public static final int MENU_ADMINISTRATOR_DATA_PENGGUNA = 7;
    
    
    public static boolean isGranted(HttpServletRequest request, int menu){
        boolean result = false;
        
        try {
            HttpSession session = request.getSession(true);
            int sts = Integer.parseInt(session.getAttribute(String.valueOf(menu)).toString());
            if(sts==1){
                result = true;
            }
        } catch (Exception e) {
            System.out.println("err_get_role:" + e.toString());
        }
        
        return result;
    }
}
