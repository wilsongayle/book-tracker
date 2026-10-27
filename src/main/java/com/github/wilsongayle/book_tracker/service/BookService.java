package com.github.wilsongayle.book_tracker.service;

import com.github.wilsongayle.book_tracker.entity.Book;
import com.github.wilsongayle.book_tracker.entity.Publisher;
import com.github.wilsongayle.book_tracker.entity.Tag;
import com.github.wilsongayle.book_tracker.exception.EntityNotFoundException;
import com.github.wilsongayle.book_tracker.exception.InvalidRequestException;
import com.github.wilsongayle.book_tracker.repository.BookRepository;
import com.github.wilsongayle.book_tracker.repository.PublisherRepository;
import com.github.wilsongayle.book_tracker.repository.TagRepository;
import com.github.wilsongayle.book_tracker.util.RepositoryLookup;
import com.github.wilsongayle.book_tracker.util.ValidateConstraints;
import jakarta.validation.Validator;
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
    private final Validator validator;

    public BookService(
            BookRepository bookRepository,
            PublisherRepository publisherRepository,
            TagRepository tagRepository,
            TagService tagService,
            Validator validator
    ) {
        this.bookRepository = bookRepository;
        this.publisherRepository = publisherRepository;
        this.tagRepository = tagRepository;
        this.tagService = tagService;
        this.validator = validator;
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book createBook(Book book) {
        Publisher publisher = book.getPublisher();
        if (publisher != null) {
            UUID publisherId = publisher.getId();

            if (publisherId == null) {
                throw new InvalidRequestException("Publisher id is required");
            }
            Publisher fullPublisher = RepositoryLookup.resolveOrThrow(publisherRepository, publisherId);
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

    public Book updateBook(UUID bookId, Book partialUpdate) {
        Book existing = RepositoryLookup.resolveOrThrow(bookRepository, bookId);

        if (partialUpdate.getTitle() != null) {
            existing.setTitle(partialUpdate.getTitle());
        }

        if (partialUpdate.getIsbn() != null) {
            existing.setIsbn(partialUpdate.getIsbn());
        }

        if (partialUpdate.getOriginalLanguage() != null) {
            existing.setOriginalLanguage(partialUpdate.getOriginalLanguage());
        }

        if (partialUpdate.getPurchaseLink() != null) {
            existing.setPurchaseLink(partialUpdate.getPurchaseLink());
        }

        if (partialUpdate.getCoverImagePath() != null) {
            existing.setCoverImagePath(partialUpdate.getCoverImagePath());
        }

        if (partialUpdate.getRating() != null) {
            existing.setRating(partialUpdate.getRating());
        }

        if (partialUpdate.getWouldRecommend() != null) {
            existing.setWouldRecommend(partialUpdate.getWouldRecommend());
        }

        if (partialUpdate.getPublisher() != null) {
            UUID publisherId = partialUpdate.getPublisher().getId();

            if (publisherId == null) {
                throw new InvalidRequestException("Publisher id is required");
            }
            Publisher publisher = RepositoryLookup.resolveOrThrow(publisherRepository, publisherId);
            existing.setPublisher(publisher);
        }

        ValidateConstraints.validateOrThrow(validator, existing);

        return bookRepository.save(existing);
    }
}
