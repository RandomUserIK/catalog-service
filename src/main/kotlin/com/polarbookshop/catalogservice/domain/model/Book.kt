package com.polarbookshop.catalogservice.domain.model

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Positive
import org.springframework.data.annotation.LastModifiedBy
import java.time.Instant

data class Book(
	val id: Long? = null,

	@field:NotBlank(message = "The book ISBN must be defined")
	@field:Pattern(
		regexp = "^([0-9]{10}|[0-9]{13})$",
		message = "The ISBN format must be valid."
	)
	val isbn: String,

	@field:NotBlank(message = "The book title must be defined.")
	val title: String,

	@field:NotBlank(message = "The book author must be defined.")
	val author: String,

	@field:Positive(
		message = "The book price must be greater than zero."
	)
	val price: Double,

	val version: Int = 0,

	val createdDate: Instant? = null,

	val lastModifiedDate: Instant? = null,

	val publisher: String? = null,

	val createdBy: String? = null,

	val lastModifiedBy: String? = null,
)