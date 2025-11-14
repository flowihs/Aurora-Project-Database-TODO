package com.example.demo.note.controller;

import com.example.demo.note.dto.NoteResponse;
import com.example.demo.note.service.NoteService;
import com.example.demo.user.entity.User;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import com.example.demo.note.dto.CreateNoteRequest;

@WebMvcTest(NoteController.class)
@AutoConfigureMockMvc(addFilters = false)
public class NoteControllerTest {

   @Autowired
   private MockMvc mockMvc;

   @MockitoBean
   private User user;

   @MockitoBean
   private NoteService noteService;

   @SneakyThrows
   @Test
   void getUserNotesTest() {
       List<NoteResponse> mockNotes = List.of(
           NoteResponse.builder()
               .id(1L)
               .name("Note 1")
               .content("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.")
               .build(),
           NoteResponse.builder()
               .id(2L)
               .name("Note 2")
               .content("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.")
               .build()
       );

       when(noteService.getUserNotesResponse(any(User.class)))
           .thenReturn(mockNotes);

       mockMvc.perform(get("/api/notes")
           .contentType(MediaType.APPLICATION_JSON))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.length()").value(2))
           .andExpect(jsonPath("$[0].id").value(1))
           .andExpect(jsonPath("$[0].name").value("Note 1"))
           .andExpect(jsonPath("$[1].id").value(2))
           .andExpect(jsonPath("$[1].name").value("Note 2"));
   }

   @SneakyThrows
   @Test
   void deleteNoteTest() {
       mockMvc.perform(delete("/api/notes/1")
           .contentType(MediaType.APPLICATION_JSON))
           .andExpect(status().isNoContent());
   }

   @SneakyThrows
   @Test
   void getNoteByIdTest() {
       NoteResponse mockResponse = NoteResponse.builder()
           .id(1L)
           .name("Note")
           .content("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.")
           .build();

       when(noteService.getNoteById(anyLong(), any(User.class)))
           .thenReturn(mockResponse);

       mockMvc.perform(get("/api/notes/1")
           .contentType(MediaType.APPLICATION_JSON))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.id").value(1))
           .andExpect(jsonPath("$.name").value("Note"))
           .andExpect(jsonPath("$.content").value("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."));
   }

   @SneakyThrows
   @Test
   void createNoteTest() {
       NoteResponse mockResponse = NoteResponse.builder()
           .id(1L)
           .name("Note one")
           .content("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.")
           .build();

       when(noteService.createNote(any(User.class), any(CreateNoteRequest.class)))
           .thenReturn(mockResponse);

       mockMvc.perform(post("/api/notes")
           .contentType(MediaType.APPLICATION_JSON)
           .content("""
               {
                   "name": "Note one",
                   "content": "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."
               }"""))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.id").value(1))
           .andExpect(jsonPath("$.name").value("Note one"))
           .andExpect(jsonPath("$.content").value("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."));
   }
}
