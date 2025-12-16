package com.polarbookshop.catalogservice.domain

import com.polarbookshop.catalogservice.domain.model.Book
import io.kotest.assertions.json.shouldContainJsonKeyValue
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.json.JsonTest
import org.springframework.boot.test.json.JacksonTester

@JsonTest
internal class BookJsonTests @Autowired constructor(
	private val json: JacksonTester<Book>,
) {
	@Test
	fun contextLoads() {
	}

	@Test
	fun testSerialize() {
		// GIVEN
		val book = Book(
			isbn = "1234567890",
			title = "Title",
			author = "Author",
			price = 9.90,
		)

		// WHEN
		val result = json.write(book)

		// THEN
		result.json.apply {
			shouldContainJsonKeyValue("isbn", "1234567890")
			shouldContainJsonKeyValue("title", "Title")
			shouldContainJsonKeyValue("author", "Author")
			shouldContainJsonKeyValue("price", 9.90)
		}
	}

	@Test
	fun testDeserialize() {
		// GIVEN
		val content = """
			{
			"isbn": "1234567890",
			"title": "Title",
			"author": "Author",
			"price": 9.90
			}
		""".trimIndent()

		// WHEN
		val result = json.parse(content)

		// THEN
		result.`object`.apply {
			isbn shouldBe "1234567890"
			title shouldBe "Title"
			author shouldBe "Author"
			price shouldBe 9.90
		}
	}
}