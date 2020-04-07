/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.peramalan.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.peramalan.config.MainConfig;
import java.util.HashMap;
import java.util.Map;
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
    
    /* session section */
    public static final String SESSION_USER_OBJECT = "_USER_OBJ";
    public static final String SESSION_USER_ROLE = "_USER_ROLE";
    public static final String SESSION_MESSAGING = "_MESSAGING_SESSION";
    
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
    
    public static long requestLong(HttpServletRequest request, String requestName){
        long result = 0;
        try{
            result = Long.parseLong(request.getParameter(requestName));
        }catch(Exception e){}
        return result;
    }
    
    public static double requestDouble(HttpServletRequest request, String requestName){
        double result = 0;
        try{
            result = Double.valueOf(request.getParameter(requestName));
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
    
    public static String generateJsonMessage(int code, String message){
        String result = "";
        Map map = new HashMap();
        map.put("code", code);
        map.put("message", message);
        
        try{
            result = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(map);
        }catch(Exception e){}
        
        return result;
    }
}
