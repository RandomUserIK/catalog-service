package com.polarbookshop.catalogservice.domain

import org.springframework.stereotype.Repository
import java.util.concurrent.ConcurrentHashMap

@Repository
class InMemoryBookRepository : BookRepository {
    companion object {
        private val books = ConcurrentHashMap<String, Book>()
    }

    override fun findAll(): Iterable<Book> =
        books.values

    override fun findByIsbn(isbn: String): Book? =
        books[isbn]

    override fun existsByIsbn(isbn: String): Boolean =
        books[isbn] != null

    override fun save(book: Book): Book =
        book.let {
            books.put(it.isbn, it)
            it
        }

    override fun deleteByIsbn(isbn: String) {
        books.remove(isbn)
    }

}
