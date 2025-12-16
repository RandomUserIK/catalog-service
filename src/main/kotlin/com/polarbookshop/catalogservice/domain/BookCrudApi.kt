package com.polarbookshop.catalogservice.domain

import com.polarbookshop.catalogservice.domain.model.Book

interface BookCrudApi {
	fun findAll(): Iterable<Book>

	fun findByIsbn(isbn: String): Book?

	fun existsByIsbn(isbn: String): Boolean

	fun save(book: Book): Book

	fun deleteByIsbn(isbn: String)

	fun deleteAll()
}
