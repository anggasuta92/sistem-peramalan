/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.peramalan.conn;

import com.peramalan.config.MainConfig;
import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author OxysystemPC
 */
public class DbConnection {
    public static Connection getConnection() throws Exception{
        Class.forName(MainConfig.DATABASE_DRIVER);
        return DriverManager.getConnection(MainConfig.DATABASE_URL, MainConfig.DATABASE_USER, MainConfig.DATABASE_PASSSWORD);
    }
}
