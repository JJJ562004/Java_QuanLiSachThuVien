package com.example.library.controller;

import com.example.library.model.BorrowTicket;
import com.example.library.model.Reader;
import com.example.library.service.BorrowTicketService;
import com.example.library.service.ReaderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

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
        borrowTicket.setBorrowDate(LocalDate.now());
        borrowTicketService.saveBorrowTicket(borrowTicket);
        return "redirect:/borrow-tickets";
    }
    // 4. Form sửa phiếu mượn
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        BorrowTicket ticket = borrowTicketService.getBorrowTicketById(id);
        if (ticket == null) {
            return "redirect:/borrow-tickets";
        }
        model.addAttribute("borrowTicket", ticket);
        model.addAttribute("readers", readerService.getAllReaders());
        return "edit-borrow-ticket";
    }

    // 5. Cập nhật phiếu mượn
    @PostMapping("/edit/{id}")
    public String updateBorrowTicket(@PathVariable Long id, @ModelAttribute BorrowTicket borrowTicket) {
        borrowTicket.setId(id);
        borrowTicketService.saveBorrowTicket(borrowTicket);
        return "redirect:/borrow-tickets";
    }

    // 6. Xóa phiếu mượn
    @GetMapping("/delete/{id}")
    public String deleteBorrowTicket(@PathVariable Long id) {
        borrowTicketService.deleteBorrowTicket(id);
        return "redirect:/borrow-tickets";
    }
}
