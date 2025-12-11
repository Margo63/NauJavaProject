package ru.margarita.NauJava.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "tbl_friends")
public class Friend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long userId;

    @Column
    private Long friendUserId;

    @Column
    private String status;

}
