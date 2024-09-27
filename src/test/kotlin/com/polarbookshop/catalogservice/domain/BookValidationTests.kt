package com.polarbookshop.catalogservice.domain

import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import jakarta.validation.Validation
import org.junit.jupiter.api.Test

internal class BookValidationTests {
    private val validator = Validation.buildDefaultValidatorFactory().validator

    @Test
    fun whenAllFieldsCorrectThenValidationSucceeds() {
        // GIVEN
        val book = Book.of("1234567890", "Title", "Author", 9.90)

        // WHEN
        val result = validator.validate(book)

        // THEN
        result.isEmpty() shouldBe true
    }

    @Test
    fun whenIsbnDefinedButIncorrectThenValidationFails() {
        // GIVEN
        val book = Book.of("a234567890", "Title", "Author", 9.90)

        // WHEN
        val result = validator.validate(book)

        // THEN
        result.size shouldBe 1
        result.first().apply {
            message shouldBe "The ISBN format must be valid."
        }
    }
}
