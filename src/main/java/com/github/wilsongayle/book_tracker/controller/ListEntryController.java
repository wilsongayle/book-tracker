package com.github.wilsongayle.book_tracker.controller;

import com.github.wilsongayle.book_tracker.entity.ListEntry;
import com.github.wilsongayle.book_tracker.service.ListEntryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/list-entries")
public class ListEntryController {
    private final ListEntryService listEntryService;

    public ListEntryController(ListEntryService listEntryService) {
        this.listEntryService = listEntryService;
    }

    @GetMapping
    public List<ListEntry> getAllListEntries() {
        return listEntryService.getAllListEntries();
    }

    @PostMapping
    public ListEntry createListEntry(@RequestBody ListEntry listEntry) {
        return listEntryService.createListEntry(listEntry);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ListEntry> getListEntryById(@PathVariable UUID id) {
        return listEntryService.getListEntryById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteListEntry(@PathVariable UUID id) {
        boolean deleted = listEntryService.deleteListEntryById(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
