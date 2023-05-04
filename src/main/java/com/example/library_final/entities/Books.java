package com.example.library_final.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="t_books")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Books {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="author_book")
    private String author;

    @Column(name="name_book")
    private String name;

    @Column(name="amount")
    private int amount;

    @Column(name="url")
    private String url;

    @Column(name="year")
    private int year;

//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private Users user;

    @ManyToMany(mappedBy = "books")
    private List<Users> users;

}
