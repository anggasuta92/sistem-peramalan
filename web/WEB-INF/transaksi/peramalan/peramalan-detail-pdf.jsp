<%@page import="com.lowagie.text.Cell"%>
<%@page import="com.lowagie.text.pdf.CMYKColor"%>
<%@page import="java.awt.Color"%>
<%@page import="com.lowagie.text.FontFactory"%>
<%@page import="com.lowagie.text.Font"%>
<%@page import="com.lowagie.text.Table"%>
<%@page import="com.lowagie.text.Chunk"%>
<%@page import="java.io.ByteArrayOutputStream"%>
<%@page import="com.lowagie.text.PageSize"%>
<%@page import="com.peramalan.model.transaksi.PeramalanDetail"%>
<%@page import="java.util.Vector"%>
<%@page import="com.peramalan.model.transaksi.Peramalan"%>
<%@page import="com.lowagie.text.Document"%>
<%@page import="com.lowagie.text.pdf.PdfWriter"%>
<%
    
    Peramalan peramalan = new Peramalan();
    try {
        peramalan = (Peramalan) request.getAttribute("peramalan");
    } catch (Exception e) {
    }
    
    Vector<PeramalanDetail> peramalanDetails = new Vector<PeramalanDetail>();
    try {
        peramalanDetails = (Vector<PeramalanDetail>) request.getAttribute("peramalanDetails");
    } catch (Exception e) {
    }
    
    /* PDF */

    Font fontHeader = FontFactory.getFont(FontFactory.HELVETICA,14,Font.BOLD,CMYKColor.BLACK);
    
    
    Document document = new Document(PageSize.A4);
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    
    try {
        PdfWriter writer = PdfWriter.getInstance(document, baos);
        document.open();
        
        Table tableHeader = new Table(7);
        int widthHeaders[] = {10,5,30,10,10,5,30};
        tableHeader.setWidths(widthHeaders);
        tableHeader.setWidth(100);
        
        Cell cell = new Cell();
        
        cell = new Cell(new Chunk("Nomor", fontHeader));
        cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Cell.ALIGN_LEFT);
        tableHeader.addCell(cell);
        
        cell = new Cell(new Chunk("Nomor", fontHeader));
        cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Cell.ALIGN_LEFT);
        tableHeader.addCell(cell);
        
        cell = new Cell(new Chunk("Nomor", fontHeader));
        cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Cell.ALIGN_LEFT);
        tableHeader.addCell(cell);
        
        cell = new Cell(new Chunk("Nomor", fontHeader));
        cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Cell.ALIGN_LEFT);
        tableHeader.addCell(cell);
        
        cell = new Cell(new Chunk("Nomor", fontHeader));
        cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Cell.ALIGN_LEFT);
        tableHeader.addCell(cell);
        
        cell = new Cell(new Chunk("Nomor", fontHeader));
        cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Cell.ALIGN_LEFT);
        tableHeader.addCell(cell);
        
        document.add(tableHeader);
        
        document.add(new Chunk(""));
    } catch (Exception e) {
        e.printStackTrace();
    }
    
    document.close();
    
    /* response handle */
    response.reset();
    response.setContentType("application/application/pdf");
    response.setHeader("Content-Disposition", "attachment; filename=detail-peramalan.pdf");
    response.setContentLength(baos.size());
    ServletOutputStream outx = response.getOutputStream();
    baos.writeTo(outx);
    outx.flush();
%>