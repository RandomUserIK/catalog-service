package com.polarbookshop.catalogservice.web

import com.polarbookshop.catalogservice.domain.BookAlreadyExistsException
import com.polarbookshop.catalogservice.domain.BookNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class BookControllerAdvice {
    @ExceptionHandler(BookNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun bookNotFoundHandler(ex: BookNotFoundException) =
        ex.message

    @ExceptionHandler(BookAlreadyExistsException::class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    fun bookAlreadyExistsHandler(ex: BookNotFoundException) =
        ex.message

    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun validationHandler(ex: MethodArgumentNotValidException) =
        buildMap {
            ex.bindingResult.allErrors.forEach { error ->
                put(
                    (error as FieldError).field,
                    error.defaultMessage,
                )
            }
        }

}
