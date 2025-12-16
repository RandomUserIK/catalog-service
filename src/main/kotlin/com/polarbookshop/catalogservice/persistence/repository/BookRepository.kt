package com.polarbookshop.catalogservice.persistence.repository

import com.polarbookshop.catalogservice.persistence.model.BookEntity
import org.springframework.data.jdbc.repository.query.Modifying
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.transaction.annotation.Transactional

interface BookRepository : CrudRepository<BookEntity, Long> {
	fun findByIsbn(isbn: String): BookEntity?

	fun existsByIsbn(isbn: String): Boolean

	fun save(book: BookEntity): BookEntity

	@Modifying
	@Transactional
	@Query("delete from BookEntity where isbn = :isbn")
	fun deleteByIsbn(isbn: String)
}