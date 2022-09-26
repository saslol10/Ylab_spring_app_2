package com.edu.ulab.app.service.impl;

import com.edu.ulab.app.dto.BookDto;
import com.edu.ulab.app.entity.Book;
import com.edu.ulab.app.exception.NotFoundException;
import com.edu.ulab.app.mapper.BookMapper;
import com.edu.ulab.app.repository.BookRepository;
import com.edu.ulab.app.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    private final BookMapper bookMapper;

    public BookServiceImpl(BookRepository bookRepository,
                           BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }

    @Override
    public BookDto createBook(BookDto bookDto) {
        Book book = bookMapper.bookDtoToBook(bookDto);
        log.info("Mapped book: {}", book);
        Book savedBook = bookRepository.save(book);
        log.info("Saved book: {}", savedBook);
        return bookMapper.bookToBookDto(savedBook);
    }

    @Override
    public BookDto updateBook(BookDto bookDto) {
        if (bookRepository.findById(bookDto.getId()).orElse(null) != null) {
            bookRepository.save(bookMapper.bookDtoToBook(bookDto));
            log.info("Updated book: {}", bookDto);
            return bookDto;
        }
        else{
            throw new NotFoundException("Book with that id not found");
        }

    }

    @Override
    public BookDto getBookById(Long id) {
        if (bookRepository.findById(id).orElse(null) != null) {
            log.info("Got book by id: {}", id);
            return bookMapper.bookToBookDto(bookRepository.findById(id).get());
        }else throw new NotFoundException("Book with that id not found");
    }

    public List<Long> getBooksByUserId(Long id)
    {
        if (bookRepository.findBooksByUserId(id).stream().filter(Objects::nonNull).anyMatch(x->x.getUserId().equals(id))) {
            List<Long> bookList = bookRepository.findBooksByUserId(id).stream().map(Book::getId).collect(Collectors.toList());
            log.info("Got books by userId: {}", id);
            return bookList;
        }else throw new NotFoundException("Books with that userId not found");
    }
    @Override
    public void deleteBookById(Long id) {
        if (bookRepository.findById(id).orElse(null) != null) {
            log.info("Delete book by id: {}", id);
            bookRepository.deleteById(id);
        }else throw new NotFoundException("Book with that id not found");
    }


    @Override
    public void deleteBookByUserId(Long id) {
        if (bookRepository.findBooksByUserId(id)
                .stream()
                .filter(Objects::nonNull)
                .anyMatch(x->x.getUserId().equals(id))){
            bookRepository.deleteBooksByUserId(id);
            log.info("Delete books by userId: {}", id);
        }else throw new NotFoundException("Books with that userId not found");
    }
}
