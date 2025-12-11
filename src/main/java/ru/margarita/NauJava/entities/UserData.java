package ru.margarita.NauJava.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "tbl_users_data")
public class UserData {
    @Id
    private Long id;

    @Column
    private String surname;

    @Column
    private String patronymic;

    @Column
    private String job;

    public UserData() {
        this.surname = "";
        this.patronymic = "";
        this.job = "";
    }

    public UserData(String surname, String patronymic, String job) {
        this.surname = surname;
        this.patronymic = patronymic;
        this.job = job;
    }

    public UserData(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getSurname() {
        return surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public String getJob() {
        return job;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public void setJob(String job) {
        this.job = job;
    }
}
