package com.github.wilsongayle.book_tracker.repository;

import com.github.wilsongayle.book_tracker.entity.ContributorRelationship;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ContributorRelationshipRepository extends JpaRepository<ContributorRelationship, UUID> {
}
