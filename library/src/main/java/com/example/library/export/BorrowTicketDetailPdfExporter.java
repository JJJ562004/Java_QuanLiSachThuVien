package com.example.library.export;

import com.example.library.model.Book;
import com.example.library.model.BorrowTicketDetail;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        font.setSize(18);
        Paragraph title = new Paragraph("Chi tiết Phiếu Mượn", font);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);
        document.add(Chunk.NEWLINE);

        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100f);
        table.setWidths(new float[]{1f, 2f, 2f, 3f, 1f});
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

        cell.setPhrase(new Phrase("ID phiếu mượn", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Sách mượn", font));
        table.addCell(cell);

    }

    private void writeTableData(PdfPTable table) {
        for (BorrowTicketDetail d : detailList) {
            // Cột ID chi tiết
            table.addCell(d.getId());

            // Cột ID phiếu mượn
            table.addCell(d.getBorrowTicket().getId().toString());

            // Cột list tiêu đề sách, ngăn cách bởi dấu phẩy
            List<String> titles = d.getBooks().stream()
                                    .map(Book::getTitle)
                                    .collect(Collectors.toList());
            table.addCell(String.join(", ", titles));
        }
    }
}
