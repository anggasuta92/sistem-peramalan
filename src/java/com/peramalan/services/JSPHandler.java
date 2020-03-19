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
    public static final String PAGE_QUERY_REQUEST_PREFFIX = "_aksi";
    
    public static final int ACTION_INDEX = 0;
    public static final int ACTION_ADD = 1;
    public static final int ACTION_SAVE = 2;
    public static final int ACTION_CONFIRM = 3;
    public static final int ACTION_DELETE = 4;
    public static final int ACTION_PROCESS = 5;
    public static final int ACTION_OK = 6;
    public static final int ACTION_CANCEL = 7;
    
    public static final int ACTION_GET = 8;
    
    public static final String USER_SESS_USER = "_USER_OBJ";
    public static final String USER_SESS_USER_ROLE = "_USER_ROLE";
    
    public static String generateUrl(HttpServletRequest request, String servletName,  String action, String queries){
        String result = MainConfig.getRootApplicationUrl(request) + "/" + servletName; 
        
        if(action.length()!=0 && action!=null){
            result += "?" + JSPHandler.PAGE_QUERY_REQUEST_PREFFIX + "=" + action;
        }
        
        if(queries.length()>0 && queries!=null){
            result += "&" + queries;
        }
        return result;
    }
    
    public static int requestInt(HttpServletRequest request, String requestName){
        int result = 0;
        try{
            result = Integer.parseInt(request.getParameter(requestName));
        }catch(Exception e){}
        return result;
    }
    
    public static String requestString(HttpServletRequest request, String requestName){
        String result = "";
        try{
            result = request.getParameter(requestName);
        }catch(Exception e){}
        return result;
    }
}
