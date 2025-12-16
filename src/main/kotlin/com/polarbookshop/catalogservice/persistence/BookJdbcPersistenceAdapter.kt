package com.polarbookshop.catalogservice.persistence

import com.polarbookshop.catalogservice.domain.BookCrudApi
import com.polarbookshop.catalogservice.domain.model.Book
import com.polarbookshop.catalogservice.persistence.model.toDomain
import com.polarbookshop.catalogservice.persistence.model.toEntity
import com.polarbookshop.catalogservice.persistence.repository.BookRepository

class BookJdbcPersistenceAdapter(
	private val bookRepository: BookRepository,
) : BookCrudApi {

	override fun findAll(): Iterable<Book> =
		bookRepository.findAll().map { it.toDomain() }

	override fun findByIsbn(isbn: String): Book? =
		bookRepository.findByIsbn(isbn)?.toDomain()

	override fun existsByIsbn(isbn: String): Boolean =
		bookRepository.existsByIsbn(isbn)

	override fun save(book: Book): Book =
		bookRepository.save(book.toEntity()).toDomain()

	override fun deleteByIsbn(isbn: String) {
		bookRepository.deleteByIsbn(isbn)
	}

	override fun deleteAll() {
		bookRepository.deleteAll()
	}
}