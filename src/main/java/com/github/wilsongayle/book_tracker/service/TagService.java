package com.github.wilsongayle.book_tracker.service;

import com.github.wilsongayle.book_tracker.entity.Tag;
import com.github.wilsongayle.book_tracker.exception.ConflictException;
import com.github.wilsongayle.book_tracker.exception.InvalidRequestException;
import com.github.wilsongayle.book_tracker.repository.BookRepository;
import com.github.wilsongayle.book_tracker.repository.TagRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TagService {

    private final TagRepository tagRepository;
    private final BookRepository bookRepository;

    public TagService(TagRepository tagRepository, BookRepository bookRepository) {
        this.tagRepository = tagRepository;
        this.bookRepository = bookRepository;
    }

    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }

    private String normalize(String name) {
        if (name == null || name.isBlank()) {
            throw new InvalidRequestException("A tag name is required to create a tag");
        }
        return name.trim();
    }

    public Tag createTag(Tag tag) {
        String tagName = tag.getName();
        String normalizedName = normalize(tagName);
        if (tagRepository.findByNameIgnoreCase(normalizedName).isPresent()) {
            throw new InvalidRequestException("A tag with this name already exists");
        }
        tag.setName(normalizedName);
        return tagRepository.save(tag);
    }

    public Tag findOrCreateByName(String name) {
        String normalizedName = normalize(name);
        return tagRepository.findByNameIgnoreCase(normalizedName)
                .orElseGet(() -> {
                    Tag newTag = new Tag();
                    newTag.setName(normalizedName);
                    return tagRepository.save(newTag);
                });
    }

    public Optional<Tag> getTagById(UUID id) {
        return tagRepository.findById(id);
    }

    public boolean deleteTagById(UUID id) {
        if (!tagRepository.existsById(id)) {
            return false;
        }
        if(bookRepository.existsByTagsId(id)) {
            throw new ConflictException("Tag is attached to one or more books and can't be deleted");
        }
        tagRepository.deleteById(id);
        return true;
    }
}
