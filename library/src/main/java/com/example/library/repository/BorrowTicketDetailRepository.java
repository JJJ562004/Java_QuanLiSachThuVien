package com.example.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.library.model.BorrowTicketDetail;

public interface BorrowTicketDetailRepository extends JpaRepository<BorrowTicketDetail, String> {
}
