package com.github.wilsongayle.book_tracker.service;

import com.github.wilsongayle.book_tracker.entity.BookRelationship;
import com.github.wilsongayle.book_tracker.repository.BookRelationshipRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BookRelationshipService {
    private final BookRelationshipRepository bookRelationshipRepository;

    public BookRelationshipService(BookRelationshipRepository bookRelationshipRepository) {
        this.bookRelationshipRepository = bookRelationshipRepository;
    }

    public List<BookRelationship> getAllBookRelationships() {
        return bookRelationshipRepository.findAll();
    }

    public BookRelationship createBookRelationship(BookRelationship relationship) {
        return bookRelationshipRepository.save(relationship);
    }

    public Optional<BookRelationship> getBookRelationshipById(UUID id) {
        return bookRelationshipRepository.findById(id);
    }

    public boolean deleteBookRelationshipById(UUID id) {
        if (!bookRelationshipRepository.existsById(id)) {
            return false;
        }
        bookRelationshipRepository.deleteById(id);
        return true;
    }

}
