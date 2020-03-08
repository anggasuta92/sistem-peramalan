/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.peramalan.services;

import com.peramalan.config.MainConfig;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author OxysystemPC
 */
public class JSPHandler {
    /*
    * Servlet info
    */
    public static final String PAGE_QUERY_REQUEST_PREFFIX = "xdo";
    
    public static final int ACTION_INDEX = 0;
    public static final int ACTION_ADD = 1;
    public static final int ACTION_SAVE = 2;
    public static final int ACTION_CONFIRM = 3;
    public static final int ACTION_DELETE = 4;
    public static final int ACTION_PROCESS = 5;
    public static final int ACTION_OK = 6;
    public static final int ACTION_CANCEL = 7;
    
    public static final String USER_SESS_OBJ = "_USER_OBJ";
    public static final String USER_SESS_ROLE = "_USER_ROLE";
    
    public static String generateUrl(HttpServletRequest request, String servletName,  int actionCode, String queries){
        String result = MainConfig.getRootApplicationUrl(request) + "/" + servletName; 
        
        if(actionCode!=0){
            result += "?" + JSPHandler.PAGE_QUERY_REQUEST_PREFFIX + "=" + actionCode;
        }
        
        if(queries.length()>0 && queries!=null){
            result += "&" + queries;
        }
        return result;
    }
}
