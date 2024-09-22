package com.polarbookshop.catalogservice.domain

import org.springframework.stereotype.Service

@Service
class BookService(
    private val bookRepository: BookRepository,
) {
    fun viewBookList() =
        bookRepository.findAll()

    fun viewBookDetails(isbn: String) =
        bookRepository.findByIsbn(isbn) ?: throw BookNotFoundException(isbn)

    fun addBookToCatalog(book: Book) =
        when (bookRepository.existsByIsbn(book.isbn)) {
            true -> throw BookAlreadyExistsException(book.isbn)

            else -> bookRepository.save(book)
        }

    fun removeFromCatalog(isbn: String) {
        bookRepository.deleteByIsbn(isbn)
    }

    fun editBookDetails(isbn: String, book: Book) =
        bookRepository.findByIsbn(isbn)?.let { existingBook ->
            bookRepository.save(
                existingBook.copy(
                    title = book.title,
                    author = book.author,
                    price = book.price,
                )
            )
        } ?: addBookToCatalog(book)
}
