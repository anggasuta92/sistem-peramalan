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
        //return DriverManager.getConnection("jdbc:mysql://if0ck476y7axojpg.cbetxkdyhwsb.us-east-1.rds.amazonaws.com/o7frcrl1ix5i6c0b", "pt08wkn9rccenxg3", "sp77mezpivmmiqvc");
    }
}
