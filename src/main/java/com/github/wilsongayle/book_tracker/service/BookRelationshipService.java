package com.github.wilsongayle.book_tracker.service;

import com.github.wilsongayle.book_tracker.entity.Book;
import com.github.wilsongayle.book_tracker.entity.BookRelationship;
import com.github.wilsongayle.book_tracker.exception.InvalidRequestException;
import com.github.wilsongayle.book_tracker.repository.BookRelationshipRepository;
import com.github.wilsongayle.book_tracker.repository.BookRepository;
import com.github.wilsongayle.book_tracker.util.RepositoryLookup;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BookRelationshipService {
    private final BookRelationshipRepository bookRelationshipRepository;
    private final BookRepository bookRepository;

    public BookRelationshipService(BookRelationshipRepository bookRelationshipRepository, BookRepository bookRepository) {
        this.bookRelationshipRepository = bookRelationshipRepository;
        this.bookRepository = bookRepository;
    }

    public List<BookRelationship> getAllBookRelationships() {
        return bookRelationshipRepository.findAll();
    }

    public BookRelationship createBookRelationship(BookRelationship relationship) {
        Book bookA = relationship.getBookA();
        Book bookB = relationship.getBookB();
        if (bookA == null || bookB == null) {
            throw new InvalidRequestException("Two books are required to create a book relationship");
        }
        if (bookA.getId().equals(bookB.getId())) {
            throw new InvalidRequestException("A book cannot be related to itself");
        }
        Book fullBookA = RepositoryLookup.resolveOrThrow(bookRepository, bookA.getId());
        relationship.setBookA(fullBookA);
        Book fullBookB = RepositoryLookup.resolveOrThrow(bookRepository, bookB.getId());
        relationship.setBookB(fullBookB);
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
