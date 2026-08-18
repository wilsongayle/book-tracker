package com.github.wilsongayle.book_tracker.repository;

import com.github.wilsongayle.book_tracker.entity.Contributor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ContributorRepository extends JpaRepository<Contributor, UUID> {
}
