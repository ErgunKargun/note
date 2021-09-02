package com.takeanote.note;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import com.takeanote.note.persistence.model.Note;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class NoteAPITest {

	private static final String API_ROOT = "http://localhost:8080/api/v1.0/notes";

    @Test
    public void whenGetAllNotes_thenOK() {
    	
        final Response response = RestAssured.get(API_ROOT);
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
    }

    @Test
    public void whenGetCreatedNoteById_thenOK() {
    	
        final Note note = createRandomNote();
        final String location = createNoteAsUri(note);

        final Response response = RestAssured.get(location);
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        assertEquals(note.getTitle(), response.jsonPath().get("title"));
    }

	@Test
    public void whenGetNotExistNoteById_thenNotFound() {
		
        final Response response = RestAssured.get(API_ROOT + "/" + 1287);
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode());
    }

    @Test
    public void whenCreateNewNote_thenCreated() {
    	
        final Note note = createRandomNote();

        final Response response = RestAssured.given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(note)
            .post(API_ROOT);
        System.out.println("NOTE: " + note);
        System.out.println("RESPONSE:" + response.body().asPrettyString());
        assertEquals(HttpStatus.CREATED.value(), response.getStatusCode());
    }

    @Test
    public void whenInvalidNote_thenError() {
    	
        final Note note = createRandomNote();
        note.setTitle(null);

        final Response response = RestAssured.given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(note)
            .post(API_ROOT);
        
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatusCode());
    }

    @Test
    public void whenUpdateCreatedNote_thenUpdated() {
    	
        final Note note = createRandomNote();
        final String location = createNoteAsUri(note);

        //note.setId(Integer.parseInt(location.substring(location.length()-2)));
        note.setBody("newBody");
        note.setTitle("newTitle");
        
        Response response = RestAssured.given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(note)
            .put(location);
       
        System.out.println("LOCATION: " + location);
        //assertEquals(HttpStatus.CREATED.value(), response.getStatusCode());

        response = RestAssured.get(location);
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        //assertEquals("newTitle", response.jsonPath().get("title"));

    }

    @Test
    public void whenDeleteCreatedNote_thenOk() {

        final String location = createNoteAsUri(createRandomNote());

        Response response = RestAssured.delete(location);
        assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatusCode());

        response = RestAssured.get(location);
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode());
    }


    private Note createRandomNote() {

    	Note note = new Note();
    	note.setTitle("Title");
    	note.setBody("Body");
    	note.setCreatedDate(LocalDateTime.now());
    	note.setIsActive(true);
    	
		return note;
	}
    
    private String createNoteAsUri(Note note) {
    	
        final Response response = RestAssured.given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(note)
            .post(API_ROOT);
        return API_ROOT + "/" + response.jsonPath().get("id");
    }
}
