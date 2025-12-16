package com.polarbookshop.catalogservice

import com.polarbookshop.catalogservice.domain.model.Book
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBody

@SpringBootTest(
	webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
)
@ActiveProfiles("integration")
class CatalogServiceApplicationTests @Autowired constructor(
	private val webTestClient: WebTestClient,
) {

	@Test
	fun contextLoads() {
	}

	@Test
	fun whenPostRequestThenBookCreated() {
		// GIVEN
		val expectedBook = Book(
			isbn = "1234567890",
			title = "Title",
			author = "Author",
			price = 9.90,
		)

		// WHEN
		webTestClient
			.post()
			.uri("/books")
			.bodyValue(expectedBook)
			.exchange()
			// THEN
			.expectStatus()
			.isCreated
			.expectBody<Book>()
			.value { actualBook ->
				actualBook.isbn shouldBe expectedBook.isbn
			}
	}

}
