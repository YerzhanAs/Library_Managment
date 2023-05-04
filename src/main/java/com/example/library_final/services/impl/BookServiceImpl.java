package com.example.library_final.services.impl;

import com.example.library_final.entities.Books;
import com.example.library_final.repositories.BookRepository;
import com.example.library_final.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private BookRepository bookRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Books> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Books getBook(Long id) {
        return bookRepository.getOne(id);
    }

    @Override
    public Books saveBook(Books books) {
        return bookRepository.save(books);
    }

    @Override
    public void deleteBook(Books books) {
        bookRepository.delete(books);
    }
}
