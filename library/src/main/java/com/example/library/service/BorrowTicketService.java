package com.example.library.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.library.model.BorrowTicket;
import com.example.library.repository.BorrowTicketRepository;

@Service
public class BorrowTicketService {
    @Autowired
    private BorrowTicketRepository repository;

    public List<BorrowTicket> getAllBorrowTickets() {
        return repository.findAll();
    }

    public BorrowTicket getBorrowTicketById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public BorrowTicket saveBorrowTicket(BorrowTicket borrowTicket) {
        return repository.save(borrowTicket);
    }

    public void deleteBorrowTicket(Long id) {
        repository.deleteById(id);
    }

    // Tìm phiếu mượn theo tên bạn đọc
    //Trả về tất cả nếu keyword trống, hoặc kết quả lọc.
    public List<BorrowTicket> searchBorrowTickets(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return repository.findAll();
        }
        return repository.findByReaderNameContainingIgnoreCase(
            keyword
        );
    }
}
