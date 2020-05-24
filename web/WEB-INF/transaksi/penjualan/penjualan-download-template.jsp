<%@page import="org.apache.poi.ss.usermodel.IndexedColors"%>
<%@page import="org.apache.poi.ss.usermodel.BorderStyle"%>
<%@page import="org.apache.poi.ss.usermodel.CellStyle"%>
<%@page import="org.apache.poi.ss.usermodel.Workbook"%>
<%@page import="org.apache.poi.ss.usermodel.HorizontalAlignment"%>
<%@page import="org.apache.poi.ss.usermodel.VerticalAlignment"%>
<%@page import="org.apache.poi.ss.util.CellRangeAddress"%>
<%@page import="java.io.OutputStream"%>
<%@page import="java.io.ByteArrayOutputStream"%>
<%@page import="java.io.FileOutputStream"%>
<%@page import="org.apache.poi.ss.usermodel.Cell"%>
<%@page import="org.apache.poi.ss.usermodel.Row"%>
<%@page import="org.apache.poi.ss.usermodel.Sheet"%>
<%@page import="org.apache.poi.hssf.usermodel.HSSFWorkbook"%>
<%!
    public static void setCellAlign(Workbook wb, Cell cell, VerticalAlignment vAlign, HorizontalAlignment hAlign){
        CellStyle cellStyle = wb.createCellStyle();
        cellStyle.setVerticalAlignment(vAlign);
        cellStyle.setAlignment(hAlign);
        cell.setCellStyle(cellStyle);
    }

%>
<%          
    HSSFWorkbook workbook = new HSSFWorkbook();
    Sheet sheet = workbook.createSheet("import");
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
    
    row = sheet.createRow(0);
    cell = row.createCell(0);
    cell.setCellValue("FORM IMPORT DATA PENJUALAN");
    
    /* header tabel */
    row = sheet.createRow(2);
    cell = row.createCell(0);
    cell.setCellValue("No");
    cell.setCellStyle(styleHeaderTable);
    sheet.addMergedRegion(new CellRangeAddress(2, 3, 0, 0));
    
    cell = row.createCell(1);
    cell.setCellValue("Periode");
    cell.setCellStyle(styleHeaderTable);
    
    cell = row.createCell(2);
    cell.setCellStyle(styleHeaderTable);
    sheet.addMergedRegion(new CellRangeAddress(2, 2, 1, 2));
    
    cell = row.createCell(3);
    cell.setCellValue("Detail Barang");
    cell.setCellStyle(styleHeaderTable);
    
    cell = row.createCell(4);
    cell.setCellStyle(styleHeaderTable);
    sheet.addMergedRegion(new CellRangeAddress(2, 2, 3, 4));
    
    cell = row.createCell(5);
    cell.setCellValue("Qty");
    cell.setCellStyle(styleHeaderTable);
    sheet.addMergedRegion(new CellRangeAddress(2, 3, 5, 5));
    
    row = sheet.createRow(3);
    cell = row.createCell(0);
    cell.setCellStyle(styleHeaderTable);
    
    cell = row.createCell(1);
    cell.setCellValue("Tahun");
    cell.setCellStyle(styleHeaderTable);
    
    cell = row.createCell(2);
    cell.setCellValue("Bulan");
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
    
    /* detail tabel */
    row = sheet.createRow(4);
    cell = row.createCell(0);
    cell.setCellValue(1);
    cell.setCellStyle(styleDetailTable);
    
    cell = row.createCell(1);
    cell.setCellValue(2020);
    cell.setCellStyle(styleDetailTable);
    
    cell = row.createCell(2);
    cell.setCellValue(12);
    cell.setCellStyle(styleDetailTable);
    
    cell = row.createCell(3);
    cell.setCellValue("BRG00001");
    cell.setCellStyle(styleDetailTable);
    
    cell = row.createCell(4);
    cell.setCellValue("Nama Barang 0001");
    cell.setCellStyle(styleDetailTable);
    
    cell = row.createCell(5);
    cell.setCellValue(10);
    cell.setCellStyle(styleDetailTable);
    
    cell = row.createCell(6);
    cell.setCellValue("contoh pengisian");
    
    row = sheet.createRow(5);
    cell = row.createCell(0);
    cell = row.createCell(1);
    cell = row.createCell(2);
    cell = row.createCell(3);
    cell = row.createCell(4);
    cell = row.createCell(5);
    /* response handle */
    response.reset();
    response.setContentType("application/vnd.ms-excel");
    response.setHeader("Content-Disposition", "attachment; filename=templateImportPenjualan.xls");
    
    OutputStream outStream = response.getOutputStream();
    outStream.flush();
    workbook.write(outStream);
    outStream.close();
    
%>