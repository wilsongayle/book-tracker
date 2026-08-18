package com.github.wilsongayle.book_tracker.repository;

import com.github.wilsongayle.book_tracker.entity.ReadingEntry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ReadingEntryRepository extends JpaRepository<ReadingEntry, UUID> {
}
