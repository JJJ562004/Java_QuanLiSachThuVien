package com.example.library.repository;

import com.example.library.model.BorrowTicketDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BorrowTicketDetailRepository extends JpaRepository<BorrowTicketDetail, Long> {
}
