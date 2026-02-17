package com.example.controller;

import com.example.dto.BookDTO;
import com.example.dto.UpdateBookPriceRequest;
import com.example.exception.BookNotFoundException;
import com.example.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(LibraryController.class)
class LibraryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BookService bookService;

    @Test
    void testGetBookById() throws Exception {
        BookDTO bookDTO = new BookDTO(1L, "testName", 1, BigDecimal.valueOf(320));
        when(bookService.findById(1L))
                .thenReturn(bookDTO);

        mockMvc.perform(get("/api/v1/library/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("testName"))
                .andExpect(jsonPath("$.volume").value(1))
                .andExpect(jsonPath("$.price").value(BigDecimal.valueOf(320)));
    }

    @Test
    void testGetBookByIdNotFound() throws Exception {
        when(bookService.findById(1L))
                .thenThrow(new BookNotFoundException("Такой книги нет в системе"));
        mockMvc.perform(get("/api/v1/library/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    void testAddBook() throws Exception {
        BookDTO bookDTO = new BookDTO(1L, "testName", 1, BigDecimal.valueOf(320));
        when(bookService.saveBook(any(BookDTO.class))).thenReturn(bookDTO);

        mockMvc.perform(post("/api/v1/library")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bookDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("testName"))
                .andExpect(jsonPath("$.volume").value(1))
                .andExpect(jsonPath("$.price").value(BigDecimal.valueOf(320)));
    }

    @Test
    void testUpdateBook() throws Exception {
        Long bookId = 1L;
        UpdateBookPriceRequest request = new UpdateBookPriceRequest(BigDecimal.valueOf(420));
        BookDTO updatedBook = new BookDTO(bookId, "testName", 1, BigDecimal.valueOf(420));

        when(bookService.updatePrice(eq(bookId), any(UpdateBookPriceRequest.class)))
                .thenReturn(updatedBook);

        mockMvc.perform(patch("/api/v1/library/{id}", bookId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.price").value(420));
    }

    @Test
    void testGetBooksPage() throws Exception {
        BookDTO book = new BookDTO(1L, "testName", 1, BigDecimal.valueOf(320));
        Page<BookDTO> bookPage = new PageImpl<>(List.of(book));

        when(bookService.getAll(any(Pageable.class))).thenReturn(bookPage);

        mockMvc.perform(get("/api/v1/library")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sort", "name,asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name").value("testName"))
                .andExpect(jsonPath("$.totalPages").exists());
    }

    @Test
    void testAddBookValidationError() throws Exception {
        BookDTO invalidBook = new BookDTO(null, null, -1, BigDecimal.valueOf(-100));

        mockMvc.perform(post("/api/v1/library")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidBook)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testDeleteBook() throws Exception {
        Long bookId = 1L;

        mockMvc.perform(delete("/api/v1/library/{id}", bookId))
                .andExpect(status().isNoContent());
        verify(bookService, times(1)).deleteBookById(bookId);
    }
}