package com.takeanote.note.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.takeanote.note.persistence.model.Note;

public interface INoteRepository extends JpaRepository<Note, Integer> {

}
