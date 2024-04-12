package com.example.ota;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class Controller {

    @Autowired
    private Service service;

    //· POST /notes: Create a new note.
    @PostMapping("/notes")
    public ResponseEntity<List<Notes>> postNotes(@RequestBody List<Notes> notes) {
        List<Notes> updatedNotes = service.postNotes(notes);
        if (updatedNotes != null) {
            return ResponseEntity.ok(updatedNotes);
        } else {
            // If the operation fails to create the note, return a 404 Not Found response
            return ResponseEntity.notFound().build();
        }
    }

    //· GET /notes: Retrieve all notes.
    @GetMapping("/notes")
    public ResponseEntity<List<Notes>> getNotes(){
        Optional<List<Notes>> optionalNotes = service.getNotes();
        if (optionalNotes.isPresent()) {
            return ResponseEntity.ok(optionalNotes.get());
        } else {
            // Return a 404 Not Found response if the note is empty
            return ResponseEntity.notFound().build();
        }
    }

    //· GET /notes/:id: Retrieve a specific note by ID.
    @GetMapping("/notes/{id}")
    public ResponseEntity<Notes> getNotesById(@PathVariable(value="id") String id){
        Optional<Notes> optionalNotes = service.getNotesById(id);
        if (optionalNotes.isPresent()) {
            return ResponseEntity.ok(optionalNotes.get());
        } else {
            // Return a 404 Not Found response if the note with the specified ID doesn't exist
            return ResponseEntity.notFound().build();
        }
    }

    //· PUT /notes/:id: Update a specific note.
    @PutMapping("/notes/{id}")
    public ResponseEntity<String> putNotesById(@PathVariable(value="id") String id, @RequestBody Notes notes) {
        String response = service.putNotesById(id, notes);
        if (response.isEmpty()) {
            // Return a 404 Not Found response if the note with the specified ID doesn't exist
            return ResponseEntity.notFound().build();
        } else {
            // Return a 200 OK response with the message indicating the note has been deleted
            return ResponseEntity.ok(response);
        }
    }

    //· DELETE /notes/:id: Delete a specific note.
    @DeleteMapping("/notes/{id}")
    public ResponseEntity<String> deleteNotesById(@PathVariable(value="id") String id) {
        String response = service.deleteNotesById(id);
        if (response.isEmpty()) {
            // Return a 404 Not Found response if the note with the specified ID doesn't exist
            return ResponseEntity.notFound().build();
        } else {
            // Return a 200 OK response with the message indicating the note has been deleted
            return ResponseEntity.ok(response);
        }
    }
}
