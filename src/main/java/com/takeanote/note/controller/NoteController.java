package com.takeanote.note.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.takeanote.note.hateoas.NoteModelAssembler;
import com.takeanote.note.persistence.model.Note;
import com.takeanote.note.service.NoteService;

@RestController
@RequestMapping("/api/v1.0/notes")
public class NoteController {

	@Autowired
	NoteService noteService;

	@Autowired
	NoteModelAssembler assembler;

	@GetMapping
	public CollectionModel<EntityModel<Note>> all() {

		List<Note> notes = noteService.findAll();
		return assembler.toCollectionModel(notes);
	}

	@GetMapping("/{id}")
	public EntityModel<Note> one(@PathVariable int id) {

		Note note = noteService.findById(id);
		return assembler.toModel(note);
	}

	@PostMapping
	public ResponseEntity<?> createNote(@RequestBody Note newNote) {

		EntityModel<Note> entityModel = assembler.toModel(noteService.save(newNote));
		return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateNote(@RequestBody Note newNote, @PathVariable int id) {

		EntityModel<Note> entityModel = assembler.toModel(noteService.update(newNote, id));
		return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
	}

	@PutMapping("/{id}/set-passive")
	public ResponseEntity<?> setPassiveNote(@PathVariable int id) {

		EntityModel<Note> entityModel = assembler.toModel(noteService.setPassive(id));
		return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteNote(@PathVariable int id) {

		noteService.deleteById(id);
		return ResponseEntity.noContent().build();
	}
}
