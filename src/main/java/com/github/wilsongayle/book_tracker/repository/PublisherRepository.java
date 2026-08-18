package com.github.wilsongayle.book_tracker.repository;

import com.github.wilsongayle.book_tracker.entity.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PublisherRepository extends JpaRepository<Publisher, UUID> {
}
