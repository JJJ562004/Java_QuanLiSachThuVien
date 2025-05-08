package com.example.library.repository;

import com.example.library.model.BorrowTicket;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BorrowTicketRepository extends JpaRepository<BorrowTicket, Long> {
    List<BorrowTicket> findByReaderNameContainingIgnoreCase(
        String name);
}
