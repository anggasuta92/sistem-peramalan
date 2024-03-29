/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.peramalan.config;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author OxysystemPC
 */
public class MainConfig {
    
    public static final String APP_NAME = "Sistem Peramalan Penjualan";
    
    /*
    * Database cofiguration
    */
    public static final String DATABASE_DRIVER= "com.mysql.jdbc.Driver";
    public static final String DATABASE_URL= "jdbc:mysql://localhost:3306/inconvet";
    public static final String DATABASE_USER= "root";
    public static final String DATABASE_PASSSWORD= "root";
    
    
    
    /*
    * servlet function
    */
    public static String getRootApplicationUrl(HttpServletRequest request){
        String scheme = request.getHeader("X-Forwarded-Proto");
        
        if(scheme==null || scheme.equalsIgnoreCase("null")){
            scheme = "http://";
        }else{
            scheme += "://";
        }
        return scheme + request.getServerName() + ":" + request.getServerPort() + "" + request.getContextPath() + "";
        
        
        //return request.getServerName() + ":" + request.getServerPort() + "" + request.getContextPath() + "";
        //return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "" + request.getContextPath() + "";
    }
    
    public static String getAssetUrl(HttpServletRequest request){
        return getRootApplicationUrl(request) + "/assets";
    }
}
