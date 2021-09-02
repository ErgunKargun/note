package com.takeanote.note.exception;

public class NoteNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5598597999976644221L;

	public NoteNotFoundException(int id) {
		super("Could not find note id: " + id);
	}
}
