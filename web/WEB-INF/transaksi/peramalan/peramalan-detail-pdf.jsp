<%@page import="com.peramalan.services.NumberServices"%>
<%@page import="com.peramalan.services.TransaksiService"%>
<%@page import="com.lowagie.text.Rectangle"%>
<%@page import="org.w3c.dom.css.RGBColor"%>
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
    Color colorBlack = new Color(0, 0, 0);
    Color colorWhite = new Color(250, 250, 250);
    
    Font fontBold = FontFactory.getFont(FontFactory.HELVETICA,10,Font.BOLD,colorBlack);
    Font fontBoldBigger = FontFactory.getFont(FontFactory.HELVETICA,12,Font.BOLD,colorBlack);
    Font font = FontFactory.getFont(FontFactory.HELVETICA,10,Font.NORMAL,colorBlack);

    Document document = new Document(PageSize.A4, 50,50,50,50);
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    
    try {
        PdfWriter writer = PdfWriter.getInstance(document, baos);
        document.open();
        
        Table tableHeader = new Table(7);
        int widthHeaders[] = {25,3,30,5,25,3,30};
        tableHeader.setWidths(widthHeaders);
        tableHeader.setBorder(Rectangle.NO_BORDER);
        tableHeader.setWidth(100);
        tableHeader.setPadding(1);
        tableHeader.setSpacing(0);
        
        Cell cell = new Cell();
        
        /* Header report */
        cell = new Cell(new Chunk("UD PARAMA STORE", fontBoldBigger));
        cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setColspan(7);
        tableHeader.addCell(cell);
        
        cell = new Cell(new Chunk("DETAIL PERAMALAN", fontBoldBigger));
        cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setColspan(7);
        tableHeader.addCell(cell);
        
        cell = new Cell(new Chunk("", fontBold));
        cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setColspan(7);
        tableHeader.addCell(cell);
        
        cell = new Cell(new Chunk("Nomor", font));
        cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Cell.ALIGN_LEFT);
        cell.setBorder(Rectangle.NO_BORDER);
        tableHeader.addCell(cell);
        
        cell = new Cell(new Chunk(":", font));
        cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Cell.ALIGN_LEFT);
        cell.setBorder(Rectangle.NO_BORDER);
        tableHeader.addCell(cell);
        
        cell = new Cell(new Chunk("" + peramalan.getNomor(), font));
        cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Cell.ALIGN_LEFT);
        cell.setBorder(Rectangle.NO_BORDER);
        tableHeader.addCell(cell);
        
        cell = new Cell(new Chunk("", font));
        cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Cell.ALIGN_LEFT);
        cell.setBorder(Rectangle.NO_BORDER);
        tableHeader.addCell(cell);
        
        cell = new Cell(new Chunk("Periode Peramalan", font));
        cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Cell.ALIGN_LEFT);
        cell.setBorder(Rectangle.NO_BORDER);
        tableHeader.addCell(cell);
        
        cell = new Cell(new Chunk(":", font));
        cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Cell.ALIGN_LEFT);
        cell.setBorder(Rectangle.NO_BORDER);
        tableHeader.addCell(cell);
        
        cell = new Cell(new Chunk("" + TransaksiService.periodeBulan[peramalan.getPeramalanBulan()] + " " + peramalan.getPeramalanTahun(), font));
        cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Cell.ALIGN_LEFT);
        cell.setBorder(Rectangle.NO_BORDER);
        tableHeader.addCell(cell);
        
        cell = new Cell(new Chunk("Tanggal", font));
        cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Cell.ALIGN_LEFT);
        cell.setBorder(Rectangle.NO_BORDER);
        tableHeader.addCell(cell);
        
        cell = new Cell(new Chunk(":", font));
        cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Cell.ALIGN_LEFT);
        cell.setBorder(Rectangle.NO_BORDER);
        tableHeader.addCell(cell);
        
        cell = new Cell(new Chunk("" + peramalan.getTanggal(), font));
        cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Cell.ALIGN_LEFT);
        cell.setBorder(Rectangle.NO_BORDER);
        tableHeader.addCell(cell);
        
        cell = new Cell(new Chunk("", font));
        cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Cell.ALIGN_LEFT);
        cell.setBorder(Rectangle.NO_BORDER);
        tableHeader.addCell(cell);
        
        cell = new Cell(new Chunk("Periode Penjualan", font));
        cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Cell.ALIGN_LEFT);
        cell.setBorder(Rectangle.NO_BORDER);
        tableHeader.addCell(cell);
        
        cell = new Cell(new Chunk(":", font));
        cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Cell.ALIGN_LEFT);
        cell.setBorder(Rectangle.NO_BORDER);
        tableHeader.addCell(cell);
        
        cell = new Cell(new Chunk("" + TransaksiService.periodeBulan[peramalan.getPenjualanBulan()] + " " + peramalan.getPenjualanTahun(), font));
        cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Cell.ALIGN_LEFT);
        cell.setBorder(Rectangle.NO_BORDER);
        tableHeader.addCell(cell);
        
        cell = new Cell(new Chunk("", font));
        cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Cell.ALIGN_LEFT);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setColspan(7);
        tableHeader.addCell(cell);
        
        document.add(tableHeader);
        /* header report */
        
        /* detail */
        Table tableDetail = new Table(6);
        int widthDetails[] = {7,20,50,10,15,15};
        tableDetail.setWidths(widthDetails);
        tableDetail.setBorder(Rectangle.NO_BORDER);
        tableDetail.setWidth(100);
        tableDetail.setPadding(2);
        tableDetail.setSpacing(0);
        
        cell = new Cell(new Chunk("No", font));
        cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
        cell.setBorder(Rectangle.BOX);
        tableDetail.addCell(cell);
        
        cell = new Cell(new Chunk("Periode", font));
        cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
        cell.setBorder(Rectangle.BOX);
        tableDetail.addCell(cell);
        
        cell = new Cell(new Chunk("Barang", font));
        cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
        cell.setBorder(Rectangle.BOX);
        tableDetail.addCell(cell);
        
        cell = new Cell(new Chunk("Alpha", font));
        cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
        cell.setBorder(Rectangle.BOX);
        tableDetail.addCell(cell);
        
        cell = new Cell(new Chunk("Peramalan", font));
        cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
        cell.setBorder(Rectangle.BOX);
        tableDetail.addCell(cell);
        
        cell = new Cell(new Chunk("Ketarangan", font));
        cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
        cell.setBorder(Rectangle.BOX);
        tableDetail.addCell(cell);
        
        int no = 0;
        for(PeramalanDetail peramalanDetail : peramalanDetails){
            no++;
            
            cell = new Cell(new Chunk("" + NumberServices.formatNumber(no, "#,##0"), font));
            cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cell.setBorder(Rectangle.BOX);
            tableDetail.addCell(cell);

            cell = new Cell(new Chunk("" + TransaksiService.periodeBulan[peramalanDetail.getBulan()] + " " + peramalanDetail.getTahun(), font));
            cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cell.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cell.setBorder(Rectangle.BOX);
            tableDetail.addCell(cell);

            cell = new Cell(new Chunk("" + peramalanDetail.getBarang().getNama(), font));
            cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cell.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cell.setBorder(Rectangle.BOX);
            tableDetail.addCell(cell);

            cell = new Cell(new Chunk("" + peramalanDetail.getAlpha(), font));
            cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cell.setHorizontalAlignment(Cell.ALIGN_RIGHT);
            cell.setBorder(Rectangle.BOX);
            tableDetail.addCell(cell);

            cell = new Cell(new Chunk("" + NumberServices.formatNumber(peramalanDetail.getPeramalan(), "#,##0.00"), font));
            cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cell.setHorizontalAlignment(Cell.ALIGN_RIGHT);
            cell.setBorder(Rectangle.BOX);
            tableDetail.addCell(cell);
            
            cell = new Cell(new Chunk("" + (peramalanDetail.getDisarankan()==1 ? "Disarankan":""), font));
            cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cell.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cell.setBorder(Rectangle.BOX);
            tableDetail.addCell(cell);
        }
        
        document.add(tableDetail);
        /* detail */
        
    } catch (Exception e) {
        e.printStackTrace();
    }
    
    document.close();
    
    /* response handle */
    response.reset();
    response.setContentType("application/pdf");
    response.setHeader("Content-Disposition", "inline; filename=detail-peramalan.pdf");
    response.setContentLength(baos.size());
    ServletOutputStream outx = response.getOutputStream();
    baos.writeTo(outx);
    outx.flush();
%>