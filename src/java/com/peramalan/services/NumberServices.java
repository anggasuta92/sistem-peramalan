/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.peramalan.services;

import java.text.DecimalFormat;

/**
 *
 * @author Angga Suta
 */
public class NumberServices {
    public static double decimalRounded(double number, int decimalPlace){
        double result = 0;
        
        String strDecimal = "";
        for(int i = 0; i < decimalPlace; i++){
            strDecimal += "#";
        }
        strDecimal = ((strDecimal.length()>0) ? ".":"") + strDecimal;
        
        DecimalFormat df = new DecimalFormat("###"+ strDecimal);
        result = Double.parseDouble(df.format(number));
        
        return result;
    }
}
