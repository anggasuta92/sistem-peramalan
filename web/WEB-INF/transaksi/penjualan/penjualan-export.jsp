<%@page import="com.peramalan.model.transaksi.Penjualan"%>
<%@page import="com.peramalan.model.transaksi.DbPenjualan"%>
<%@page import="java.util.Vector"%>
<%@page import="com.peramalan.model.master.DbBarang"%>
<%@page import="com.peramalan.services.TransaksiService"%>
<%@page import="java.io.OutputStream"%>
<%@page import="org.apache.poi.ss.util.CellRangeAddress"%>
<%@page import="org.apache.poi.ss.usermodel.Cell"%>
<%@page import="org.apache.poi.ss.usermodel.Row"%>
<%@page import="org.apache.poi.ss.usermodel.IndexedColors"%>
<%@page import="org.apache.poi.ss.usermodel.BorderStyle"%>
<%@page import="org.apache.poi.ss.usermodel.HorizontalAlignment"%>
<%@page import="org.apache.poi.ss.usermodel.VerticalAlignment"%>
<%@page import="org.apache.poi.ss.usermodel.CellStyle"%>
<%@page import="com.peramalan.services.JSPHandler"%>
<%@page import="org.apache.poi.ss.usermodel.Sheet"%>
<%@page import="org.apache.poi.hssf.usermodel.HSSFWorkbook"%>
<%
    int bulan = JSPHandler.requestInt(request, "bulan");
    int tahun = JSPHandler.requestInt(request, "tahun");
    String param = JSPHandler.requestString(request, "param");
    xxxxxxxxxx;
    
    String where = "";
    if(param!=null && param.length()>0){
        where = DbBarang.COL_KODE + " like '%"+ param +"%' or "+DbBarang.COL_BARCODE + " like '%"+ param +"%' or " + DbBarang.COL_NAMA + " like '%"+ param +"%'";
    }
    
    Vector datas = new Vector();
    try {
        datas = DbPenjualan.listPenjualanJoinBarang(where, tahun, bulan, "", 0, 0);
    } catch (Exception e) {
    }
    
    String fileName = TransaksiService.periodeBulan[bulan] + "_" + tahun + "_Export_Penjualan.xls";
    
    HSSFWorkbook workbook = new HSSFWorkbook();
    Sheet sheet = workbook.createSheet("dataPenjualan");
    sheet.setColumnWidth(0, (5 * 256));
    sheet.setColumnWidth(1, (8 * 256));
    sheet.setColumnWidth(2, (8 * 256));
    sheet.setColumnWidth(3, (20 * 256));
    sheet.setColumnWidth(4, (40 * 256));
    sheet.setColumnWidth(5, (13 * 256));
    sheet.setMargin(Sheet.LeftMargin, 0.5);
    sheet.setMargin(Sheet.RightMargin, 0.5);
    
    /* Style */
    CellStyle styleHeaderTable = workbook.createCellStyle();
    styleHeaderTable.setVerticalAlignment(VerticalAlignment.CENTER);
    styleHeaderTable.setAlignment(HorizontalAlignment.CENTER);
    styleHeaderTable.setBorderBottom(BorderStyle.THIN);  
    styleHeaderTable.setBottomBorderColor(IndexedColors.BLACK.getIndex());  
    styleHeaderTable.setBorderRight(BorderStyle.THIN);  
    styleHeaderTable.setRightBorderColor(IndexedColors.BLACK.getIndex());  
    styleHeaderTable.setBorderTop(BorderStyle.THIN);  
    styleHeaderTable.setTopBorderColor(IndexedColors.BLACK.getIndex());
    
    CellStyle styleDetailTable = workbook.createCellStyle();
    styleDetailTable.setBorderBottom(BorderStyle.THIN);  
    styleDetailTable.setBottomBorderColor(IndexedColors.BLACK.getIndex());  
    styleDetailTable.setBorderRight(BorderStyle.THIN);  
    styleDetailTable.setRightBorderColor(IndexedColors.BLACK.getIndex());  
    styleDetailTable.setBorderTop(BorderStyle.THIN);  
    styleDetailTable.setTopBorderColor(IndexedColors.BLACK.getIndex());
    
    /* Data */
    Row row;
    Cell cell;
    
    int baris = 0;
    
    row = sheet.createRow(baris);
    cell = row.createCell(0);
    cell.setCellValue("Laporan Data Penjualan");
    
    baris++;
    row = sheet.createRow(baris);
    cell = row.createCell(0);
    cell.setCellValue("Periode : " + TransaksiService.periodeBulan[bulan] + " " + tahun);
    
    /* header tabel */
    baris++;
    baris++;
    row = sheet.createRow(baris);
    cell = row.createCell(0);
    cell.setCellValue("No");
    cell.setCellStyle(styleHeaderTable);
    sheet.addMergedRegion(new CellRangeAddress(baris, baris+1, 0, 0));
    
    cell = row.createCell(1);
    cell.setCellValue("Periode");
    cell.setCellStyle(styleHeaderTable);
    sheet.addMergedRegion(new CellRangeAddress(baris, baris+1, 1, 2));
    
    cell = row.createCell(2);
    cell.setCellStyle(styleHeaderTable);
//    sheet.addMergedRegion(new CellRangeAddress(baris, baris, 1, 2));
    
    cell = row.createCell(3);
    cell.setCellValue("Detail Barang");
    cell.setCellStyle(styleHeaderTable);
    
    cell = row.createCell(4);
    cell.setCellStyle(styleHeaderTable);
    sheet.addMergedRegion(new CellRangeAddress(baris, baris, 3, 4));
    
    cell = row.createCell(5);
    cell.setCellValue("Qty");
    cell.setCellStyle(styleHeaderTable);
    sheet.addMergedRegion(new CellRangeAddress(baris, baris+1, 5, 5));
    
    baris++;
    row = sheet.createRow(baris);
    cell = row.createCell(0);
    cell.setCellStyle(styleHeaderTable);
    
    cell = row.createCell(1);
    cell.setCellStyle(styleHeaderTable);
    
    cell = row.createCell(2);
    cell.setCellStyle(styleHeaderTable);
    
    cell = row.createCell(3);
    cell.setCellValue("Kode");
    cell.setCellStyle(styleHeaderTable);
    
    cell = row.createCell(4);
    cell.setCellValue("Nama Barang");
    cell.setCellStyle(styleHeaderTable);
    
    cell = row.createCell(5);
    cell.setCellStyle(styleHeaderTable);
    /* header tabel */
    
    /* detail */
    if(datas!=null && datas.size()>0){
        for(int i = 0; i< datas.size(); i++){
            Penjualan penjualan = (Penjualan) datas.get(i);
            
            baris++;
            int noUrut = i + 1;
            row = sheet.createRow(baris);
            cell = row.createCell(0);
            cell.setCellValue(noUrut);
            cell.setCellStyle(styleDetailTable);

            cell = row.createCell(1);
            cell.setCellValue(""+TransaksiService.periodeBulan[bulan] + " " + tahun);
            cell.setCellStyle(styleDetailTable);
            
            cell = row.createCell(2);
            cell.setCellStyle(styleDetailTable);
            sheet.addMergedRegion(new CellRangeAddress(baris, baris, 1, 2));

            cell = row.createCell(3);
            cell.setCellValue("" + penjualan.getBarang().getKode());
            cell.setCellStyle(styleDetailTable);

            cell = row.createCell(4);
            cell.setCellValue("" + penjualan.getBarang().getNama());
            cell.setCellStyle(styleDetailTable);

            cell = row.createCell(5);
            cell.setCellValue(penjualan.getQty());
            cell.setCellStyle(styleDetailTable);
        }
    }
        
    
    /* response handle */
    response.reset();
    response.setContentType("application/vnd.ms-excel");
    response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
    
    OutputStream outStream = response.getOutputStream();
    outStream.flush();
    workbook.write(outStream);
    outStream.close();
    
%>