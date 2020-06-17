/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.peramalan.services;

import com.peramalan.model.OIDGenerator;
import com.peramalan.model.master.Barang;
import com.peramalan.model.master.DbBarang;
import com.peramalan.model.transaksi.DbPenjualan;
import com.peramalan.model.transaksi.DbPeramalan;
import com.peramalan.model.transaksi.DbPeramalanDetail;
import com.peramalan.model.transaksi.Penjualan;
import com.peramalan.model.transaksi.Peramalan;
import com.peramalan.model.transaksi.PeramalanDetail;
import java.util.Date;
import java.util.Vector;

/**
 *
 * @author Angga Suta
 */
public class PeramalanServices {
    
    public static double tentukanBobot(int tahun, int bulan, long barangId){
        double result = 0;
        
        /* list penjualan */
        String wherePenjualan = "("+ DbPenjualan.COL_TAHUN +"*12)+"+ DbPenjualan.COL_BULAN +" <= ("+ ((tahun*12)+bulan) +") and " + DbPenjualan.COL_BARANG_ID + "='"+ barangId +"'";
        Vector listPenjualan = DbPenjualan.list(wherePenjualan, "", 0, 0);
        
        /* penentuan bobot terbaik antara 0 sampai 1 */
        double prevSmoothSingle = 0;
        double prevSmoothDouble = 0;
        double prevNilaiA = 0;
        double prevNilaiB = 0;
        Vector listTmpPeramalan = new Vector();

        int tahunHitungMAPE = 0;
        int bulanHitungMAPE = 0;
        
        if(listPenjualan!=null && listPenjualan.size()>0){
            
            /* cari periode awal untuk hitung MAPE sampai periode akhir penjualan */    
            for(int a = 1; a<=9; a++){
                double alpha = a / 10.0;

                /* loop data penjualan */
                for(int p = 0; p < listPenjualan.size(); p++){
                    Penjualan penjualan = (Penjualan) listPenjualan.get(p);

                    if(p==0){
                        prevSmoothSingle = penjualan.getQty();
                        prevSmoothDouble = penjualan.getQty();
                    }

                    double smoothSingle = 0;
                    double smoothDouble = 0;
                    double nilaiA = 0;
                    double nilaiB = 0;
                    double peramalan = 0;

                    if(p>0){
                        /* hitung pemulusan I */
                        smoothSingle = (alpha * penjualan.getQty()) + ((1 - alpha) * prevSmoothSingle);
                        smoothSingle = NumberServices.decimalRounded(smoothSingle, 2);

                        /* hitung pemulusan II */
                        smoothDouble = (alpha * smoothSingle) + ((1-alpha) * prevSmoothDouble);
                        smoothDouble = NumberServices.decimalRounded(smoothDouble, 2);

                        /* hitung nilai A */
                        nilaiA = (2 * smoothSingle) - smoothDouble;
                        nilaiA = NumberServices.decimalRounded(nilaiA, 2);

                        /* hitung nilai B */
                        nilaiB = (alpha/(1-alpha)) * (smoothSingle - smoothDouble);
                        nilaiB = NumberServices.decimalRounded(nilaiB, 2);

                        /* hitung peramalan */
                        if(p>1){
                            peramalan = prevNilaiA + (prevNilaiB * 1);
                            peramalan = NumberServices.decimalRounded(peramalan, 2);
                        }                    
                        prevSmoothSingle = smoothSingle;
                        prevSmoothDouble = smoothDouble;
                        prevNilaiA = nilaiA;
                        prevNilaiB = nilaiB;
                    }

                    /* simpan ke table temporary */
                    PeramalanDetail tmp = new PeramalanDetail();
                    tmp.setPeramalanId(OIDGenerator.generateOID());
                    tmp.setTahun(penjualan.getTahun());
                    tmp.setBulan(penjualan.getBulan());
                    tmp.setBarang(penjualan.getBarang());
                    tmp.setAlpha(alpha);
                    tmp.setSmoothingSingle(smoothSingle);
                    tmp.setSmoothingDouble(smoothDouble);
                    tmp.setNilaiA(nilaiA);
                    tmp.setNilaiB(nilaiB);
                    tmp.setPeramalan(peramalan);

                    listTmpPeramalan.add(tmp);

                    //DbPeramalan dbTempPeramalan = new DbPeramalan("tmp_peramalan");
                    //dbTempPeramalan.save(tmp);
                }
            }
        }
        
        /* select lagi dengan error terendah */
        if(listTmpPeramalan!=null && listTmpPeramalan.size()>0){
            for(int i = 0; i < listTmpPeramalan.size(); i++){
                PeramalanDetail peramalan = (PeramalanDetail) listTmpPeramalan.get(i);
                System.out.println("->" + peramalan.getPeramalanId() + "; " + peramalan.getTahun() + "-" + peramalan.getBulan() + "; "+ peramalan.getAlpha() + "; " + peramalan.getPeramalan());
            }
        }
        
        return result;
    }
    
    public static void hitungPeramalan(int penjualanBulan, int penjualanTahun, int peramalanBulan, int peramalanTahun){
        
        Vector<Barang> listBarang = new Vector<Barang>();
        try {
            listBarang = DbBarang.list("", "", 0, 0);
        } catch (Exception e) {
        }
        
        long peramalanId = 0;
        Peramalan peramalan = new Peramalan();
        peramalan.setPeramalanBulan(peramalanBulan);
        peramalan.setPeramalanTahun(peramalanTahun);
        peramalan.setPenjualanBulan(penjualanBulan);
        peramalan.setPenjualanTahun(penjualanTahun);
        peramalan.setTanggal(new Date());
        
        try {
            peramalanId = DbPeramalan.save(peramalan);
        } catch (Exception e) {
        }
        
        if(peramalanId!=0){
            for(Barang barang : listBarang){
                for(int a = 1; a<=9; a++){
                    double alpha = a / 10.0;
                    hitungPeramalanByBarang(penjualanBulan, penjualanTahun, peramalanBulan, peramalanTahun, 
                            alpha, barang.getBarangId(), peramalanId);
                }
            }
        }
    }
    
    public static void hitungPeramalanByBarang(int penjualanBulan, int penjualanTahun, 
            int peramalanBulan, int peramalanTahun, 
            double alpha, long barangId, long peramalanId){
        /* periode awal data peramalan */
        int periodeAwal = penjualanBulan + (penjualanTahun*12);
        int periodeAkhir = peramalanBulan + (peramalanTahun*12);
        
        double prevSmoothSingle = 0;
        double prevSmoothDouble = 0;
        double prevNilaiA = 0;
        double prevNilaiB = 0;
        
        for(int i = periodeAwal; i<=periodeAkhir; i++){
            int bulan = (i%12==0) ? 12:(i%12);
            int tahun = Integer.parseInt(String.valueOf(i/12));
            if(bulan==12){
                tahun = tahun - 1;
            }
            
            Penjualan penjualan = new Penjualan();
            try {
                penjualan = DbPenjualan.findByPeriode(barangId, tahun, bulan);
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            if(i==periodeAwal){
                prevSmoothSingle = penjualan.getQty();
                prevSmoothDouble = penjualan.getQty();
            }
            
            double smoothSingle = 0;
            double smoothDouble = 0;
            double nilaiA = 0;
            double nilaiB = 0;
            double peramalan = 0;

            if(i>periodeAwal){
                /* hitung pemulusan I */
                smoothSingle = (alpha * penjualan.getQty()) + ((1 - alpha) * prevSmoothSingle);
                smoothSingle = NumberServices.decimalRounded(smoothSingle, 2);

                /* hitung pemulusan II */
                smoothDouble = (alpha * smoothSingle) + ((1-alpha) * prevSmoothDouble);
                smoothDouble = NumberServices.decimalRounded(smoothDouble, 2);

                /* hitung nilai A */
                nilaiA = (2 * smoothSingle) - smoothDouble;
                nilaiA = NumberServices.decimalRounded(nilaiA, 2);

                /* hitung nilai B */
                nilaiB = (alpha/(1-alpha)) * (smoothSingle - smoothDouble);
                nilaiB = NumberServices.decimalRounded(nilaiB, 2);

                /* hitung peramalan */
                if(i>(periodeAwal+1)){
                    peramalan = prevNilaiA + (prevNilaiB * 1);
                    peramalan = NumberServices.decimalRounded(peramalan, 2);
                }                    
                prevSmoothSingle = smoothSingle;
                prevSmoothDouble = smoothDouble;
                prevNilaiA = nilaiA;
                prevNilaiB = nilaiB;
            }

            /* simpan ke table temporary */
            PeramalanDetail peramalanDetail = new PeramalanDetail();
            peramalanDetail.setPeramalanId(peramalanId);
            peramalanDetail.setTahun(penjualan.getTahun());
            peramalanDetail.setBulan(penjualan.getBulan());
            peramalanDetail.setBarang(penjualan.getBarang());
            peramalanDetail.setAlpha(alpha);
            peramalanDetail.setSmoothingSingle(smoothSingle);
            peramalanDetail.setSmoothingDouble(smoothDouble);
            peramalanDetail.setNilaiA(nilaiA);
            peramalanDetail.setNilaiB(nilaiB);
            peramalanDetail.setPeramalan(peramalan);
            
            try {
                DbPeramalanDetail.save(peramalanDetail);
            } catch (Exception e) {
            }
            
        }
    }
    
    public static PeramalanDetail hitungPeramalanPerBarang(int tahun, int bulan, long barangId, double bobot){
        PeramalanDetail result = new PeramalanDetail();
        return result;
    }
}
