package com.edu.ulab.app.entity;



import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "person")
@Data
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "fullName")
    @NotNull
    @NotEmpty
    private String fullName;

    @Column(name = "title")
    @NotNull
    @NotEmpty
    private String title;

    @Column(name = "age")
    @NotNull
    @NotEmpty
    private int age;
}
