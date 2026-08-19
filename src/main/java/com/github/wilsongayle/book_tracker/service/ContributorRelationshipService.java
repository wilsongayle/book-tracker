package com.github.wilsongayle.book_tracker.service;

import com.github.wilsongayle.book_tracker.entity.ContributorRelationship;
import com.github.wilsongayle.book_tracker.repository.ContributorRelationshipRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

    public Optional<ContributorRelationship> getContributorRelationshipById(UUID id) {
        return contributorRelationshipRepository.findById(id);
    }

    public boolean deleteContributorRelationshipById(UUID id) {
        if (!contributorRelationshipRepository.existsById(id)) {
            return false;
        }
        contributorRelationshipRepository.deleteById(id);
        return true;
    }
}
