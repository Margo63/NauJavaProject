package ru.margarita.NauJava.controllers;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Класс для обработки ошибок (не найден ресурс и внутренней ошибки сервера)
 *
 * @author Margarita
 * @version 1.0
 * @since 2025-10-27
 */
@ControllerAdvice
public class ExceptionControllerAdvice
{
    @Hidden
    @ExceptionHandler(java.lang.Exception.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionRest exception(java.lang.Exception e)
    {
        return ExceptionRest.create(e);
    }
    @Hidden
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionRest exception(ResourceNotFoundException e)
    {
        return ExceptionRest.create(e);
    }
}