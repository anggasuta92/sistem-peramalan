/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.peramalan.services;

import com.peramalan.model.master.Barang;
import com.peramalan.model.master.DbBarang;
import com.peramalan.model.transaksi.DbPenjualan;
import com.peramalan.model.transaksi.Penjualan;
import java.util.List;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 *
 * @author AnggaSuta
 */
public class ImportPenjualanServices {
    
    public static Vector importPenjualan(HttpServletRequest request){
        Vector results = new Vector();
        
        try {
            if(ServletFileUpload.isMultipartContent(request)){
                /* jika multipart form / file */
                
                HSSFWorkbook workbook = null;
                DiskFileItemFactory factory = new DiskFileItemFactory();
                ServletFileUpload upload = new ServletFileUpload(factory);
                List<FileItem> items = upload.parseRequest(request);
                String filename = "";
                
                for(FileItem item : items){
                    if (!item.isFormField()){
                        workbook = new HSSFWorkbook(item.getInputStream());
                        filename = item.getName() != null ? FilenameUtils.getName(item.getName()) : "";
                    }
                }
                
                if (workbook!=null){
                    HSSFSheet sheet = workbook.getSheetAt(0);
                    int rowCount = sheet.getPhysicalNumberOfRows();
                    
                    for(int i = 0; i < rowCount; i++){
                        int tahun = 0;
                        int bulan = 0;
                        String kodeBarang = "";
                        double qty = 0;
                        
                        /* index data berada */
                        if(i>=4){
                            HSSFRow row = sheet.getRow(i);
                            
                            try {
                                tahun = (int) row.getCell((short) 1).getNumericCellValue();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            
                            try {
                                bulan = (int) row.getCell((short) 2).getNumericCellValue();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            
                            try {
                                kodeBarang =  row.getCell((short) 3).getStringCellValue();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            
                            try {
                                qty =  row.getCell((short) 5).getNumericCellValue();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            
                            Barang barang = new Barang();
                            try {
                                barang = DbBarang.findByKode(kodeBarang);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            
                            long success = 0;
                            String info = "";
                            if(barang.getBarangId()!=0){
                                Penjualan penjualan = new Penjualan();
                                try {
                                    penjualan = DbPenjualan.findByPeriode(barang.getBarangId(), tahun, bulan);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                
                                if(penjualan.getPenjualanId()!=0){
                                    penjualan.setQty(qty);
                                    success = DbPenjualan.update(penjualan);
                                    info = "Diperbaharui";
                                }else{
                                    penjualan.setBarang(barang);
                                    penjualan.setTahun(tahun);
                                    penjualan.setBulan(bulan);
                                    penjualan.setQty(qty);
                                    success = DbPenjualan.save(penjualan);
                                    info = "Tersimpan";
                                }
                            }
                            
                            String[] x = new String[5];
                            x[0] = "Data ke-" + (i-3);
                            x[1] = TransaksiService.periodeBulan[bulan] + " " + tahun;
                            x[2] = kodeBarang;
                            x[3] = NumberServices.formatNumber(qty, "#,###.##");
                            x[4] = (success==0 ? "Gagal":"Sukses");
                            results.add(x);
                        }
                        
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return results;
    }
    
}
