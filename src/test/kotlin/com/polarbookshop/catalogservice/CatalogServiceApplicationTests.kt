package com.polarbookshop.catalogservice

import com.polarbookshop.catalogservice.domain.Book
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
)
@ActiveProfiles("integration")
class CatalogServiceApplicationTests(
    @Autowired private val webTestClient: WebTestClient,
) {

    @Test
    fun contextLoads() {
    }

    @Test
    fun whenPostRequestThenBookCreated() {
        // GIVEN
        val expected = Book.of("1231231231", "Title", "Author", 9.90)

        // WHEN
        webTestClient
            .post()
            .uri("/books")
            .bodyValue(expected)
            .exchange()
            .expectStatus().isCreated
            .expectBody(Book::class.java).value {
                it.apply {
                    id shouldNotBe null
                    createdDate shouldNotBe null
                    lastModifiedDate shouldNotBe null
                    version shouldBe expected.version.inc()
                    isbn shouldBe expected.isbn
                    title shouldBe expected.title
                    author shouldBe expected.author
                    price shouldBe expected.price
                }
            }
    }
}
