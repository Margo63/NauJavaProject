package ru.margarita.NauJava.entities;

import jakarta.persistence.*;
import java.util.Date;

/**
 * описание задачи
 *
 * @author Margarita
 * @version 2.0
 * @since 2025-11-11
 */

@Entity
@Table(name = "tbl_task")
public class Task
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String title;

    @Column(nullable = false)
    private int timerValue;
    @Column
    private String description;
    @Column
    private Date dueDate;

    @ManyToOne
    private User user;

    @ManyToOne
    private Category category;

    @ManyToOne
    private Status status;

    public Task() {
    }

    public Task(String title, User user) {
        this.title = title;
        this.user = user;
    }

    public Task(String description, String title, User user) {
        this.description = description;
        this.title = title;
        this.user = user;
    }

    public Long getId()
    {
        return id;
    }
    public void setId(Long id)
    {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getTimerValue() {
        return timerValue;
    }

    public void setTimerValue(int timerValue) {
        this.timerValue = timerValue;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}


