/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.peramalan.model.transaksi;

import java.util.Date;

/**
 *
 * @author AnggaSuta
 */
public class Peramalan {
    private long peramalanId;
    private int penjualan_bulan;
    private int penjualan_tahun;
    private int peramalan_bulan;
    private int peramalan_tahun;
    private double alphaTerbaik;
    private Date tanggal;
    

    /**
     * @return the peramalanId
     */
    public long getPeramalanId() {
        return peramalanId;
    }

    /**
     * @param peramalanId the peramalanId to set
     */
    public void setPeramalanId(long peramalanId) {
        this.peramalanId = peramalanId;
    }

    /**
     * @return the startBulan
     */
    public int getPenjualanBulan() {
        return penjualan_bulan;
    }

    /**
     * @param penjualanBulan the startBulan to set
     */
    public void setPenjualanBulan(int penjualanBulan) {
        this.penjualan_bulan = penjualanBulan;
    }

    /**
     * @return the startTahun
     */
    public int getPenjualanTahun() {
        return penjualan_tahun;
    }

    /**
     * @param penjualanTahun the startTahun to set
     */
    public void setPenjualanTahun(int penjualanTahun) {
        this.penjualan_tahun = penjualanTahun;
    }

    /**
     * @return the endBulan
     */
    public int getPeramalanBulan() {
        return peramalan_bulan;
    }

    /**
     * @param peramalanBulan the endBulan to set
     */
    public void setPeramalanBulan(int peramalanBulan) {
        this.peramalan_bulan = peramalanBulan;
    }

    /**
     * @return the endTahun
     */
    public int getPeramalanTahun() {
        return peramalan_tahun;
    }

    /**
     * @param peramalanTahun the endTahun to set
     */
    public void setPeramalanTahun(int peramalanTahun) {
        this.peramalan_tahun = peramalanTahun;
    }

    /**
     * @return the alphaTerbaik
     */
    public double getAlphaTerbaik() {
        return alphaTerbaik;
    }

    /**
     * @param alphaTerbaik the alphaTerbaik to set
     */
    public void setAlphaTerbaik(double alphaTerbaik) {
        this.alphaTerbaik = alphaTerbaik;
    }

    /**
     * @return the tanggal
     */
    public Date getTanggal() {
        return tanggal;
    }

    /**
     * @param tanggal the tanggal to set
     */
    public void setTanggal(Date tanggal) {
        this.tanggal = tanggal;
    }
    
    
}
