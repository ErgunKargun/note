package com.takeanote.note.exception;

public class NoteIdMismatchException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8345543736742986342L;

	public NoteIdMismatchException(int id) {
		super("Mismatch note id: " + id);
	}
}
