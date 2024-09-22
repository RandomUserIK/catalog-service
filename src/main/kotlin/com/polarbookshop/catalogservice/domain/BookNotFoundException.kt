package com.polarbookshop.catalogservice.domain

class BookNotFoundException(
    isbn: String,
    override val message: String? = "The book with ISBN $isbn was not found.",
) : RuntimeException()
