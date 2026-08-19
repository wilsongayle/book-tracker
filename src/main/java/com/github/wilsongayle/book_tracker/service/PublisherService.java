package com.github.wilsongayle.book_tracker.service;

import com.github.wilsongayle.book_tracker.entity.Publisher;
import com.github.wilsongayle.book_tracker.repository.PublisherRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PublisherService {
    private final PublisherRepository publisherRepository;

    public PublisherService(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    public List<Publisher> getAllPublishers() {
        return publisherRepository.findAll();
    }

    public Publisher createPublisher(Publisher publisher) {
        return publisherRepository.save(publisher);
    }

    public Optional<Publisher> getPublisherById(UUID id) {
        return publisherRepository.findById(id);
    }

    public boolean deletePublisherById(UUID id) {
        if (!publisherRepository.existsById(id)) {
            return false;
        }
        publisherRepository.deleteById(id);
        return true;
    }

}
