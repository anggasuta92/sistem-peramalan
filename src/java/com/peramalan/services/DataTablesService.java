/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.peramalan.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

/**
 *
 * @author Angga Suta
 */
public class DataTablesService {
    
    public static String generateDataTablesData(int currentPage, int recordsTotal, int recordsFiltered, Vector data){
        String result = "";
        
        Map dataMapper = new HashMap();
        dataMapper.put("draw", currentPage);
        dataMapper.put("recordsTotal", recordsTotal);
        dataMapper.put("recordsFiltered", recordsFiltered);
        dataMapper.put("data", data);
        
        try {
            result = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(dataMapper);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return result;
    }
    
}
