package com.github.wilsongayle.book_tracker.service;

import com.github.wilsongayle.book_tracker.entity.Book;
import com.github.wilsongayle.book_tracker.entity.ReadingEntry;
import com.github.wilsongayle.book_tracker.entity.ReadingStatus;
import com.github.wilsongayle.book_tracker.exception.InvalidRequestException;
import com.github.wilsongayle.book_tracker.repository.BookRepository;
import com.github.wilsongayle.book_tracker.repository.ReadingEntryRepository;
import com.github.wilsongayle.book_tracker.util.RepositoryLookup;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ReadingEntryService {
    private final ReadingEntryRepository readingEntryRepository;
    private final BookRepository bookRepository;

    public ReadingEntryService(ReadingEntryRepository readingEntryRepository, BookRepository bookRepository) {
        this.readingEntryRepository = readingEntryRepository;
        this.bookRepository = bookRepository;
    }

    public List<ReadingEntry> getAllReadingEntries() {
        return readingEntryRepository.findAll();
    }

    public ReadingEntry createReadingEntry(ReadingEntry entry) {
        Book book = entry.getBook();
        if (book == null) {
            throw new InvalidRequestException("A book is required to create a reading entry");
        }
        Book fullBook = RepositoryLookup.resolveOrThrow(bookRepository, book.getId());
        entry.setBook(fullBook);

        ReadingEntry savedEntry = readingEntryRepository.save(entry);

        if (entry.getReadingStatus() == ReadingStatus.COMPLETED && entry.getRating() != null) {
            fullBook.setRating(entry.getRating());
            bookRepository.save(fullBook);
        }

        return savedEntry;
    }

    public Optional<ReadingEntry> getReadingEntryById(UUID id) {
        return readingEntryRepository.findById(id);
    }

    public boolean deleteReadingEntryById(UUID id) {
        if (!readingEntryRepository.existsById(id)) {
            return false;
        }
        readingEntryRepository.deleteById(id);
        return true;
    }
}
