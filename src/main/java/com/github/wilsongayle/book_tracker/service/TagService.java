package com.github.wilsongayle.book_tracker.service;

import com.github.wilsongayle.book_tracker.entity.Tag;
import com.github.wilsongayle.book_tracker.repository.TagRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TagService {

    private final TagRepository tagRepository;

    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }

    public Tag createTag(Tag tag) {
        return tagRepository.save(tag);
    }

    public Optional<Tag> getTagById(UUID id) {
        return tagRepository.findById(id);
    }

    public boolean deleteTagById(UUID id) {
        if (!tagRepository.existsById(id)) {
            return false;
        }
        tagRepository.deleteById(id);
        return true;
    }
}
