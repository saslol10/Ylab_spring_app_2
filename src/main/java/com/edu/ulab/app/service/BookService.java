package com.edu.ulab.app.service;


import com.edu.ulab.app.dto.BookDto;

import java.util.List;

public interface BookService {
    BookDto createBook(BookDto bookDto);

    BookDto updateBook(BookDto bookDto);

    BookDto getBookById(Long id);

    List<Long> getBooksByUserId(Long id);
    void deleteBookById(Long id);

    void deleteBookByUserId(Long id);
}
