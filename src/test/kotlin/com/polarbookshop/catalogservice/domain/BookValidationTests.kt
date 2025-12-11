package com.polarbookshop.catalogservice.domain

import io.kotest.matchers.shouldBe
import jakarta.validation.Validation
import org.junit.jupiter.api.Test

internal class BookValidationTests {
	private val validator = Validation.buildDefaultValidatorFactory().validator

	@Test
	fun whenAllFieldsCorrectThenValidationSucceeds() {
		// GIVEN
		val book = Book(
			isbn = "1234567890",
			title = "Title",
			author = "Author",
			price = 9.90,
		)

		// WHEN
		val violations = validator.validate(book)

		// THEN
		violations.isEmpty() shouldBe true
	}

	@Test
	fun whenIsbnDefinedButIncorrectThenValidationFails() {
		// GIVEN
		val book = Book(
			isbn = "a1234567890",
			title = "Title",
			author = "Author",
			price = 9.90,
		)

		// WHEN
		val violations = validator.validate(book)

		// THEN
		violations.apply {
			size shouldBe 1
			first().message shouldBe "The ISBN format must be valid."
		}
	}
}
