package com.github.wilsongayle.book_tracker.controller;

import com.github.wilsongayle.book_tracker.entity.ReadingEntry;
import com.github.wilsongayle.book_tracker.service.ReadingEntryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/reading-entries")
public class ReadingEntryController {
    private final ReadingEntryService readingEntryService;

    public ReadingEntryController(ReadingEntryService readingEntryService) {
        this.readingEntryService = readingEntryService;
    }

    @GetMapping
    public List<ReadingEntry> getAllReadingEntries() {
        return readingEntryService.getAllReadingEntries();
    }

    @PostMapping
    public ReadingEntry createReadingEntry(@RequestBody ReadingEntry entry) {
        return readingEntryService.createReadingEntry(entry);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReadingEntry> getReadingEntryById(@PathVariable UUID id) {
        return readingEntryService.getReadingEntryById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReadingEntryById(@PathVariable UUID id) {
        boolean deleted = readingEntryService.deleteReadingEntryById(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
