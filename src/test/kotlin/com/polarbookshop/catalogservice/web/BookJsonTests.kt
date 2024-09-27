package com.polarbookshop.catalogservice.web

import com.polarbookshop.catalogservice.domain.Book
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.json.JsonTest
import org.springframework.boot.test.json.JacksonTester

@JsonTest
internal class BookJsonTests(
    @Autowired private val json: JacksonTester<Book>,
) {
    @Test
    fun testSerialize() {
        // GIVEN
        val book = Book.of("1234567890", "Title", "Author", 9.90)

        // WHEN
        val result = json.write(book)

        // THEn
        result.apply {
            this.json shouldBe "{\"isbn\":\"1234567890\",\"title\":\"Title\",\"author\":\"Author\",\"price\":9.9}"
        }
    }

    @Test
    fun testDeserialize() {
        // GIVEN
        val content = "{\"isbn\":\"1234567890\",\"title\":\"Title\",\"author\":\"Author\",\"price\":9.9}"
        val expected = Book.of("1234567890", "Title", "Author", 9.90)

        // WHEN
        val result = json.parse(content)

        // THEN
        result.apply {
            `object` shouldBe expected
        }
    }
}
