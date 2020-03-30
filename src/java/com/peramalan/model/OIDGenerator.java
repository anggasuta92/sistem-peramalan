/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.peramalan.model;

import java.util.Date;

/**
 *
 * @author Angga Suta
 */
public class OIDGenerator {

    private static int appIdx = 1;
    static long lastOID = 0;

    synchronized public static long generateOIDx() {
        Date dateGenerated = new Date();
        long oid = dateGenerated.getTime() + (0x0100000000000000L * appIdx);
        while (lastOID == oid) {
            try {
                Thread.sleep(1);
            } catch (Exception e) {}
            
            dateGenerated = new Date();
            oid = dateGenerated.getTime() + (0x0100000000000000L * appIdx);
        }
        lastOID = oid;
        return oid;
    }
    
    synchronized public static long generateOID() {
        Date dateGenerated = new Date();
        long oid = dateGenerated.getTime() + (0x01000000000000L * appIdx);
        while (lastOID == oid) {
            try {
                Thread.sleep(1);
            } catch (Exception e) {}
            
            dateGenerated = new Date();
            oid = dateGenerated.getTime() + (0x01000000000000L * appIdx);
        }
        lastOID = oid;
        return oid;
    }
    
}
