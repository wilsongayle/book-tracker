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

    private void syncBookRating(ReadingEntry entry) {
        if (entry.getReadingStatus() == ReadingStatus.COMPLETED && entry.getRating() != null) {
            Book entryBook = entry.getBook();
            entryBook.setRating(entry.getRating());
            bookRepository.save(entryBook);
        }
    }

    public ReadingEntry createReadingEntry(ReadingEntry entry) {
        Book book = entry.getBook();
        if (book == null) {
            throw new InvalidRequestException("A book is required to create a reading entry");
        }
        Book fullBook = RepositoryLookup.resolveOrThrow(bookRepository, book.getId());
        entry.setBook(fullBook);

        ReadingEntry savedEntry = readingEntryRepository.save(entry);

        syncBookRating(entry);

        return savedEntry;
    }

    public ReadingEntry updateReadingEntry(UUID id, ReadingEntry partialUpdate) {
        ReadingEntry existing = RepositoryLookup.resolveOrThrow(readingEntryRepository, id);

        if (partialUpdate.getReadingStatus() != null) {
            existing.setReadingStatus(partialUpdate.getReadingStatus());
        }

        if (partialUpdate.getStartDate() != null) {
            existing.setStartDate(partialUpdate.getStartDate());
        }

        if (partialUpdate.getFinishDate() != null) {
            existing.setFinishDate(partialUpdate.getFinishDate());
        }

        if (partialUpdate.getRating() != null) {
            existing.setRating(partialUpdate.getRating());
        }

        if (partialUpdate.getNotes() != null) {
            existing.setNotes(partialUpdate.getNotes());
        }

        ReadingEntry savedEntry = readingEntryRepository.save(existing);
        syncBookRating(existing);

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
