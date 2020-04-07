/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.peramalan.services;

import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

/**
 *
 * @author Angga Suta
 */
public class TransaksiService {
    /* array periode */
    private static int startYear = 2018;
    
    public static String[] periodeBulan = {
        "",
        "Januari",
        "Februari",
        "Maret",
        "April",
        "Mei",
        "Juni",
        "Juli",
        "Agustus",
        "September",
        "Oktober",
        "Nopember",
        "Desember"};
    
    public static String[] periodeTahun(){
        
        Calendar calendar = Calendar.getInstance();
        int size = (calendar.get(Calendar.YEAR) - startYear)+1;
        String[] result = new String[size];
        
        for(int i = 0; i < size; i++){
            result[i] = String.valueOf(startYear + i);
        }
        
        return result;
    }
}
