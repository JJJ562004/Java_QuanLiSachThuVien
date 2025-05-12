package com.example.library.controller;

import com.example.library.export.BorrowTicketDetailPdfExporter;
import com.example.library.model.Book;
import com.example.library.model.BorrowTicket;
import com.example.library.model.BorrowTicketDetail;
import com.example.library.service.BookService;
import com.example.library.service.BorrowTicketDetailService;
import com.example.library.service.BorrowTicketService;
import com.itextpdf.text.DocumentException;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/borrow-ticket-details")
public class BorrowTicketDetailController {

    private final BorrowTicketDetailService detailService;
    private final BorrowTicketService ticketService;
    private final BookService bookService;

    public BorrowTicketDetailController(BorrowTicketDetailService detailService,
                                        BorrowTicketService ticketService,
                                        BookService bookService) {
        this.detailService = detailService;
        this.ticketService = ticketService;
        this.bookService = bookService;
    }

    // 1. Danh sách BorrowTicketDetail
    @GetMapping
    public String listDetails(Model model) {
        List<BorrowTicketDetail> details = detailService.getAllDetails();
        model.addAttribute("details", details);
        return "borrow-ticket-details";
    }

    // 2. Form thêm mới
    @GetMapping("/add")
    public String showCreateForm(Model model) {
        model.addAttribute("detail", new BorrowTicketDetail());
        model.addAttribute("tickets", ticketService.getAllBorrowTickets());
        model.addAttribute("books", bookService.getAllBooks());
        return "add-borrow-ticket-detail";
    }

    // 3. Lưu mới
    @PostMapping("/add")
    public String saveDetail(@ModelAttribute("detail") BorrowTicketDetail detail,
                             @RequestParam("ticketId") String ticketId,
                             @RequestParam("bookIds") List<String> bookIds) {
        // Gán BorrowTicket
        BorrowTicket ticket = ticketService.getBorrowTicketById(ticketId);
        detail.setBorrowTicket(ticket);
        // Gán sách
        List<Book> books = bookService.getAllBooks().stream()
            .filter(b -> bookIds.contains(b.getId()))
            .toList();
        detail.setBooks(books);
        detailService.save(detail);
        return "redirect:/borrow-ticket-details";
    }

    // 4. Form sửa
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable String id, Model model) {
        BorrowTicketDetail detail = detailService.getById(id);
        if (detail == null) {
            return "redirect:/borrow-ticket-details";
        }
        model.addAttribute("detail", detail);
        model.addAttribute("tickets", ticketService.getAllBorrowTickets());
        model.addAttribute("books", bookService.getAllBooks());
        return "borrow_ticket_detail_form";
    }

    // 5. Cập nhật Chi tiết Phiếu Mượn
@PostMapping("/edit/{id}")
public String updateDetail(@PathVariable String id,
                           @RequestParam("ticketId") String ticketId,
                           @RequestParam("bookIds") List<String> bookIds) {
    // 1. Lấy chi tiết hiện tại từ DB
    BorrowTicketDetail existing = detailService.getById(id);
    if (existing == null) {
        return "redirect:/borrow-ticket-details";
    }

    // 2. Cập nhật phiếu mượn
    BorrowTicket ticket = ticketService.getBorrowTicketById(ticketId);
    existing.setBorrowTicket(ticket);

    // 3. Cập nhật danh sách sách
    List<Book> selectedBooks = bookService.getAllBooks().stream()
        .filter(b -> bookIds.contains(b.getId()))
        .toList();
    existing.setBooks(selectedBooks);

    // 4. Lưu lại
    detailService.save(existing);
    return "redirect:/borrow-ticket-details";
}


    // 6. Xóa
    @GetMapping("/delete/{id}")
    public String deleteDetail(@PathVariable String id) {
        detailService.deleteById(id);
        return "redirect:/borrow-ticket-details";
    }

    @GetMapping("/export/pdf")
public void exportBorrowTicketDetailsToPdf(HttpServletResponse response) throws DocumentException, IOException {
    response.setContentType("application/pdf");
    response.setHeader("Content-Disposition", "attachment; filename=borrow_ticket_details.pdf");

    List<BorrowTicketDetail> details = detailService.getAllDetails();
    BorrowTicketDetailPdfExporter exporter = new BorrowTicketDetailPdfExporter(details);
    exporter.export(response);
}

}
