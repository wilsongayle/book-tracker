package com.github.wilsongayle.book_tracker.service;

import com.github.wilsongayle.book_tracker.entity.Book;
import com.github.wilsongayle.book_tracker.entity.ReadingEntry;
import com.github.wilsongayle.book_tracker.entity.ReadingStatus;
import com.github.wilsongayle.book_tracker.repository.BookRepository;
import com.github.wilsongayle.book_tracker.repository.ReadingEntryRepository;
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
        ReadingEntry savedEntry = readingEntryRepository.save(entry);
        Integer entryRating = savedEntry.getRating();

        if(entry.getReadingStatus() == ReadingStatus.COMPLETED && entryRating != null) {
            Book book = bookRepository.findById(savedEntry.getBook().getId())
                    .orElseThrow(() -> new RuntimeException("Book not found"));
            book.setRating(entryRating);
            bookRepository.save(book);
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
