package com.github.wilsongayle.book_tracker.service;

import com.github.wilsongayle.book_tracker.entity.Book;
import com.github.wilsongayle.book_tracker.entity.ListEntry;
import com.github.wilsongayle.book_tracker.exception.InvalidRequestException;
import com.github.wilsongayle.book_tracker.repository.BookRepository;
import com.github.wilsongayle.book_tracker.repository.ListEntryRepository;
import com.github.wilsongayle.book_tracker.util.RepositoryLookup;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ListEntryService {
    private final ListEntryRepository listEntryRepository;
    private final BookRepository bookRepository;

    public ListEntryService(ListEntryRepository listEntryRepository, BookRepository bookRepository) {
        this.listEntryRepository = listEntryRepository;
        this.bookRepository = bookRepository;
    }

    public List<ListEntry> getAllListEntries() {
        return listEntryRepository.findAll();
    }

    public ListEntry createListEntry(ListEntry entry) {
        Book book = entry.getBook();
        if (book == null) {
            throw new InvalidRequestException("A book is required to create a list entry");
        }
        Book fullBook = RepositoryLookup.resolveOrThrow(bookRepository, book.getId());
        entry.setBook(fullBook);
        return listEntryRepository.save(entry);
    }

    public Optional<ListEntry> getListEntryById(UUID id) {
        return listEntryRepository.findById(id);
    }

    public boolean deleteListEntryById(UUID id) {
        if (!listEntryRepository.existsById(id)) {
            return false;
        }
        listEntryRepository.deleteById(id);
        return true;
    }
}
