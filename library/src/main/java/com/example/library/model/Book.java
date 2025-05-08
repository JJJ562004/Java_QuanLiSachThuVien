package com.example.library.model;

import jakarta.persistence.*;

@Entity
public class Book {
    @Id
    private String id;
    private String title;
    private String author;
    private String category;
    private int year;

    @ManyToOne
    @JoinColumn(name = "borrow_ticket_detail_id")
    private BorrowTicketDetail borrowTicketDetail;

    public Book() {
    }

    public Book(String author, String category, String id, String title, int year) {
        this.author = author;
        this.category = category;
        this.id = id;
        this.title = title;
        this.year = year;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public BorrowTicketDetail getBorrowTicketDetail() {
        return borrowTicketDetail;
    }

    public void setBorrowTicketDetail(BorrowTicketDetail borrowTicketDetail) {
        this.borrowTicketDetail = borrowTicketDetail;

    }
}
