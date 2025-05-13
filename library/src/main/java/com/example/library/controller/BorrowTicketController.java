package com.example.library.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.library.export.BorrowTicketPdfExporter;
import com.example.library.model.BorrowTicket;
import com.example.library.service.BorrowTicketService;
import com.example.library.service.ReaderService;
import com.itextpdf.text.DocumentException;

import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/borrow-tickets")
public class BorrowTicketController {

    private final BorrowTicketService borrowTicketService;
    private final ReaderService readerService;

    public BorrowTicketController(BorrowTicketService borrowTicketService, ReaderService readerService) {
        this.borrowTicketService = borrowTicketService;
        this.readerService = readerService;
    }

    // 1. Danh sách hoặc tìm kiếm phiếu mượn
    @GetMapping
    public String listBorrowTickets(@RequestParam(name = "keyword", required = false) String keyword, Model model) {
        List<BorrowTicket> tickets = (keyword == null || keyword.isBlank())
                ? borrowTicketService.getAllBorrowTickets()
                : borrowTicketService.searchBorrowTickets(keyword);

        model.addAttribute("borrowTickets", tickets);
        model.addAttribute("keyword", keyword);
        return "borrow-tickets";
    }

    // 2. Form thêm phiếu mượn
    @GetMapping("/add")
    public String showCreateForm(Model model) {
        model.addAttribute("borrowTicket", new BorrowTicket());
        model.addAttribute("readers", readerService.getAllReaders());
        return "add-borrow-ticket";
    }

    // 3. Lưu phiếu mượn mới
    @PostMapping("/add")
    public String saveBorrowTicket(@ModelAttribute BorrowTicket borrowTicket) {
        borrowTicketService.saveBorrowTicket(borrowTicket);
        return "redirect:/borrow-tickets";
    }

    // 4. Form sửa phiếu mượn
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable String id, Model model) {
        BorrowTicket ticket = borrowTicketService.getBorrowTicketById(id);
        if (ticket == null) {
            return "redirect:/borrow-tickets";
        }
        model.addAttribute("borrowTicket", ticket);
        model.addAttribute("readers", readerService.getAllReaders());
        return "edit-borrow-ticket";
    }

    // 5. Cập nhật Phiếu Mượn
    @PostMapping("/edit/{id}")
    public String editBorrowTicket(@PathVariable String id,
            @ModelAttribute BorrowTicket borrowTicket) {
        BorrowTicket existing_borrow_ticket = borrowTicketService.getBorrowTicketById(id);
        if (existing_borrow_ticket == null) {
            return "redirect:/borrow-tickets";
        }
        // Cập nhật các trường
        existing_borrow_ticket.setBorrowDate(borrowTicket.getBorrowDate());
        existing_borrow_ticket.setReturnDate(borrowTicket.getReturnDate());
        existing_borrow_ticket.setReader(borrowTicket.getReader());

        borrowTicketService.saveBorrowTicket(existing_borrow_ticket);
        return "redirect:/borrow-tickets";
    }

    // 6. Xóa phiếu mượn
    @GetMapping("/delete/{id}")
    public String deleteBorrowTicket(@PathVariable String id) {
        borrowTicketService.deleteBorrowTicket(id);
        return "redirect:/borrow-tickets";
    }

    @GetMapping("/export/pdf")
    public void exportBorrowTicketsToPdf(HttpServletResponse response) throws DocumentException, IOException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=borrow_tickets.pdf");

        List<BorrowTicket> tickets = borrowTicketService.getAllBorrowTickets();
        BorrowTicketPdfExporter exporter = new BorrowTicketPdfExporter(tickets);
        exporter.export(response);
    }

}
