package com.example.library.service;

import com.example.library.model.BorrowTicketDetail;
import com.example.library.repository.BorrowTicketDetailRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BorrowTicketDetailService {

    private final BorrowTicketDetailRepository repository;

    public BorrowTicketDetailService(BorrowTicketDetailRepository repository) {
        this.repository = repository;
    }

    public List<BorrowTicketDetail> getAllDetails() {
        return repository.findAll();
    }

    public BorrowTicketDetail getById(String id) {
        return repository.findById(id).orElse(null);
    }

    public BorrowTicketDetail save(BorrowTicketDetail detail) {
        return repository.save(detail);
    }

    public void deleteById(String id) {
        repository.deleteById(id);
    }
}

