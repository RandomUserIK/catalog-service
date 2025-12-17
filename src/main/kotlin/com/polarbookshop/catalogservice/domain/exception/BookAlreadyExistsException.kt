package com.polarbookshop.catalogservice.domain.exception

class BookAlreadyExistsException(
	isbn: String,
) : RuntimeException(
	"A book with ISBN $isbn already exists.",
)