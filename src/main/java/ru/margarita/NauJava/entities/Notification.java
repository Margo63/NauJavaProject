package ru.margarita.NauJava.entities;

import jakarta.persistence.*;
import java.util.Date;

/**
 * описание уведомлений
 *
 * @author Margarita
 * @version 1.0
 * @since 2025-10-21
 */
@Entity
@Table(name = "tbl_notification")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String text;
    @Column(nullable = false)
    private Date sendTime;
    @ManyToOne
    private Task task;

    public Notification(String text, Date sendTime, Task task) {
        this.text = text;
        this.sendTime = sendTime;
        this.task = task;
    }

    public Notification() {
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public Long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }
}
