package com.github.wilsongayle.book_tracker.service;

import com.github.wilsongayle.book_tracker.entity.Book;
import com.github.wilsongayle.book_tracker.entity.Note;
import com.github.wilsongayle.book_tracker.exception.EntityNotFoundException;
import com.github.wilsongayle.book_tracker.exception.InvalidRequestException;
import com.github.wilsongayle.book_tracker.repository.BookRepository;
import com.github.wilsongayle.book_tracker.repository.NoteRepository;
import com.github.wilsongayle.book_tracker.util.RepositoryLookup;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class NoteService {
    private final NoteRepository noteRepository;
    private final BookRepository bookRepository;

    public NoteService(NoteRepository noteRepository, BookRepository bookRepository) {
        this.noteRepository = noteRepository;
        this.bookRepository = bookRepository;
    }

    public List<Note> getAllNotes() {
        return noteRepository.findAll();
    }

    public Note createNote(Note note) {
        Book book = note.getBook();
        if (book == null) {
            throw new InvalidRequestException("A book is required to creata a note");
        }
        Book fullBook = RepositoryLookup.resolveOrThrow(bookRepository, book.getId());
        note.setBook(fullBook);
        return noteRepository.save(note);

    }

    public Optional<Note> getNoteById(UUID id) {
        return noteRepository.findById(id);
    }

    public boolean deleteNoteById(UUID id) {
        if (!noteRepository.existsById(id)) {
            return false;
        }
        noteRepository.deleteById(id);
        return true;
    }

}
