package com.github.wilsongayle.book_tracker.controller;

import com.github.wilsongayle.book_tracker.entity.BookRelationship;
import com.github.wilsongayle.book_tracker.service.BookRelationshipService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/book-relationships")
public class BookRelationshipController {

    private final BookRelationshipService bookRelationshipService;

    public BookRelationshipController(BookRelationshipService bookRelationshipService) {
        this.bookRelationshipService = bookRelationshipService;
    }

    @GetMapping
    public List<BookRelationship> getAllBookRelationships() {
        return bookRelationshipService.getAllBookRelationships();
    }

    @PostMapping
    public BookRelationship createBookRelationship(@RequestBody BookRelationship relationship) {
        return bookRelationshipService.createBookRelationship(relationship);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookRelationship> getBookRelationshipById(@PathVariable UUID id) {
        return bookRelationshipService.getBookRelationshipById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBookRelationshipById(@PathVariable UUID id) {
        boolean deleted = bookRelationshipService.deleteBookRelationshipById(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
