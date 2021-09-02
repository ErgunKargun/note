package com.takeanote.note.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.takeanote.note.exception.NoteIdMismatchException;
import com.takeanote.note.exception.NoteNotFoundException;
import com.takeanote.note.persistence.model.Note;
import com.takeanote.note.persistence.repository.INoteRepository;

@Service
public class NoteService {

	@Autowired
	INoteRepository noteRepo;

	public List<Note> findAll() {

		return noteRepo.findAll();
	}

	public Note findById(int id) {

		return noteRepo.findById(id).orElseThrow(() -> new NoteNotFoundException(id));
	}

	public Note save(Note newNote) {
		newNote.setCreatedDate(LocalDateTime.now());
		newNote.setIsActive(true);
		return noteRepo.save(newNote);
	}

	public Note update(Note newNote, int id) {

		if (newNote.getId() != id) {
			throw new NoteIdMismatchException(id);
		}
		noteRepo.findById(id).orElseThrow(() -> new NoteNotFoundException(id));
		newNote.setUpdatedDate(LocalDateTime.now());
		return noteRepo.save(newNote);
	}

	public Note setPassive(int id) {

		Note note = noteRepo.findById(id).orElseThrow(() -> new NoteNotFoundException(id));
		note.setIsActive(false);
		return noteRepo.save(note);
	}
	
	public void deleteById(int id) {

		noteRepo.deleteById(id);
	}
}
