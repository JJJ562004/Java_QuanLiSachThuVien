package com.example.library.export;

import java.io.IOException;
import java.util.List;

import com.example.library.model.BorrowTicketDetail;
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

public class BorrowTicketDetailPdfExporter {

    private List<BorrowTicketDetail> detailList;

    public BorrowTicketDetailPdfExporter(List<BorrowTicketDetail> detailList) {
        this.detailList = detailList;
    }

    public void export(HttpServletResponse response) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4.rotate());
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();

        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(15);

        Paragraph title = new Paragraph("Chi tiết Phiếu Mượn", font);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);
        document.add(Chunk.NEWLINE);

        PdfPTable table = new PdfPTable(3);
        table.setWidthPercentage(100f);
        table.setWidths(new float[]{2f, 3f, 6f});
        table.setSpacingBefore(2);

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
        font.setSize(10);

        cell.setPhrase(new Phrase("ID", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("ID Phieu Muon", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Sach muon", font));
        table.addCell(cell);
    }

    private void writeTableData(PdfPTable table) {
        for (BorrowTicketDetail d : detailList) {
            // ID của chi tiết phiếu mượn
            table.addCell(d.getId());

            // ID của phiếu mượn
            table.addCell(d.getBorrowTicket().getId());

            // Danh sách tên sách
            String titles = d.getBook().getTitle();
            table.addCell(titles);
        }
    }
}
