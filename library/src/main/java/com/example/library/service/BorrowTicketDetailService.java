package com.example.library.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.library.model.BorrowTicketDetail;
import com.example.library.repository.BorrowTicketDetailRepository;

@Service
public class BorrowTicketDetailService {

    @Autowired
    private BorrowTicketDetailRepository repository;

    public List<BorrowTicketDetail> getAll() {
        return repository.findAll();
    }

    public void save(BorrowTicketDetail detail) {
        repository.save(detail);
    }

    public BorrowTicketDetail getById(String id) {
        return repository.findById(id).orElse(null);
    }

    public void deleteById(String id) {
        repository.deleteById(id);
    }
    // Tìm chi tiết phiếu mượn theo tên sách
    //Trả về tất cả nếu keyword trống, hoặc kết quả lọc.
    public List<BorrowTicketDetail> searchBorrowTicketDetails(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return repository.findAll();
        }
        return repository.findByBookTitleContainingIgnoreCase(keyword);
    }
}
