package com.github.wilsongayle.book_tracker.service;

import com.github.wilsongayle.book_tracker.entity.Book;
import com.github.wilsongayle.book_tracker.entity.Contributor;
import com.github.wilsongayle.book_tracker.entity.ContributorRelationship;
import com.github.wilsongayle.book_tracker.exception.InvalidRequestException;
import com.github.wilsongayle.book_tracker.repository.BookRepository;
import com.github.wilsongayle.book_tracker.repository.ContributorRelationshipRepository;
import com.github.wilsongayle.book_tracker.repository.ContributorRepository;
import com.github.wilsongayle.book_tracker.util.RepositoryLookup;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ContributorRelationshipService {
    private final ContributorRelationshipRepository contributorRelationshipRepository;
    private final ContributorRepository contributorRepository;
    private final BookRepository bookRepository;

    public ContributorRelationshipService(ContributorRelationshipRepository contributorRelationshipRepository, ContributorRepository contributorRepository, BookRepository bookRepository) {
        this.contributorRelationshipRepository = contributorRelationshipRepository;
        this.contributorRepository = contributorRepository;
        this.bookRepository = bookRepository;
    }

    public List<ContributorRelationship> getAllContributorRelationships() {
        return contributorRelationshipRepository.findAll();
    }

    public ContributorRelationship createContributorRelationship(ContributorRelationship relationship) {
        Contributor contributor = relationship.getContributor();
        if (contributor == null) {
            throw new InvalidRequestException("A contributor is required to create a contributor relationship");
        }
        Contributor fullContributor = RepositoryLookup.resolveOrThrow(contributorRepository, contributor.getId());
        relationship.setContributor(fullContributor);
        Book book = relationship.getBook();
        if (book == null) {
            throw new InvalidRequestException("A book is required to create a contributor relationship");
        }
        Book fullBook = RepositoryLookup.resolveOrThrow(bookRepository, book.getId());
        relationship.setBook(fullBook);
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
