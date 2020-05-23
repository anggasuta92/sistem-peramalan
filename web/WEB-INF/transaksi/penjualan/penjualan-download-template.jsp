<%@page import="java.io.FileOutputStream"%>
<%@page import="org.apache.poi.ss.usermodel.Cell"%>
<%@page import="org.apache.poi.ss.usermodel.Row"%>
<%@page import="org.apache.poi.ss.usermodel.Sheet"%>
<%@page import="org.apache.poi.hssf.usermodel.HSSFWorkbook"%>
<%
    response.setContentType("application/vnd.ms-excel");
    response.setHeader("Content-Disposition", "attachment");
    
    HSSFWorkbook workbook = new HSSFWorkbook();
    Sheet sheet = workbook.createSheet("import");
    
    sheet.setDisplayGridlines(false);
    sheet.setPrintGridlines(false);
    
    Row row = sheet.createRow(0);
    Cell cellHeader = row.createCell(0);
    cellHeader.setCellValue("Import Data Penjualan");
    

    String fileName = "template-import.xls";
    FileOutputStream output = new FileOutputStream(fileName);
    workbook.write(output);
    output.close();
    workbook.close();
%>