package com.example.inventario_labs_movil;

import android.graphics.Bitmap;
import android.os.Environment;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class PdfController {

    Bitmap bmpHeader, bmpFooter;
    private String[] laboratorio;
    private String[] reactivo;
    private String[] cantidad;

    public PdfController(String[] laboratorio, String[] reactivo, String[] cantidad,
                         Bitmap header, Bitmap footer){
        this.laboratorio = laboratorio;
        this.reactivo = reactivo;
        this.cantidad = cantidad;
        this.bmpHeader = header;
        this.bmpFooter = footer;
    }

    private String path = Environment.getExternalStorageDirectory().getPath() + "/TEST.pdf";

    public void generatePDF() throws IOException, DocumentException {
        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(path));

        writer.setPageEvent(new PdfPageEventHelper() {
            public void onEndPage(PdfWriter writer, Document document){
                try {
                    createHeader().writeSelectedRows(0, -1,
                            document.left(),
                            document.top() + document.topMargin() - 10,
                            writer.getDirectContent());

                    createFooter().writeSelectedRows(0, -1,
                            document.left(),
                            document.bottom(),
                            writer.getDirectContent());
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        document.setPageSize(PageSize.A4);
        document.setMargins(36, 36,
                60 + createHeader().getTotalHeight(),
                30 + createFooter().getTotalHeight());
        document.setMarginMirroring(false);
        document.open();

        document.add(createDataTable());

        System.out.println("PDF file generated successfully");

        document.close();
    }

    public PdfPTable createHeader() throws DocumentException, IOException {
        PdfPTable headerTable = new PdfPTable(2);
        headerTable.setSplitLate(false);
        headerTable.setSplitRows(true);
        headerTable.setWidthPercentage(50);
        headerTable.setLockedWidth(true);
        headerTable.setTotalWidth(500);

        PdfPCell logoCell = createImageCell(bmpHeader);
        logoCell.setBorder(Rectangle.NO_BORDER);
        headerTable.addCell(logoCell);
        headerTable.addCell(createEmptyCell());
        headerTable.addCell(createEmptyCell());
        headerTable.addCell(createTitleCell());

        return headerTable;
    }

    public PdfPCell createEmptyCell() {
        PdfPCell emptyCell = new PdfPCell();
        emptyCell.setBorder(Rectangle.NO_BORDER);
        return emptyCell;
    }

    public PdfPCell createTitleCell() {
        Font font1 = new Font();
        font1.setStyle(Font.BOLD);
        font1.setSize(10);
        font1.setColor(BaseColor.GRAY);

        Font font2 = new Font();
        font2.setStyle(Font.BOLD);
        font2.setSize(8);
        font2.setColor(BaseColor.GRAY);

        PdfPCell titleCell = new PdfPCell();
        Chunk chunk1 = new Chunk("Instituto Tecnologico de Ciudad Madero\n", font1);
        Chunk chunk2 = new Chunk("Departamento de Quimica y Bioquimica\n", font2);
        Paragraph paragraph = new Paragraph(new Chunk(chunk1));
        paragraph.add(new Chunk(chunk2));
        paragraph.setAlignment(Element.ALIGN_RIGHT);
        titleCell.addElement(paragraph);
        titleCell.setVerticalAlignment(Element.ALIGN_BOTTOM);
        titleCell.setBorder(Rectangle.NO_BORDER);
        return titleCell;
    }

    public PdfPCell createImageCell(Bitmap bmp) throws DocumentException, IOException {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        Image image = Image.getInstance(byteArray);
        return new PdfPCell(image, true);
    }

    public PdfPTable createFooter() throws DocumentException, IOException {
        PdfPTable footerTable = new PdfPTable(1);
        footerTable.setSplitLate(false);
        footerTable.setSplitRows(true);
        footerTable.setWidthPercentage(100);
        footerTable.setLockedWidth(true);
        footerTable.setTotalWidth(527);

        PdfPCell logoCell = createImageCell(bmpFooter);
        logoCell.setBorder(Rectangle.NO_BORDER);

        footerTable.addCell(logoCell);

        return footerTable;
    }

    public PdfPTable createDataTable() {
        PdfPTable table = new PdfPTable(3);
        table.setSplitLate(false);
        table.setSplitRows(true);

        Font fontHeader = new Font();
        fontHeader.setSize(13);
        fontHeader.setStyle(Font.BOLD);

        PdfPCell column1 = new PdfPCell(new Phrase("Laboratorio", fontHeader));
        column1.setHorizontalAlignment(Element.ALIGN_CENTER);

        PdfPCell column2 = new PdfPCell(new Phrase("Reactivo/Material", fontHeader));
        column2.setHorizontalAlignment(Element.ALIGN_CENTER);

        PdfPCell column3 = new PdfPCell(new Phrase("Cantidad", fontHeader));
        column3.setHorizontalAlignment(Element.ALIGN_CENTER);

        table.addCell(column1);
        table.addCell(column2);
        table.addCell(column3);

        table.setHeaderRows(1);

        for(int i = 0; i < laboratorio.length ; i++){
            table.addCell(new Phrase(laboratorio[i], FontFactory.getFont("montserrat_light", 11)));
            table.addCell(new Phrase(reactivo[i], FontFactory.getFont("montserrat_light", 11)));
            table.addCell(new Phrase(cantidad[i], FontFactory.getFont("montserrat_light", 11)));
        }

        return table;
    }

    /*public Font generateFont() {
        BaseFont baseFont = null;
        try{
            baseFont = BaseFont.createFont("montserrat_light.ttf",
                    BaseFont.IDENTITY_H, true, false, fontData, null);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        //FontFactory.register(font_path.toString(), "montserrat_light");
        return new Font(baseFont);
    }*/
}
