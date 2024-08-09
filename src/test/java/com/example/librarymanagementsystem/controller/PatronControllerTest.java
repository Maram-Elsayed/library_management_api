package com.example.librarymanagementsystem.controller;

import com.example.librarymanagementsystem.dto.PatronCreateInfo;
import com.example.librarymanagementsystem.dto.PatronDto;
import com.example.librarymanagementsystem.service.PatronService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PatronControllerTest {
    @Mock
    private PatronService patronService;

    @InjectMocks
    private PatronController patronController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(patronController).build();
    }

    @Test
    void testFindAllPatrons() throws Exception {
        List<PatronDto> patrons = Arrays.asList(
                new PatronDto(1L, "Patron1", "123456789"),
                new PatronDto(2L, "Patron2", "987654321")
        );

        when(patronService.findAllPatrons()).thenReturn(patrons);

        mockMvc.perform(get("/api/patrons"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Patron1"))
                .andExpect(jsonPath("$[1].name").value("Patron2"));
    }

    @Test
    void testFindPatronById() throws Exception {
        PatronDto patron = new PatronDto(1L, "Name1", "123456789");

        when(patronService.findPatron(1L)).thenReturn(patron);

        mockMvc.perform(get("/api/patrons/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Name1"))
                .andExpect(jsonPath("$.phone").value("123456789"));
    }

    @Test
    void testCreatePatron() throws Exception {
        PatronDto patron = new PatronDto(1L, "Name1", "123456789");

        when(patronService.createPatron(any(PatronCreateInfo.class))).thenReturn(patron);

        mockMvc.perform(post("/api/patrons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Name1\",\"phone\":\"123456789\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Name1"))
                .andExpect(jsonPath("$.phone").value("123456789"));
    }

    @Test
    void testUpdatePatron() throws Exception {
        PatronDto updatedPatron = new PatronDto(1L, "NameUpdated", "123456789");

        when(patronService.updatePatron(any(Long.class), any(PatronCreateInfo.class))).thenReturn(updatedPatron);

        mockMvc.perform(put("/api/patrons/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"NameUpdated\",\"phone\":\"123456789\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("NameUpdated"))
                .andExpect(jsonPath("$.phone").value("123456789"));
    }

    @Test
    void testDeletePatron() throws Exception {
        mockMvc.perform(delete("/api/patrons/1"))
                .andExpect(status().isNoContent());
    }

}
