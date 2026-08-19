package com.github.wilsongayle.book_tracker.service;

import com.github.wilsongayle.book_tracker.entity.Contributor;
import com.github.wilsongayle.book_tracker.repository.ContributorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ContributorService {
    private final ContributorRepository contributorRepository;

    public ContributorService(ContributorRepository contributorRepository) {
        this.contributorRepository = contributorRepository;
    }

    public List<Contributor> getAllContributors() {
        return contributorRepository.findAll();
    }

    public Contributor createContributor(Contributor contributor) {
        return contributorRepository.save(contributor);
    }

    public Optional<Contributor> getContributorById(UUID id) {
        return contributorRepository.findById(id);
    }

    public boolean deleteContributorById(UUID id) {
        if (!contributorRepository.existsById(id)) {
            return false;
        }
        contributorRepository.deleteById(id);
        return true;
    }
}
