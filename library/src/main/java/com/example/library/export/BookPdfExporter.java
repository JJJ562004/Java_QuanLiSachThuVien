package com.example.library.export;

import com.example.library.model.Book;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

public class BookPdfExporter {
    private List<Book> bookList;

    public BookPdfExporter(List<Book> bookList) {
        this.bookList = bookList;
    }

    public void export(HttpServletResponse response) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();

        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(18);
        font.setColor(BaseColor.BLUE);

        Paragraph title = new Paragraph("Danh sách Sách", font);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100f);
        table.setSpacingBefore(10);

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

        cell.setPhrase(new Phrase("Tiêu đề", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Tác giả", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Thể loại", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Năm", font));
        table.addCell(cell);
    }

    private void writeTableData(PdfPTable table) {
        for (Book book : bookList) {
            table.addCell(book.getId().toString());
            table.addCell(book.getTitle());
            table.addCell(book.getAuthor());
            table.addCell(book.getCategory());
            table.addCell(String.valueOf(book.getYear()));
        }
    }
}
