package com.github.wilsongayle.book_tracker.repository;

import com.github.wilsongayle.book_tracker.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface BookRepository extends JpaRepository<Book, UUID> {
    List<Book> findByTitle(String title);
    List<Book> findByPublisherId(UUID publisherId);
    List<Book> findByWouldRecommendTrue();
}
