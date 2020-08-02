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
import java.text.SimpleDateFormat;
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
                }
            }
        }
        
        /* select lagi dengan error terendah */
        if(listTmpPeramalan!=null && listTmpPeramalan.size()>0){
            for(int i = 0; i < listTmpPeramalan.size(); i++){
                PeramalanDetail peramalan = (PeramalanDetail) listTmpPeramalan.get(i);
            }
        }
        
        return result;
    }
    
    public static long hitungPeramalan(int penjualanBulan, int penjualanTahun, int peramalanBulan, int peramalanTahun, long userId){
        long result = 0;
        
        Vector<Barang> listBarang = new Vector<Barang>();
        try {
            listBarang = DbBarang.list("", "", 0, 0);
        } catch (Exception e) {
        }
        
        Peramalan peramalan = new Peramalan();
        peramalan.setPeramalanBulan(peramalanBulan);
        peramalan.setPeramalanTahun(peramalanTahun);
        peramalan.setPenjualanBulan(penjualanBulan);
        peramalan.setPenjualanTahun(penjualanTahun);
        peramalan.setPenggunaId(userId);
        
        SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
        String pattern = format.format(new Date());
        peramalan.setNomor(DbPeramalan.nomorOtomatis(pattern));
        peramalan.setTanggal(new Date());
        
        try {
            result = DbPeramalan.save(peramalan);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        if(result!=0){
            for(Barang barang : listBarang){
                for(int a = 1; a<=9; a++){
                    double alpha = a / 10.0;
                    hitungPeramalanByBarang(penjualanBulan, penjualanTahun, peramalanBulan, peramalanTahun, 
                            alpha, barang.getBarangId(), result);
                }
            }
            
            /* cari alpha terbaik */
            try {
                peramalan = DbPeramalan.findById(peramalan.getPeramalanId());
                menentukanBobotTerbaik(peramalan.getPeramalanId());
//                double alphaTerbaik = tentukanAlphaTerbaik(peramalan.getPeramalanId());
//                peramalan.setAlphaTerbaik(alphaTerbaik);;
//                DbPeramalan.update(peramalan);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        return result;
    }
    
    public static void menentukanBobotTerbaik(long peramalanId){
        
        Vector<Barang> listBarangs = new Vector<>();
        try {
            listBarangs = DbBarang.list("", "", 0, 0);
        } catch (Exception e) {
        }
        
        /* cek semua barang */
        for(Barang barang : listBarangs){
            double result = 0;
            double prevMape = 100;
            
            for(int a = 1; a<=9; a++){
                double alpha = a / 10.0;
                
                /* tampil data */
                String wherePeramalanDetail = DbPeramalanDetail.COL_PERAMALAN_ID + "='"+ peramalanId +"'"
                        + " and " + DbPeramalanDetail.COL_TIPE + "='"+ DbPeramalanDetail.DETAIL_TIPE_PENJUALAN +"'"
                        + " and " + DbPeramalanDetail.COL_ALPHA + "='"+ alpha +"' and " + DbPeramalanDetail.COL_BARANG_ID + "='"+ barang.getBarangId() +"'";
                
                Vector<PeramalanDetail> peramalanDetails = new Vector<>();
                try {
                    peramalanDetails = DbPeramalanDetail.list(wherePeramalanDetail, "(("+ DbPeramalanDetail.COL_TAHUN +" * 12)+"+ DbPeramalanDetail.COL_BULAN +") asc", 0, 0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                
                double totalAPE = 0;
                for(PeramalanDetail peramalanDetail : peramalanDetails){
                    double err = peramalanDetail.getPenjualan() - peramalanDetail.getPeramalan();
                    double percentError = err / peramalanDetail.getPenjualan() * 100;
                    double absPercentError = Math.abs(percentError);
                    totalAPE += absPercentError;
                    
                    System.out.println("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
                    System.out.print(" PERIODE: " + peramalanDetail.getBulan() + "/" + peramalanDetail.getTahun());
                    System.out.print(" penjualan: " + peramalanDetail.getPenjualan());
                    System.out.print(" peramalan: " + peramalanDetail.getPeramalan());
                    System.out.print(" err: " + err);
                    System.out.print(" errPercent: " + percentError);
                    System.out.println(" totalAPE: " + totalAPE);
                }
                double meanAbsPercentErr = totalAPE / peramalanDetails.size();
                
                System.out.println("MAPE:" + meanAbsPercentErr);
                
                /* cek angka terkecil */
                if(meanAbsPercentErr<prevMape){
                    result = alpha;
                    prevMape = meanAbsPercentErr;
                }
            }
            
            /* simpan data bobot terbaik dengan error terendah */
            Vector<PeramalanDetail> peramalanDetails = new Vector<>();
            try {
                peramalanDetails = DbPeramalanDetail.findByPeramalanBarangTipeAlpha(peramalanId, barang.getBarangId(), DbPeramalanDetail.DETAIL_TIPE_PERAMALAN, result);
                for(PeramalanDetail peramalanDetail : peramalanDetails){
                    peramalanDetail.setDisarankan(1);
                    try{
                        DbPeramalanDetail.update(peramalanDetail);
                    }catch(Exception e){
                    }
                    
                    System.out.println("--->> update rekomendasi: " + peramalanDetail.getBarang().getNama() + " | " + result);
                }
            } catch (Exception e) {
            }
        }
    }
    
    public static double tentukanAlphaTerbaik(long peramalanId){
        double result = 0;
        
        double prevMape = 100;
        for(int a = 1; a<=9; a++){
            double alpha = a / 10.0;
            
            /* tampil data */
            String wherePeramalanDetail = DbPeramalanDetail.COL_PERAMALAN_ID + "='"+ peramalanId +"'"
                    + " and " + DbPeramalanDetail.COL_TIPE + "='"+ DbPeramalanDetail.DETAIL_TIPE_PENJUALAN +"'"
                    + " and " + DbPeramalanDetail.COL_ALPHA + "='"+ alpha +"'";
            Vector<PeramalanDetail> peramalanDetails = new Vector<>();
            try {
                peramalanDetails = DbPeramalanDetail.list(wherePeramalanDetail, "(("+ DbPeramalanDetail.COL_TAHUN +" * 12)+"+ DbPeramalanDetail.COL_BULAN +") asc", 0, 0);
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            double totalAPE = 0;
            
            for(PeramalanDetail peramalanDetail : peramalanDetails){
                double err = peramalanDetail.getPenjualan() - peramalanDetail.getPeramalan();
                double percentError = err / peramalanDetail.getPenjualan() * 100;
                double absPercentError = Math.abs(percentError);
                totalAPE += absPercentError;
            }
            
            double meanAbsPercentErr = totalAPE / peramalanDetails.size();
            
            /* cek angka terkecil */
            if(meanAbsPercentErr<prevMape){
                result = alpha;
                prevMape = meanAbsPercentErr;
            }
        }
        
        return result;
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
        
        Penjualan penjualanTerbaru = new Penjualan();
        try {
            penjualanTerbaru = DbPenjualan.findByLastPeriode(barangId);
        } catch (Exception e) {
        }
        
        int periodeTerbaruPenjualan = (penjualanTerbaru.getTahun() * 12) + penjualanTerbaru.getBulan();
        
        for(int i = periodeAwal; i<=periodeAkhir; i++){
            int bulan = (i%12==0) ? 12:(i%12);
            int tahun = Integer.parseInt(String.valueOf(i/12));
            double qtyPenjualan = 0;
            
            if(bulan==12){
                tahun = tahun - 1;
            }
            
            Barang barang = new Barang();
            try {
                barang = DbBarang.findById(barangId);
            } catch (Exception e) {
            }
            
            Penjualan penjualan = new Penjualan();
            try {
                penjualan = DbPenjualan.findByPeriode(barangId, tahun, bulan);
                qtyPenjualan = penjualan.getQty();
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            if(i==periodeAwal){
                prevSmoothSingle = qtyPenjualan;
                prevSmoothDouble = qtyPenjualan;
            }
            
            double smoothSingle = 0;
            double smoothDouble = 0;
            double nilaiA = 0;
            double nilaiB = 0;
            double peramalan = 0;
            int m = 1;
            int tipe = DbPeramalanDetail.DETAIL_TIPE_PENJUALAN;

            if(i>periodeAwal){
                
                /* cek periode berjalan atau periode dimasa yang akan datang, tanpa ada penjualan */
                if(i>(periodeTerbaruPenjualan)){
                    m = i - periodeTerbaruPenjualan;
                }else{
                    /* hitung pemulusan I */
                    smoothSingle = (alpha * qtyPenjualan) + ((1 - alpha) * prevSmoothSingle);
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
                }

                /* hitung peramalan */
                if(i>(periodeAwal+1)){
                    peramalan = prevNilaiA + (prevNilaiB * m);
                    peramalan = NumberServices.decimalRounded(peramalan, 2);
                }
                
                if(i<=(periodeTerbaruPenjualan)){
                    /* peramalan periode berikutnya */
                    prevSmoothSingle = smoothSingle;
                    prevSmoothDouble = smoothDouble;
                    prevNilaiA = nilaiA;
                    prevNilaiB = nilaiB;
                    
                }else{
                    tipe = DbPeramalanDetail.DETAIL_TIPE_PERAMALAN;
                }
            }else{
                smoothSingle = qtyPenjualan;
                smoothDouble = qtyPenjualan;
            }
            
            if(i==periodeAkhir){
                tipe = DbPeramalanDetail.DETAIL_TIPE_PERAMALAN;
            }
            
            PeramalanDetail peramalanDetail = new PeramalanDetail();
            peramalanDetail.setPeramalanId(peramalanId);
            peramalanDetail.setTahun(tahun);
            peramalanDetail.setBulan(bulan);
            peramalanDetail.setBarang(barang);
            peramalanDetail.setAlpha(alpha);
            peramalanDetail.setSmoothingSingle(smoothSingle);
            peramalanDetail.setSmoothingDouble(smoothDouble);
            peramalanDetail.setNilaiA(nilaiA);
            peramalanDetail.setNilaiB(nilaiB);
            peramalanDetail.setNilaiM(m);
            peramalanDetail.setPeramalan(peramalan);
            peramalanDetail.setPenjualan(penjualan.getQty());
            peramalanDetail.setTipe(tipe);
            
            try {
                DbPeramalanDetail.save(peramalanDetail);
            } catch (Exception e) {
                e.printStackTrace();
            }
            
        }
    }
}
