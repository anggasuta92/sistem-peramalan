/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.peramalan.model.master;

/**
 *
 * @author Angga Suta
 */
public class KategoriBarang {
    private long kategoriBarangId;
    private String kode;
    private String nama;

    /**
     * @return the kategoriBarangId
     */
    public long getKategoriBarangId() {
        return kategoriBarangId;
    }

    /**
     * @param kategoriBarangId the kategoriBarangId to set
     */
    public void setKategoriBarangId(long kategoriBarangId) {
        this.kategoriBarangId = kategoriBarangId;
    }

    /**
     * @return the nama
     */
    public String getNama() {
        return nama;
    }

    /**
     * @param nama the nama to set
     */
    public void setNama(String nama) {
        this.nama = nama;
    }

    /**
     * @return the kode
     */
    public String getKode() {
        return kode;
    }

    /**
     * @param kode the kode to set
     */
    public void setKode(String kode) {
        this.kode = kode;
    }
    
    
}
