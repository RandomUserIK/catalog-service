package com.polarbookshop.catalogservice.domain.exception

class BookNotFoundException(
	isbn: String
) : RuntimeException(
	"The book with ISBN $isbn was not found."
)