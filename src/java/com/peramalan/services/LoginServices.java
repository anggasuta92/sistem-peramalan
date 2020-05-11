/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.peramalan.services;

import com.peramalan.model.master.DbRoleDetail;
import com.peramalan.model.master.RoleDetail;
import static com.peramalan.services.MenuServices.menuPreffix;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Angga Suta
 */
public class LoginServices {
    
    public static boolean isGranted(HttpServletRequest request, int menu){
        boolean result = false;
        try {
            HttpSession session = request.getSession(true);
            int sts = Integer.parseInt(session.getAttribute(MenuServices.menuPreffix+String.valueOf(menu)).toString());
            if(sts==1){
                result = true;
            }
        } catch (Exception e) {
            System.out.println("err_get_role:" + e.toString());
        }
        
        return result;
    }
    
    public static void setUserPriv(HttpServletRequest request, long roleId){
        try {
            HttpSession session = request.getSession(true);
            Vector<RoleDetail> list = new Vector<>();
            try {
                list = DbRoleDetail.list(DbRoleDetail.COL_ROLE_ID + "='"+ roleId +"'", "", 0, 0);
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            for(RoleDetail roleDetail : list){
                String kodeMenu  = MenuServices.menuPreffix + roleDetail.getKodeMenu();
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
