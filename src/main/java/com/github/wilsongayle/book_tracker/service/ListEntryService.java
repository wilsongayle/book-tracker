package com.github.wilsongayle.book_tracker.service;

import com.github.wilsongayle.book_tracker.entity.ListEntry;
import com.github.wilsongayle.book_tracker.repository.ListEntryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListEntryService {
    private final ListEntryRepository listEntryRepository;

    public ListEntryService(ListEntryRepository listEntryRepository) {
        this.listEntryRepository = listEntryRepository;
    }

    public List<ListEntry> getAllListEntries() {
        return listEntryRepository.findAll();
    }

    public ListEntry createListEntry(ListEntry entry) {
        return listEntryRepository.save(entry);
    }
}
