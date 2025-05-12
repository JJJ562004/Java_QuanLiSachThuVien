package com.example.library.export;

import com.example.library.model.BorrowTicket;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

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
        font.setSize(18);
        Paragraph title = new Paragraph("Danh sách Phiếu Mượn", font);
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
        cell.setPadding(5);

        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setColor(BaseColor.BLACK);

        cell.setPhrase(new Phrase("ID", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Tên bạn đọc", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Ngày mượn", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Ngày trả", font));
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
