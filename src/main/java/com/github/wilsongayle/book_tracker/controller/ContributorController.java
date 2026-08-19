package com.github.wilsongayle.book_tracker.controller;

import com.github.wilsongayle.book_tracker.entity.Contributor;
import com.github.wilsongayle.book_tracker.service.ContributorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/contributors")
public class ContributorController {
    private final ContributorService contributorService;

    public ContributorController(ContributorService contributorService) {
        this.contributorService = contributorService;
    }

    @GetMapping
    public List<Contributor> getAllContributors() {
        return contributorService.getAllContributors();
    }

    @PostMapping
    public Contributor createContributor(@RequestBody Contributor contributor) {
        return contributorService.createContributor(contributor);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Contributor> getContributorById(@PathVariable UUID id) {
        return contributorService.getContributorById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContributorById(@PathVariable UUID id) {
        boolean deleted = contributorService.deleteContributorById(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
