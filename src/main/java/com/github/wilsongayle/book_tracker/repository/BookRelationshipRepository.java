package com.github.wilsongayle.book_tracker.repository;

import com.github.wilsongayle.book_tracker.entity.BookRelationship;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BookRelationshipRepository extends JpaRepository<BookRelationship, UUID> {
}
