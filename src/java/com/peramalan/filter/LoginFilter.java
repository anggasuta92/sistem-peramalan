/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.peramalan.filter;

import com.peramalan.services.JSPHandler;
import com.peramalan.services.LoginServices;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author AnggaSuta
 */
@WebFilter(
        urlPatterns = {
            "/home/*",
            "/kategori-barang/*",
            "/barang/*",
            "/penjualan/*",
            "/peramalan/*",
            "/role/*",
            "/user/*"
        }
)
public class LoginFilter implements Filter {

    @Override
    public void init(FilterConfig fc) throws ServletException {
        //
    }

    @Override
    public void doFilter(ServletRequest sr, ServletResponse sr1, FilterChain fc) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) sr;
        HttpServletResponse response = (HttpServletResponse) sr1;
        
        boolean isLogedIn = LoginServices.isLogedIn(request);
        if(isLogedIn){
            fc.doFilter(sr, sr1);
        }else{
            response.sendRedirect(JSPHandler.generateUrl(request, "login", "", ""));
        }
    }

    @Override
    public void destroy() {
        //
    }
    
}
