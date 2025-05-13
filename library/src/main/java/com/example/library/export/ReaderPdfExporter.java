package com.example.library.export;

import java.io.IOException;
import java.util.List;

import com.example.library.model.Reader;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import jakarta.servlet.http.HttpServletResponse;

public class ReaderPdfExporter {

    private List<Reader> readerList;

    public ReaderPdfExporter(List<Reader> readerList) {
        this.readerList = readerList;
    }

    public void export(HttpServletResponse response) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();

        Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fontTitle.setSize(15);
        Paragraph title = new Paragraph("Danh sach ban doc", fontTitle);
        title.setAlignment(Element.ALIGN_CENTER);

        document.add(title);
        document.add(new Paragraph(" "));

        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100f);
        table.setWidths(new float[]{2.5f, 2.5f, 2.5f, 2.5f, 2.5f});
        table.setSpacingBefore(3);

        writeTableHeader(table);
        writeTableData(table);

        document.add(table);
        document.close();
    }

    private void writeTableHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(BaseColor.CYAN);
        cell.setPadding(2);

        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setColor(BaseColor.BLACK);
        font.setSize(10);

        cell.setPhrase(new Phrase("ID", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Ten", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Email", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("SDT", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Dia chi", font));
        table.addCell(cell);
    }

    private void writeTableData(PdfPTable table) {
        for (Reader reader : readerList) {
            table.addCell(reader.getId());
            table.addCell(reader.getName());
            table.addCell(reader.getEmail());
            table.addCell(reader.getPhoneNumber());
            table.addCell(reader.getAddress());
        }
    }
}
