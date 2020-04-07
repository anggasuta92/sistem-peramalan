/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.peramalan.model.transaksi;

import com.peramalan.model.master.Barang;

/**
 *
 * @author Angga Suta
 */
public class Penjualan {
    private long penjualanId;
    private int tahun;
    private int bulan;
    private Barang barang;
    private double qty;

    /**
     * @return the penjualanId
     */
    public long getPenjualanId() {
        return penjualanId;
    }

    /**
     * @param penjualanId the penjualanId to set
     */
    public void setPenjualanId(long penjualanId) {
        this.penjualanId = penjualanId;
    }

    /**
     * @return the tahun
     */
    public int getTahun() {
        return tahun;
    }

    /**
     * @param tahun the tahun to set
     */
    public void setTahun(int tahun) {
        this.tahun = tahun;
    }

    /**
     * @return the bulan
     */
    public int getBulan() {
        return bulan;
    }

    /**
     * @param bulan the bulan to set
     */
    public void setBulan(int bulan) {
        this.bulan = bulan;
    }

    /**
     * @return the barang
     */
    public Barang getBarang() {
        return barang;
    }

    /**
     * @param barang the barang to set
     */
    public void setBarang(Barang barang) {
        this.barang = barang;
    }

    /**
     * @return the qty
     */
    public double getQty() {
        return qty;
    }

    /**
     * @param qty the qty to set
     */
    public void setQty(double qty) {
        this.qty = qty;
    }
}
