package com.takeanote.note.exception.handler;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.takeanote.note.exception.NoteIdMismatchException;
import com.takeanote.note.exception.NoteNotFoundException;

@ControllerAdvice
public class NoteExceptionHandler extends ResponseEntityExceptionHandler {	
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handler(Exception ex, WebRequest request) {
		return handleExceptionInternal(ex, ex.getLocalizedMessage(), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
	}
	
	@ExceptionHandler(NoteNotFoundException.class)
	protected ResponseEntity<Object> noteNotFoundHandler(Exception ex, WebRequest request) {
		return handleExceptionInternal(ex, ex.getLocalizedMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND, request);
	}
	
	@ExceptionHandler(NoteIdMismatchException.class)
	public ResponseEntity<Object> noteIdMismatchHandler(Exception ex, WebRequest request) {
		return handleExceptionInternal(ex, ex.getLocalizedMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}
}