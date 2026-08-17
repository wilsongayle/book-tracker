package com.github.wilsongayle.book_tracker.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class Quote {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    private String content;

    private String pageRef;

    public Quote() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPageRef() {
        return pageRef;
    }

    public void setPageRef(String pageRef) {
        this.pageRef = pageRef;
    }
}
