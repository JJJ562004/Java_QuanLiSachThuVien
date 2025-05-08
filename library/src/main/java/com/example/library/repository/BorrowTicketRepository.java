package com.example.library.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.library.model.BorrowTicket;

public interface BorrowTicketRepository extends JpaRepository<BorrowTicket, String> {
    List<BorrowTicket> findByReaderNameContainingIgnoreCase(
        String name);
}
