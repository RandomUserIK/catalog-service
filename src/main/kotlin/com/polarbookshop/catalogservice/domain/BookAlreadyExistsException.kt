package com.polarbookshop.catalogservice.domain

class BookAlreadyExistsException(
    isbn: String,
    override val message: String? = "The book with ISBN $isbn already exists.",
) : RuntimeException()
