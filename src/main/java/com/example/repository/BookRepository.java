package com.example.repository;


import com.example.model.Book;
import com.example.model.dto.BookResponseDTO;

import java.util.List;
import java.util.Optional;

public interface BookRepository {
    Optional<Book> getBookById(Long id);
    void saveBook(Book book);
    void deleteBookById(Long id);
    List<Book> getAllBooks();
    void updateBook(Book book);
}
