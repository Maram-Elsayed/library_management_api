package com.example.librarymanagementsystem.controller;

import com.example.librarymanagementsystem.dto.BorrowBookInfo;
import com.example.librarymanagementsystem.dto.BorrowRecordDto;
import com.example.librarymanagementsystem.dto.ReturnBookInfo;
import com.example.librarymanagementsystem.service.BorrowRecordService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class BorrowRecordControllerTest {

    @Mock
    private BorrowRecordService borrowRecordService;

    @InjectMocks
    private BorrowRecordController borrowRecordController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(borrowRecordController).build();
    }

    @Test
    void testBorrowBook() throws Exception {
        BorrowRecordDto borrowRecord = new BorrowRecordDto(1L, null, null, 20200801L, null);

        when(borrowRecordService.borrowBook(any(Long.class), any(Long.class), any(BorrowBookInfo.class)))
                .thenReturn(borrowRecord);

        mockMvc.perform(post("/api/borrow/1/patron/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"borrowDate\":20200801}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.borrowDate").value(20200801));
    }

    @Test
    void testReturnBook() throws Exception {
        BorrowRecordDto borrowRecord = new BorrowRecordDto(1L, null, null, 20200801L, 20200810L);

        when(borrowRecordService.returnBook(any(Long.class), any(Long.class), any(ReturnBookInfo.class)))
                .thenReturn(borrowRecord);

        mockMvc.perform(put("/api/return/1/patron/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"returnDate\":20200810}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.returnDate").value(20200810));
    }
}
