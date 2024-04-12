package com.example.ota;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
public class Service {

    // READING JSON FILE
    public List<Notes> readNotesFromFile() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            InputStream inputStream = getClass().getResourceAsStream("/data/notes.json");
            if (inputStream != null) {
                List<Notes> notes = objectMapper.readValue(inputStream, new TypeReference<>() {});
                return notes;
            } else {
                System.err.println("File not found or unable to read: /data/notes.json");
                return null;
            }
        } catch (IOException e) {
            System.err.println("Error reading JSON file:");
            e.printStackTrace();
            return null;
        }
    }

    // Helper method to write notes to the JSON file
    private void writeNotesToFile(List<Notes> notes) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File("src/main/resources/data/notes.json");
        objectMapper.writeValue(file, notes);
    }

    // POST NEW NOTE
    public List<Notes> postNotes(List<Notes> newNotes) {
        try {
            List<Notes> existingNotes = readNotesFromFile();
            if (existingNotes == null) {
                existingNotes = newNotes;
            } else {
                existingNotes.addAll(newNotes);
            }
            writeNotesToFile(existingNotes);
            return existingNotes; // Return the updated list of notes
        } catch (IOException e) {
            System.err.println("Error writing JSON file: " + e.getMessage());
            e.printStackTrace();
            return null; // Return null if an error occurs
        }
    }

    // GET ALL NOTES
    public Optional<List<Notes>> getNotes() {
        List<Notes> notes = readNotesFromFile();
        if (notes == null || notes.isEmpty()) {
            return Optional.empty(); // Return an empty optional if there are no notes
        } else {
            return Optional.of(notes); // Return an optional containing the list of notes
        }
    }


    // GET NOTES BY ID
    public Optional<Notes> getNotesById(String id) {
        Optional optional = Optional.empty();
        for(Notes note: readNotesFromFile()){
            if(note.getId().equals(id)){
                optional = Optional.of(note);
                return optional;
            }
        }
        return optional;
    }

    // EDIT NOTES
    public String putNotesById(String id, Notes notes) {
        try {
            List<Notes> existingNotes = readNotesFromFile();
            String response = "";
            if (existingNotes == null) {
                response = "The notes are empty.";
                return response;
            } else {
                int indexToEdit = -1;
                for (int i = 0; i < existingNotes.size(); i++) {
                    Notes note = existingNotes.get(i);
                    if (note.getId().equals(id)) {
                        indexToEdit = i; // Store the index to remove
                        break; // Exit the loop once the note is found
                    }
                }
                if (indexToEdit != -1) {
                    existingNotes.remove(indexToEdit); // Remove the note from the list
                    existingNotes.add(notes); // Remove the note from the list
                    response = "The note with the " + id + " ID has been successfully edited.";
                    writeNotesToFile(existingNotes); // Write the updated list to the file
                    return response;
                } else {
                    // If the note with the specified ID was not found
                    response = "The note with the " + id + " ID was not found.";
                    return response;
                }
            }
        } catch (IOException e) {
            System.err.println("Error writing JSON file: " + e.getMessage());
            e.printStackTrace();
            return "An error occurred while processing the request.";
        }
    }

    // DELETE NOTES BY ID
    public String deleteNotesById(String id) {
        try {
            List<Notes> existingNotes = readNotesFromFile();
            String response = "";
            if (existingNotes == null) {
                response = "The notes are empty.";
                return response;
            } else {
                int indexToRemove = -1;
                for (int i = 0; i < existingNotes.size(); i++) {
                    Notes note = existingNotes.get(i);
                    if (note.getId().equals(id)) {
                        indexToRemove = i; // Store the index to remove
                        break; // Exit the loop once the note is found
                    }
                }
                if (indexToRemove != -1) {
                    existingNotes.remove(indexToRemove); // Remove the note from the list
                    response = "The note with the " + id + " ID has been successfully removed.";
                    writeNotesToFile(existingNotes); // Write the updated list to the file
                    return response;
                } else {
                    // If the note with the specified ID was not found
                    response = "The note with the " + id + " ID was not found.";
                    return response;
                }
            }
        } catch (IOException e) {
            System.err.println("Error writing JSON file: " + e.getMessage());
            e.printStackTrace();
            return "An error occurred while processing the request.";
        }
    }


}
