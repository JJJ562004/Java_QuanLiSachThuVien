package com.example.library.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class BorrowTicketDetail {

    @Id
    private String id;

    @ManyToOne
    @JoinColumn(name = "borrow_ticket_id", nullable = false)
    private BorrowTicket borrowTicket;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    public BorrowTicketDetail() {
    }

    public BorrowTicketDetail(String id, BorrowTicket borrowTicket, Book book) {
        this.id = id;
        this.borrowTicket = borrowTicket;
        this.book = book;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BorrowTicket getBorrowTicket() {
        return borrowTicket;
    }

    public void setBorrowTicket(BorrowTicket borrowTicket) {
        this.borrowTicket = borrowTicket;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}
