package com.example.service;

import com.example.exception.BookNotFoundException;
import com.example.model.Book;
import com.example.model.dto.BookRequestDTO;
import com.example.model.dto.BookResponseDTO;
import com.example.repository.JdbcBookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private JdbcBookRepository jdbcBookRepository;

    public BookService(JdbcBookRepository bookRepository) {
        this.jdbcBookRepository = bookRepository;
    }

    public void createBook(BookRequestDTO bookDTO) {
        Book book = new Book();
        book.setTitle(bookDTO.getTitle());
        book.setAuthor(bookDTO.getAuthor());
        book.setPublicationYear(bookDTO.getPublicationYear());
        jdbcBookRepository.saveBook(book);
    }

    public void updateBook(Long id, BookRequestDTO bookDTO) {
        Book existBook = jdbcBookRepository.getBookById(id).orElseThrow(() ->
                new BookNotFoundException("Book Not Found")
        );
        if (bookDTO.getTitle() != null)
            existBook.setTitle(bookDTO.getTitle());
        if (bookDTO.getAuthor() != null)
            existBook.setAuthor(bookDTO.getAuthor());
        if (bookDTO.getPublicationYear() != null)
            existBook.setPublicationYear(bookDTO.getPublicationYear());

        jdbcBookRepository.updateBook(existBook);
    }

    public void deleteBook(Long id) {
        jdbcBookRepository.deleteBookById(id);
    }

    public BookResponseDTO getBookById(Long id) {
        return jdbcBookRepository.getBookById(id)
                .map(this::dtoMapper)
                .orElseThrow(() ->
                        new BookNotFoundException("Book Not Found"));
    }

    public List<BookResponseDTO> getAllBooks() {
        List<Book> books = jdbcBookRepository.getAllBooks();
        return books.stream().map(this::dtoMapper).toList();
    }

    private BookResponseDTO dtoMapper(Book book) {
        BookResponseDTO dto = new BookResponseDTO();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setAuthor(book.getAuthor());
        dto.setPublicationYear(book.getPublicationYear());
        return dto;
    }
}
