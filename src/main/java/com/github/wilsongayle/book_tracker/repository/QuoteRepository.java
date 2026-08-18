package com.github.wilsongayle.book_tracker.repository;

import com.github.wilsongayle.book_tracker.entity.Quote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface QuoteRepository extends JpaRepository<Quote, UUID> {
}
