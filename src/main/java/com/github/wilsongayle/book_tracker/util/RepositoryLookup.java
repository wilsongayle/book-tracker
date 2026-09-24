package com.github.wilsongayle.book_tracker.util;

import com.github.wilsongayle.book_tracker.exception.EntityNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

public class RepositoryLookup {
    public static <T, ID> T resolveOrThrow(JpaRepository<T, ID> repository, ID id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Entity not found with id: " + id));
    }
}
