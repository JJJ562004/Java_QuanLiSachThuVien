package com.example.library.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class BorrowTicket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate borrowDate;
    private LocalDate returnDate;

    @ManyToOne
    @JoinColumn(name = "reader_id")
    private Reader reader;

    @OneToMany(mappedBy = "borrowTicket", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BorrowTicketDetail> borrowTicketDetails = new ArrayList<>();

    public BorrowTicket() {
    }

    public BorrowTicket(Long id, LocalDate borrowDate, LocalDate returnDate, Reader reader) {
        this.id = id;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
        this.reader = reader;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(LocalDate borrowDate) {
        this.borrowDate = borrowDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public Reader getReader() {
        return reader;
    }

    public void setReader(Reader reader) {
        this.reader = reader;
    }

    public List<BorrowTicketDetail> getBorrowTicketDetails() {
        return borrowTicketDetails;
    }

    public void setBorrowTicketDetails(List<BorrowTicketDetail> borrowTicketDetails) {
        this.borrowTicketDetails = borrowTicketDetails;
    }

    public void addDetail(BorrowTicketDetail detail) {
        borrowTicketDetails.add(detail);
        detail.setBorrowTicket(this);
    }

    public void removeDetail(BorrowTicketDetail detail) {
        borrowTicketDetails.remove(detail);
        detail.setBorrowTicket(null);
    }
}
