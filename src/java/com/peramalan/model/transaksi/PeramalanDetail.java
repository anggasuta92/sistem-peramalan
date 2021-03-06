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
public class PeramalanDetail {
    private long peramalanDetailId;
    private long peramalanId;
    private int tahun;
    private int bulan;
    private Barang barang;
    private double alpha;
    private double smoothingSingle;
    private double smoothingDouble;
    private double nilaiA;
    private double nilaiB;
    private double nilaiM;
    private double peramalan;
    private double penjualan;
    private int tipe;
    private int disarankan;
    
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
     * @return the alpha
     */
    public double getAlpha() {
        return alpha;
    }

    /**
     * @param alpha the alpha to set
     */
    public void setAlpha(double alpha) {
        this.alpha = alpha;
    }

    /**
     * @return the smoothingSingle
     */
    public double getSmoothingSingle() {
        return smoothingSingle;
    }

    /**
     * @param smoothingSingle the smoothingSingle to set
     */
    public void setSmoothingSingle(double smoothingSingle) {
        this.smoothingSingle = smoothingSingle;
    }

    /**
     * @return the smoothingDouble
     */
    public double getSmoothingDouble() {
        return smoothingDouble;
    }

    /**
     * @param smoothingDouble the smoothingDouble to set
     */
    public void setSmoothingDouble(double smoothingDouble) {
        this.smoothingDouble = smoothingDouble;
    }

    /**
     * @return the nilaiA
     */
    public double getNilaiA() {
        return nilaiA;
    }

    /**
     * @param nilaiA the nilaiA to set
     */
    public void setNilaiA(double nilaiA) {
        this.nilaiA = nilaiA;
    }

    /**
     * @return the nilaiB
     */
    public double getNilaiB() {
        return nilaiB;
    }

    /**
     * @param nilaiB the nilaiB to set
     */
    public void setNilaiB(double nilaiB) {
        this.nilaiB = nilaiB;
    }

    /**
     * @return the peramalan
     */
    public double getPeramalan() {
        return peramalan;
    }

    /**
     * @param peramalan the peramalan to set
     */
    public void setPeramalan(double peramalan) {
        this.peramalan = peramalan;
    }

    /**
     * @return the peramalanDetailId
     */
    public long getPeramalanDetailId() {
        return peramalanDetailId;
    }

    /**
     * @param peramalanDetailId the peramalanDetailId to set
     */
    public void setPeramalanDetailId(long peramalanDetailId) {
        this.peramalanDetailId = peramalanDetailId;
    }

    /**
     * @return the penjualan
     */
    public double getPenjualan() {
        return penjualan;
    }

    /**
     * @param penjualan the penjualan to set
     */
    public void setPenjualan(double penjualan) {
        this.penjualan = penjualan;
    }

    /**
     * @return the nilaiM
     */
    public double getNilaiM() {
        return nilaiM;
    }

    /**
     * @param nilaiM the nilaiM to set
     */
    public void setNilaiM(double nilaiM) {
        this.nilaiM = nilaiM;
    }

    /**
     * @return the tipe
     */
    public int getTipe() {
        return tipe;
    }

    /**
     * @param tipe the tipe to set
     */
    public void setTipe(int tipe) {
        this.tipe = tipe;
    }

    /**
     * @return the disarankan
     */
    public int getDisarankan() {
        return disarankan;
    }

    /**
     * @param disarankan the disarankan to set
     */
    public void setDisarankan(int disarankan) {
        this.disarankan = disarankan;
    }
    
    
}
