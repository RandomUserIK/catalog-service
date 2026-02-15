package com.polarbookshop.catalogservice.persistence.model

import com.polarbookshop.catalogservice.domain.model.Book
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.annotation.Version
import org.springframework.data.relational.core.mapping.Table
import java.time.Instant

@Table("book")
data class BookEntity(
	@Id
	val id: Long?,

	@Version
	val version: Int,

	val isbn: String,

	val title: String,

	val author: String,

	val price: Double,

	@CreatedDate
	val createdDate: Instant,

	@LastModifiedDate
	val lastModifiedDate: Instant,

	val publisher: String?,

	@CreatedBy
	val createdBy: String?,

	@LastModifiedBy
	val lastModifiedBy: String?,
)

internal fun BookEntity.toDomain() =
	Book(
		id = id,
		version = version,
		isbn = isbn,
		title = title,
		author = author,
		price = price,
		createdDate = createdDate,
		lastModifiedDate = lastModifiedDate,
		publisher = publisher,
		createdBy = createdBy,
		lastModifiedBy = lastModifiedBy,
	)

internal fun Book.toEntity() =
	BookEntity(
		id = id,
		version = version,
		isbn = isbn,
		title = title,
		author = author,
		price = price,
		createdDate = createdDate ?: Instant.now(),
		lastModifiedDate = lastModifiedDate ?: Instant.now(),
		publisher = publisher,
		createdBy = createdBy,
		lastModifiedBy = lastModifiedBy,
	)
