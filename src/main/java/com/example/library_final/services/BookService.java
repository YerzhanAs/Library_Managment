package com.example.library_final.services;

import com.example.library_final.entities.Books;

import java.util.List;

public interface BookService {

    List<Books> getAllBooks();

    Books getBook(Long id);

    Books saveBook(Books books);

    void deleteBook(Books books);
}
