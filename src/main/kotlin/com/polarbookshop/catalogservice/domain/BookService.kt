package com.polarbookshop.catalogservice.domain

class BookService(
	private val bookRepository: BookRepository,
) {

	fun viewBookList() = bookRepository.findAll()

	fun viewBookDetails(isbn: String) =
		bookRepository.findByIsbn(isbn) ?: throw BookNotFoundException(isbn)

	fun addBookToCatalog(book: Book): Book {
		if (bookRepository.existsByIsbn(book.isbn)) {
			throw BookAlreadyExistsException(book.isbn)
		}
		return bookRepository.save(book)
	}

	fun removeBookFromCatalog(isbn: String) {
		bookRepository.deleteByIsbn(isbn)
	}

	fun editBookDetails(isbn: String, book: Book) =
		bookRepository.findByIsbn(isbn)?.let { existingBook ->
			bookRepository.save(
				existingBook.copy(
					title = book.title,
					author = book.author,
					price = book.price,
				),
			)
		} ?: bookRepository.save(book)
}
