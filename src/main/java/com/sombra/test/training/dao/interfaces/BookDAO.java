package com.sombra.test.training.dao.interfaces;

import com.sombra.test.training.entities.Author;
import com.sombra.test.training.entities.Book;
import com.sombra.test.training.entities.Genre;

import java.util.List;


public interface BookDAO {

    List<Book> getBooks();
    List<Book> getBooks(Author author);
    List<Book> getBooks(String bookName);
    List<Book> getBooks(Genre genre);
    List<Book> getBooks(Character letter);
    Object getFieldValue(Long id, String fieldName);
}
