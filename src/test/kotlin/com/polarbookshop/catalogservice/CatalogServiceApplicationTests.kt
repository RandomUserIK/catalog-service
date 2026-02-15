package com.polarbookshop.catalogservice

import com.fasterxml.jackson.annotation.JsonProperty
import com.polarbookshop.catalogservice.domain.model.Book
import dasniko.testcontainers.keycloak.KeycloakContainer
import io.kotest.matchers.shouldBe
import org.apache.hc.core5.http.HttpHeaders
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBody
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

@SpringBootTest(
	webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
)
@ActiveProfiles("integration")
@Testcontainers
class CatalogServiceApplicationTests @Autowired constructor(
	private val webTestClient: WebTestClient,
) {

	private lateinit var isabelleToken: KeycloakToken
	private lateinit var bjornToken: KeycloakToken

	companion object {
		@Container
		@JvmStatic
		val keycloakContainer = KeycloakContainer(
			"quay.io/keycloak/keycloak:26.4"
		).apply {
			withRealmImportFile("test-realm-config.json")
			start()
		}

		@JvmStatic
		@DynamicPropertySource
		fun dynamicProperties(registry: DynamicPropertyRegistry) {
			registry.apply {
				add("spring.security.oauth2.resourceserver.jwt.issuer-uri") {
					keycloakContainer.authServerUrl + "/realms/PolarBookshop"
				}
			}
		}

		private fun WebClient.authenticateWith(username: String, password: String) =
			post()
				.body(
					BodyInserters.fromFormData("grant_type", "password")
						.with("client_id", "polar-test")
						.with("username", username)
						.with("password", password)
				)
				.retrieve()
				.bodyToMono<KeycloakToken>()
				.block() ?: error("Could not retrieve keycloak token")
	}

	@BeforeAll
	fun setUp() {
		val webClient = WebClient
			.builder()
			.baseUrl(keycloakContainer.authServerUrl + "/realms/PolarBookshop/protocol/openid-connect/token")
			.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
			.build()

		isabelleToken = webClient.authenticateWith("isabelle", "password")
		bjornToken = webClient.authenticateWith("bjorn", "password")
	}

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
			.headers {
				it.setBearerAuth(isabelleToken.accessToken)
			}
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

	@Test
	fun whenPostRequestUnauthorizedThen403() {
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
			.headers {
				it.setBearerAuth(bjornToken.accessToken)
			}
			.bodyValue(expectedBook)
			.exchange()
			// THEN
			.expectStatus()
			.isForbidden
	}

	@Test
	fun wwhenPostRequestUnauthenticatedThen401() {
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
			.isUnauthorized
	}

	private data class KeycloakToken(
		@JsonProperty("access_token")
		val accessToken: String,
	)
}
