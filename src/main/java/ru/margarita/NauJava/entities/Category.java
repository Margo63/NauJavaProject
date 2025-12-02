package ru.margarita.NauJava.entities;

import jakarta.persistence.*;
import java.awt.*;

/**
 * описание категории
 *
 * @author Margarita
 * @version 1.0
 * @since 2025-10-21
 */
@Entity
@Table(name = "tbl_category")
public class Category
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String title;
    @Column
    private String description;
    @Column
    private Color color;

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

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
