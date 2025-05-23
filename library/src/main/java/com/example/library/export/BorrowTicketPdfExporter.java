package com.example.library.export;

import java.io.IOException;
import java.util.List;

import com.example.library.model.BorrowTicket;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
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

public class BorrowTicketPdfExporter {
    private List<BorrowTicket> ticketList;

    public BorrowTicketPdfExporter(List<BorrowTicket> ticketList) {
        this.ticketList = ticketList;
    }

    public void export(HttpServletResponse response) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();

        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(15);
        Paragraph title = new Paragraph("Danh sach phieu muon", font);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);
        document.add(Chunk.NEWLINE);

        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(100f);
        table.setWidths(new float[]{1f, 3f, 2f, 2f});
        writeTableHeader(table);
        writeTableData(table);

        document.add(table);
        document.close();
    }

    private void writeTableHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        cell.setPadding(2);

        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setColor(BaseColor.BLACK);
        font.setSize(10);

        cell.setPhrase(new Phrase("ID", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Ten ban doc", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Ngay muon", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Ngay tra", font));
        table.addCell(cell);

    }

    private void writeTableData(PdfPTable table) {
        for (BorrowTicket t : ticketList) {
            table.addCell(t.getId().toString());
            table.addCell(t.getReader().getName());
            table.addCell(t.getBorrowDate().toString());
            table.addCell(t.getReturnDate() != null ? t.getReturnDate().toString() : "");
        }
    }
}
