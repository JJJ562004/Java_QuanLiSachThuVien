package com.example.library.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.library.export.BorrowTicketDetailPdfExporter;
import com.example.library.model.BorrowTicketDetail;
import com.example.library.service.BookService;
import com.example.library.service.BorrowTicketDetailService;
import com.example.library.service.BorrowTicketService;
import com.itextpdf.text.DocumentException;

import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/borrow-ticket-details")
public class BorrowTicketDetailController {

    @Autowired
    private BorrowTicketDetailService detailService;

    @Autowired
    private BorrowTicketService ticketService;

    @Autowired
    private BookService bookService;

    // @GetMapping
    // public String list(Model model) {
    //     model.addAttribute("details", detailService.getAll());
    //     return "borrow-ticket-details";
    // }
    // 1. Danh sách hoặc tìm kiếm phiếu mượn
    @GetMapping
    public String listBorrowTickets(@RequestParam(name = "keyword", required = false) String keyword, Model model) {
        List<BorrowTicketDetail> details = (keyword == null || keyword.isBlank())
                ? detailService.getAll()
                : detailService.searchBorrowTicketDetails(keyword);

        model.addAttribute("details", details);
        model.addAttribute("keyword", keyword);
        return "borrow-ticket-details";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("detail", new BorrowTicketDetail());
        model.addAttribute("borrowTickets", ticketService.getAllBorrowTickets());
        model.addAttribute("books", bookService.getAllBooks());
        return "add-borrow-ticket-detail";
    }
@PostMapping("/save")
public String save(@ModelAttribute BorrowTicketDetail detail) {
        detailService.save(detail);
        return "redirect:/borrow-ticket-details";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable String id, Model model) {
        BorrowTicketDetail detail = detailService.getById(id);
        model.addAttribute("detail", detail);
        model.addAttribute("borrowTickets", ticketService.getAllBorrowTickets());
        model.addAttribute("books", bookService.getAllBooks());
        return "edit-borrow-ticket-detail";
    }

    @PostMapping("/edit/{id}")
    public String editBorrowTicket(@PathVariable String id,
            @ModelAttribute BorrowTicketDetail borrowTicketDetail) {
        BorrowTicketDetail existing_borrow_ticket_detail = detailService.getById(id);
        if (existing_borrow_ticket_detail == null) {
            return "redirect:/borrow-ticket-details";
        }
        // Cập nhật các trường
        existing_borrow_ticket_detail.setBorrowTicket(borrowTicketDetail.getBorrowTicket());
        existing_borrow_ticket_detail.setBook(borrowTicketDetail.getBook());

        detailService.save(existing_borrow_ticket_detail);
        return "redirect:/borrow-ticket-details";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable String id) {
        detailService.deleteById(id);
        return "redirect:/borrow-ticket-details";
    }

      @GetMapping("/export/pdf")
public void exportBorrowTicketDetailsToPdf(HttpServletResponse response) throws DocumentException, IOException {
    response.setContentType("application/pdf");
    response.setHeader("Content-Disposition", "attachment; filename=borrow_ticket_details.pdf");

    List<BorrowTicketDetail> details = detailService.getAll();
    BorrowTicketDetailPdfExporter exporter = new BorrowTicketDetailPdfExporter(details);
    exporter.export(response);
}
}