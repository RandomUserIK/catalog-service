package com.polarbookshop.catalogservice.domain

import com.polarbookshop.catalogservice.domain.exception.BookAlreadyExistsException
import com.polarbookshop.catalogservice.domain.exception.BookNotFoundException
import com.polarbookshop.catalogservice.domain.model.Book

class BookService(
	private val bookCrudApi: BookCrudApi,
) {

	fun viewBookList() = bookCrudApi.findAll()

	fun viewBookDetails(isbn: String) =
		bookCrudApi.findByIsbn(isbn) ?: throw BookNotFoundException(isbn)

	fun addBookToCatalog(book: Book): Book {
		if (bookCrudApi.existsByIsbn(book.isbn)) {
			throw BookAlreadyExistsException(book.isbn)
		}
		return bookCrudApi.save(book)
	}

	fun removeBookFromCatalog(isbn: String) {
		bookCrudApi.deleteByIsbn(isbn)
	}

	fun editBookDetails(isbn: String, book: Book) =
		bookCrudApi.findByIsbn(isbn)?.let { existingBook ->
			bookCrudApi.save(
				existingBook.copy(
					title = book.title,
					author = book.author,
					price = book.price,
				),
			)
		} ?: bookCrudApi.save(book)
}
