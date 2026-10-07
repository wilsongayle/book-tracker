package com.github.wilsongayle.book_tracker.service;

import com.github.wilsongayle.book_tracker.entity.Book;
import com.github.wilsongayle.book_tracker.entity.Publisher;
import com.github.wilsongayle.book_tracker.entity.Tag;
import com.github.wilsongayle.book_tracker.exception.EntityNotFoundException;
import com.github.wilsongayle.book_tracker.repository.BookRepository;
import com.github.wilsongayle.book_tracker.repository.PublisherRepository;
import com.github.wilsongayle.book_tracker.repository.TagRepository;
import com.github.wilsongayle.book_tracker.util.RepositoryLookup;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final PublisherRepository publisherRepository;
    private final TagRepository tagRepository;
    private final TagService tagService;

    public BookService(
            BookRepository bookRepository,
            PublisherRepository publisherRepository,
            TagRepository tagRepository,
            TagService tagService
    ) {
        this.bookRepository = bookRepository;
        this.publisherRepository = publisherRepository;
        this.tagRepository = tagRepository;
        this.tagService = tagService;
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book createBook(Book book) {
        Publisher publisher = book.getPublisher();
        if (publisher != null) {
            Publisher fullPublisher = RepositoryLookup.resolveOrThrow(publisherRepository, publisher.getId());
            book.setPublisher(fullPublisher);
        }
        return bookRepository.save(book);
    }

    public Optional<Book> getBookById(UUID id) {
        return bookRepository.findById(id);
    }

    public boolean deleteBookById(UUID id) {
        if (!bookRepository.existsById(id)) {
            return false;
        }
        bookRepository.deleteById(id);
        return true;
    }

    public Book attachTag(UUID bookId, UUID tagId) {
        Book book =  RepositoryLookup.resolveOrThrow(bookRepository, bookId);
        Tag tag = RepositoryLookup.resolveOrThrow(tagRepository, tagId);
        List<Tag> currentTags = book.getTags();

        if (currentTags.stream().noneMatch(t -> t.getId().equals(tagId))) {
            currentTags.add(tag);
            bookRepository.save(book);
        }

        return book;
    }

    public Book detachTag(UUID bookId, UUID tagId) {
        Book book =  RepositoryLookup.resolveOrThrow(bookRepository, bookId);
        RepositoryLookup.resolveOrThrow(tagRepository, tagId);
        List<Tag> currentTags = book.getTags();

        if (currentTags.stream().noneMatch(t -> t.getId().equals(tagId))) {
            throw new EntityNotFoundException("Tag is not attached to this book");
        }

        currentTags.removeIf(t -> t.getId().equals(tagId));
        bookRepository.save(book);
        return book;
    }

    @Transactional
    public Book addTagToBook(UUID bookId, String name) {
        Tag tag = tagService.findOrCreateByName(name);
        return attachTag(bookId, tag.getId());
    }
}
