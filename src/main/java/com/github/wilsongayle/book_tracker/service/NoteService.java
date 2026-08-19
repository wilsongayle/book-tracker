package com.github.wilsongayle.book_tracker.service;

import com.github.wilsongayle.book_tracker.entity.Note;
import com.github.wilsongayle.book_tracker.repository.NoteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class NoteService {
    private final NoteRepository noteRepository;

    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public List<Note> getAllNotes() {
        return noteRepository.findAll();
    }

    public Note createNote(Note note) {
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
