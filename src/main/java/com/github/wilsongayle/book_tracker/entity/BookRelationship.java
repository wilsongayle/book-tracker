package com.github.wilsongayle.book_tracker.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

@Entity
public class BookRelationship {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "book_a_id")
    private Book bookA;

    @ManyToOne
    @JoinColumn(name = "book_b_id")
    private Book bookB;

    @NotNull
    @Enumerated(EnumType.STRING)
    private BookRelationshipType bookRelationshipType;

    public BookRelationship() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Book getBookA() {
        return bookA;
    }

    public void setBookA(Book bookA) {
        this.bookA = bookA;
    }

    public Book getBookB() {
        return bookB;
    }

    public void setBookB(Book bookB) {
        this.bookB = bookB;
    }

    public BookRelationshipType getBookRelationshipType() {
        return bookRelationshipType;
    }

    public void setBookRelationshipType(BookRelationshipType bookRelationshipType) {
        this.bookRelationshipType = bookRelationshipType;
    }
}
