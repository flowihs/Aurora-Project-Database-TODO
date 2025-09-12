package com.example.demo.note.service;

import com.example.demo.exception.NoteNotFoundException;
import com.example.demo.note.dto.CreateNoteRequest;
import com.example.demo.note.dto.NoteResponse;
import com.example.demo.note.dto.UpdateNoteRequest;
import com.example.demo.note.entity.Note;
import com.example.demo.note.repository.NoteRepository;
import com.example.demo.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@CacheConfig(cacheNames = "notes")
public class NoteService {

    private final NoteRepository noteRepository;

    @Cacheable(key = "'user:' + #user.id + ':notes'")
    public List<Note> getUserNotes(User user) {
        return noteRepository.findByUser(user);
    }

    @Transactional
    @CacheEvict(key = "'user:' + #user.id + ':notes'")
    public NoteResponse createNote(User user, CreateNoteRequest request) {
        Note note = Note.builder()
                .name(request.getName())
                .content(request.getContent())
                .user(user)
                .build();

        Note savedNote = noteRepository.save(note);
        return NoteResponse.fromEntity(savedNote);
    }

    @Transactional
    @Caching(
            evict = {
                    @CacheEvict(key = "'user:' + #user.id + ':notes'"),
                    @CacheEvict(key = "'user:' + #user.id + ':note:' + #noteId")
            }
    )
    public NoteResponse updateNote(Long noteId, User user, UpdateNoteRequest request) {
        Note note = getNoteByIdAndUser(noteId, user);

        note.setName(request.getName());
        note.setContent(request.getContent());

        Note updatedNote = noteRepository.save(note);
        return NoteResponse.fromEntity(updatedNote);
    }

    @Transactional
    @Caching(
            evict = {
                    @CacheEvict(key = "'user:' + #user.id + ':notes'"),
                    @CacheEvict(key = "'user:' + #user.id + ':note:' + #noteId")
            }
    )
    public void deleteNote(Long noteId, User user) {
        Note note = getNoteByIdAndUser(noteId, user);
        noteRepository.delete(note);
    }

    @Cacheable(key = "'user:' + #user.id + ':note:' + #noteId")
    public Note getNoteById(Long noteId, User user) {
        return noteRepository.findByIdAndUser(noteId, user)
                .orElseThrow(NoteNotFoundException::new);
    }

    public List<NoteResponse> getUserNotesResponse(User user) {
        return getUserNotes(user).stream()
                .map(NoteResponse::fromEntity)
                .toList();
    }

    public NoteResponse getNoteResponseById(Long noteId, User user) {
        return NoteResponse.fromEntity(getNoteById(noteId, user));
    }

    private Note getNoteByIdAndUser(Long noteId, User user) {
        return getNoteById(noteId, user);
    }
}