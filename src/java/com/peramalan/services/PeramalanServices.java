/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.peramalan.services;

import com.peramalan.model.OIDGenerator;
import com.peramalan.model.transaksi.DbPenjualan;
import com.peramalan.model.transaksi.DbPeramalan;
import com.peramalan.model.transaksi.Penjualan;
import com.peramalan.model.transaksi.Peramalan;
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
                    Peramalan tmp = new Peramalan();
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
                Peramalan peramalan = (Peramalan) listTmpPeramalan.get(i);
                System.out.println("->" + peramalan.getPeramalanId() + "; " + peramalan.getTahun() + "-" + peramalan.getBulan() + "; "+ peramalan.getAlpha() + "; " + peramalan.getPeramalan());
            }
        }
        
        
        return result;
    }
    
    public static Peramalan hitungPeramalan(int tahun, int bulan, long barangId, double bobot){
        Peramalan result = new Peramalan();
        
        /* list penjualan */
        String wherePenjualan = "("+ DbPenjualan.COL_TAHUN +"*12)+"+ DbPenjualan.COL_BULAN +" <= ("+ ((tahun*12)+bulan) +") and " + DbPenjualan.COL_BARANG_ID + "='"+ barangId +"'";
        Vector listPenjualan = DbPenjualan.list(wherePenjualan, "", 0, 0);
        
        return result;
    }
}
