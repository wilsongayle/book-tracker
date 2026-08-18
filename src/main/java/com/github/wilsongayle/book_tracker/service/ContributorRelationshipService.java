package com.github.wilsongayle.book_tracker.service;

import com.github.wilsongayle.book_tracker.entity.ContributorRelationship;
import com.github.wilsongayle.book_tracker.repository.ContributorRelationshipRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContributorRelationshipService {
    private final ContributorRelationshipRepository contributorRelationshipRepository;

    public ContributorRelationshipService(ContributorRelationshipRepository contributorRelationshipRepository) {
        this.contributorRelationshipRepository = contributorRelationshipRepository;
    }

    public List<ContributorRelationship> getAllContributorRelationships() {
        return contributorRelationshipRepository.findAll();
    }

    public ContributorRelationship createContributorRelationship(ContributorRelationship relationship) {
        return contributorRelationshipRepository.save(relationship);
    }
}
