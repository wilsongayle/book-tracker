package com.github.wilsongayle.book_tracker.controller;

import com.github.wilsongayle.book_tracker.entity.ContributorRelationship;
import com.github.wilsongayle.book_tracker.service.ContributorRelationshipService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/contributor-relationships")
public class ContributorRelationshipController {
    private final ContributorRelationshipService contributorRelationshipService;

    public ContributorRelationshipController(ContributorRelationshipService contributorRelationshipService) {
        this.contributorRelationshipService = contributorRelationshipService;
    }

    @GetMapping
    public List<ContributorRelationship> getAllContributorRelationships() {
        return contributorRelationshipService.getAllContributorRelationships();
    }

    @PostMapping
    public ContributorRelationship createContributorRelationship(@RequestBody ContributorRelationship relationship) {
        return contributorRelationshipService.createContributorRelationship(relationship);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContributorRelationship> getContributorRelationshipById(@PathVariable UUID id) {
        return contributorRelationshipService.getContributorRelationshipById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContributorRelationshipById(@PathVariable UUID id) {
        boolean deleted = contributorRelationshipService.deleteContributorRelationshipById(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
