package com.polarbookshop.catalogservice.persistence

import com.polarbookshop.catalogservice.domain.Book
import com.polarbookshop.catalogservice.domain.BookRepository
import java.util.concurrent.ConcurrentHashMap

class InMemoryBookRepository: BookRepository {

	companion object {
		private val books: MutableMap<String, Book> = ConcurrentHashMap()
	}

	override fun findAll(): Iterable<Book> = books.values

	override fun findByIsbn(isbn: String): Book? = books[isbn]

	override fun existsByIsbn(isbn: String): Boolean = books.containsKey(isbn)

	override fun save(book: Book): Book =
		book.apply {
			books[isbn] = this
		}

	override fun deleteByIsbn(isbn: String) {
		books.remove(isbn)
	}
}