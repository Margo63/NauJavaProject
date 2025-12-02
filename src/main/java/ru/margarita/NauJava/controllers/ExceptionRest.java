package ru.margarita.NauJava.controllers;

/**
 * Класс обертки ошибок (не найден ресурс и внутренней ошибки сервера)
 *
 * @author Margarita
 * @version 1.0
 * @since 2025-10-27
 */
public class ExceptionRest
{
    private String message;
    private ExceptionRest(String message)
    {
        this.message = message;
    }
    public String getMessage()
    {
        return message;
    }
    public void setMessage(String message)
    {
        this.message = message;
    }
    public static ExceptionRest create(Throwable e)
    {
        return new ExceptionRest(e.getMessage());
    }
    public static ExceptionRest create(String message)
    {
        return new ExceptionRest(message);
    }
}