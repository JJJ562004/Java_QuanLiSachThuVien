package com.example.library.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.library.model.BorrowTicketDetail;

public interface BorrowTicketDetailRepository extends JpaRepository<BorrowTicketDetail, String> {
     List<BorrowTicketDetail> findByBookTitleContainingIgnoreCase(
        String name);
}
