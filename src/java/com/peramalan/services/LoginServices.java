/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.peramalan.services;

import com.peramalan.model.master.DbRoleDetail;
import com.peramalan.model.master.RoleDetail;
import com.peramalan.model.master.Pengguna;
import static com.peramalan.services.MenuServices.menuPreffix;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.bind.DatatypeConverter;

/**
 *
 * @author Angga Suta
 */
public class LoginServices {
    
    public static final String LOGIN_STATUS = "login_status";
    public static final int LOGIN_STATUS_TRUE = 1;
    public static final int LOGIN_STATUS_FALSE = 0;
    public static final String LOGIN_USER_ID = "user_id";
    
    public static final String PERUSAHAN_NAMA = "perusahaan_nama";
    public static final String PERUSAHAN_ALAMAT = "perusahaan_alamat";
    
    public static void removeAllLoginInformation(HttpSession session){
        if(session.getAttribute(LOGIN_STATUS)!=null){
            session.removeAttribute(LOGIN_STATUS);
        }

        if(session.getAttribute(LOGIN_USER_ID)!=null){
            session.removeAttribute(LOGIN_USER_ID);
        }
    }
    
    public static String generateMD5(String str){
        String result = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            byte[] digest = md.digest();
            result = DatatypeConverter.printHexBinary(digest).toString().toLowerCase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return result;
    }
    
    public static boolean isLogedIn(HttpServletRequest request){
        boolean result = false;
        HttpSession session = request.getSession();
        
        int sts = 0;
        if(session.getAttribute(LOGIN_STATUS)!=null){
            sts = Integer.parseInt(session.getAttribute(LOGIN_STATUS).toString());
        }
        
        if(sts==1){
            result = true;
        }
        
        return result;
    }
    
    public static boolean isGrantedByUrl(HttpServletRequest request){
        boolean result = false;
        
        long userId = Long.parseLong(request.getSession().getAttribute(LoginServices.LOGIN_USER_ID).toString());

        String url = ((HttpServletRequest) request).getRequestURL().toString();
        String baseURL = url.substring(0, url.length() - request.getRequestURI().length()) + request.getContextPath() + "/";
        String queryString = ((HttpServletRequest)request).getQueryString();        
        String urlName = url.replaceAll(baseURL, "");
        String action = getTokenValue(queryString, JSPHandler.PAGE_QUERY_REQUEST_PREFFIX);
        
        switch(urlName.trim()){
            case "home":
                result = true;
                break;
            case "kategori-barang":
                result = LoginServices.isGranted(request, MenuServices.MENU_KATEGORI_BARANG, userId);
                break;
            case "barang":
                result = LoginServices.isGranted(request, MenuServices.MENU_BARANG, userId);
                break;
            case "penjualan":
                result = LoginServices.isGranted(request, MenuServices.MENU_PENJUALAN, userId);
                break;
            case "peramalan":
                if(action.equals("arsip")){
                    result = LoginServices.isGranted(request, MenuServices.MENU_ARSIP_PERAMALAN, userId);
                }else{
                    result = LoginServices.isGranted(request, MenuServices.MENU_PERAMALAN, userId);
                }
                break;
            case "role":
                result = LoginServices.isGranted(request, MenuServices.MENU_ADMINISTRATOR_ROLE_PENGGUNA, userId);
                break;
            case "user":
                result = LoginServices.isGranted(request, MenuServices.MENU_ADMINISTRATOR_DATA_PENGGUNA, userId);
                break;
            case "perusahaan":
                result = LoginServices.isGranted(request, MenuServices.MENU_ADMINISTRATOR_INFO_PERUSAHAAN, userId);
                break;
            default:
                break;
        }
        
        return result;
    }
    
    public static String getTokenValue(String str, String strKey){
        String result = "";
        try{
            StringTokenizer stringTokenizer = new StringTokenizer(str, "&");
            while (stringTokenizer.hasMoreTokens()) {
                String strx = stringTokenizer.nextToken();
                StringTokenizer st = new StringTokenizer(strx, "=");
                while (st.hasMoreTokens()) {                    
                    String key = st.nextToken();
                    if(key.trim().equals(strKey)){
                        result = st.nextToken();
                    }
                }
            }
        }catch(Exception e){}
        return result;
    }
    
    public static boolean isGranted(HttpServletRequest request, int menu, long userId){
        boolean result = false;
        try {
            HttpSession session = request.getSession(true);
            int sts = 0;
            try{
                sts = Integer.parseInt(session.getAttribute(userId + "_" + MenuServices.menuPreffix+String.valueOf(menu)).toString());
            }catch(Exception e){}
            if(sts==1){
                result = true;
            }
        } catch (Exception e) {
            System.out.println("err_get_role:" + e.toString());
            e.printStackTrace();
        }
        
        return result;
    }
    
    public static void setUserPriv(HttpServletRequest request, long roleId, long userId){
        try {
            HttpSession session = request.getSession(true);
            Vector<RoleDetail> list = new Vector<>();
            try {
                list = DbRoleDetail.list(DbRoleDetail.COL_ROLE_ID + "='"+ roleId +"'", "", 0, 0);
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            for(RoleDetail roleDetail : list){
                String kodeMenu  = userId +"_" + MenuServices.menuPreffix + roleDetail.getKodeMenu();
                if(session.getAttribute(kodeMenu)!=null){
                    session.removeAttribute(kodeMenu);
                }
                session.setAttribute(kodeMenu, roleDetail.getGranted());
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
