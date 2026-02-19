package com.example.controller;

import com.example.model.Book;
import com.example.model.dto.BookRequestDTO;
import com.example.model.dto.BookResponseDTO;
import com.example.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/book")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @GetMapping
    public List<BookResponseDTO> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/{id}")
    public BookResponseDTO getBookById(@PathVariable Long id) {
        return bookService.getBookById(id);
    }

    @PostMapping
    public void addBook(@Valid @RequestBody BookRequestDTO bookDTO) {
        bookService.createBook(bookDTO);
    }

    @PutMapping("/{id}")
    public void updateBook(@PathVariable Long id,
                           @Valid @RequestBody BookRequestDTO bookDTO) {
        bookService.updateBook(id, bookDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteBookById(@PathVariable Long id) {
        bookService.deleteBook(id);
    }
}
