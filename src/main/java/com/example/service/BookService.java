package com.example.service;

import com.example.dto.BookDTO;
import com.example.dto.UpdateBookPriceRequest;
import com.example.exception.BookNotFoundException;
import com.example.model.Book;
import com.example.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;

    @Transactional(readOnly = true)
    public BookDTO findById(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(()->
                new BookNotFoundException("Такой книги нет в системе")
        );
        return createBookDTO(book);
    }

    @Transactional(readOnly = true)
    public Page<BookDTO> getAll(Pageable pageable) {
        Page<Book> books = bookRepository.findAll(pageable);
        return books.map(this::createBookDTO);
    }

    @Transactional
    public void deleteBookById(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(()->
                new BookNotFoundException("Такой книги нет в системе")
        );
        bookRepository.delete(book);
    }

    @Transactional
    public BookDTO saveBook(BookDTO dto) {
        Book book = new Book();
        book.setPrice(dto.price());
        book.setVolume(dto.volume());
        book.setName(dto.name());

        Book savedBook = bookRepository.save(book);
        return createBookDTO(savedBook);
    }

    @Transactional
    public BookDTO updatePrice(Long id,UpdateBookPriceRequest newPrice) {
        Book book = bookRepository.findById(id).orElseThrow(()->
                        new BookNotFoundException("Такой книги нет в системе")
                );
        book.setPrice(newPrice.price());
        return createBookDTO(book);
    }

    private BookDTO createBookDTO(Book book) {
        return new BookDTO(book.getId(),book.getName(), book.getVolume(), book.getPrice());
    }
}
