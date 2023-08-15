package com.edu.ulab.app.service.impl;

import com.edu.ulab.app.dto.BookDto;
import com.edu.ulab.app.entity.Book;
import com.edu.ulab.app.entity.Person;
import com.edu.ulab.app.mapper.BookMapperImpl;
import com.edu.ulab.app.mapper.UserMapperImpl;
import com.edu.ulab.app.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class BookServiceImplTemplate implements BookService {

    private final JdbcTemplate jdbcTemplate;

    public BookServiceImplTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public BookDto createBook(BookDto bookDto) {
        final String INSERT_SQL = "INSERT INTO BOOK(TITLE, AUTHOR, PAGE_COUNT, USER_ID) VALUES (?,?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                new PreparedStatementCreator() {
                    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                        PreparedStatement ps =
                                connection.prepareStatement(INSERT_SQL, new String[]{"id"});
                        ps.setString(1, bookDto.getTitle());
                        ps.setString(2, bookDto.getAuthor());
                        ps.setLong(3, bookDto.getPageCount());
                        ps.setLong(4, bookDto.getUserId());
                        return ps;
                    }
                },
                keyHolder);

        bookDto.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
        return bookDto;
    }

    @Override
    public BookDto updateBook(BookDto bookDto) {
        final String UPDATE_SQL = "UPDATE BOOK SET TITLE=?, AUTHOR=?, PAGE_COUNT=?, USER_ID=? WHERE ID=? AND USER_ID=?";
        jdbcTemplate.update(UPDATE_SQL,
                bookDto.getTitle(),
                bookDto.getAuthor(),
                bookDto.getPageCount(),
                bookDto.getUserId(),
                bookDto.getId(),
                bookDto.getUserId());
        return bookDto;
    }

    @Override
    public BookDto getBookById(Long id) {
        final String SELECT_SQL = "SELECT * FROM PERSON WHERE ID=?";
        Book book = jdbcTemplate.queryForObject(
                SELECT_SQL,
                (resultSet, rowNum) -> new Book(
                        resultSet.getLong("ID"),
                        resultSet.getLong("USER_ID"),
                        resultSet.getString("TITLE"),
                        resultSet.getString("AUTHOR"),
                        resultSet.getLong("PAGE_COUNT")),
                id
        );

        return new BookMapperImpl().bookToBookDto(book);
    }

    @Override
    public List<Long> getBooksByUserId(Long id) {
        final String GET_SQL = "SELECT * FROM BOOK WHERE USER_ID=?";
        List<Book> bookList = jdbcTemplate.query(
                GET_SQL,
                (resultSet, rowNum) -> new Book(
                        resultSet.getLong("ID"),
                        resultSet.getLong("USER_ID"),
                        resultSet.getString("TITLE"),
                        resultSet.getString("AUTHOR"),
                        resultSet.getLong("PAGE_COUNT")),
                id
        );
        return bookList.stream().map(Book::getId).toList();
    }

    @Override
    public void deleteBookById(Long id) {
        final String DELETE_SQL = "DELETE FROM BOOK WHERE ID=?";
        jdbcTemplate.update(DELETE_SQL,id);
    }

    @Override
    public void deleteBookByUserId(Long id) {
        final String DELETE_SQL = "DELETE FROM BOOK WHERE USER_ID=?";
        jdbcTemplate.update(DELETE_SQL,id);
    }
}
