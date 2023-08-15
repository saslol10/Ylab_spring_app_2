package com.edu.ulab.app.entity;


import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "book")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "userId")
    @NotNull
    @NotEmpty
    private Long userId;

    @Column(name = "title")
    @NotNull
    @NotEmpty
    private String title;

    @Column(name = "author")
    @NotNull
    @NotEmpty
    private String author;

    @Column(name = "pageCount")
    @NotNull
    @NotEmpty
    private long pageCount;

}

