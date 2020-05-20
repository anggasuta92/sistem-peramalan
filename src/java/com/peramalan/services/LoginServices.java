/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.peramalan.services;

import com.peramalan.model.master.DbRoleDetail;
import com.peramalan.model.master.RoleDetail;
import com.peramalan.model.master.SystemUser;
import static com.peramalan.services.MenuServices.menuPreffix;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;
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
    
    public static boolean isGranted(HttpServletRequest request, int menu, long userId){
        boolean result = false;
        try {
            HttpSession session = request.getSession(true);
            int sts = Integer.parseInt(session.getAttribute(userId + "_" + MenuServices.menuPreffix+String.valueOf(menu)).toString());
            if(sts==1){
                result = true;
            }
        } catch (Exception e) {
            System.out.println("err_get_role:" + e.toString());
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
