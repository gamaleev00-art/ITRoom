package com.example.repository;

import com.example.model.Book;
import com.example.model.dto.BookResponseDTO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcBookRepository implements BookRepository {
    private final JdbcTemplate jdbcTemplate;

    public JdbcBookRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Book> getBookById(Long id) {
        String query = "SELECT * FROM book WHERE id = ?";
        return jdbcTemplate.query(query, new BookRowMapper(), id).stream().findFirst();
    }

    @Override
    public void saveBook(Book book) {
        jdbcTemplate.update("INSERT INTO book (title, author, publication_year) VALUES (?,?,?)",
                book.getTitle(),
                book.getAuthor(),
                book.getPublicationYear());
    }

    @Override
    public void deleteBookById(Long id) {
        jdbcTemplate.update("DELETE FROM book WHERE id = (?)", id);
    }

    @Override
    public List<Book> getAllBooks() {
        String query = "SELECT * FROM book";
        return jdbcTemplate.query(query, new BookRowMapper());
    }

    @Override
    public void updateBook(Book book) {
        jdbcTemplate.update("UPDATE book SET title = (?),author = (?),publication_year = (?) WHERE id = (?)",
                book.getTitle(),
                book.getAuthor(),
                book.getPublicationYear(),
                book.getId());
    }
}
