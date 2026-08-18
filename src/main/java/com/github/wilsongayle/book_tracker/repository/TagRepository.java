package com.github.wilsongayle.book_tracker.repository;

import com.github.wilsongayle.book_tracker.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TagRepository extends JpaRepository<Tag, UUID> {
}
