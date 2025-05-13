package com.example.library.export;

import java.io.IOException;
import java.util.List;

import com.example.library.model.Book;
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
        font.setSize(15);
        font.setColor(BaseColor.BLUE);

        Paragraph title = new Paragraph("Danh sách Sách", font);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100f);
        table.setSpacingBefore(5);

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

        cell.setPhrase(new Phrase("Tieu de", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Tac gia", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("The loai", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Nam", font));
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
