package com.polarbookshop.catalogservice.web

import com.polarbookshop.catalogservice.domain.BookService
import com.polarbookshop.catalogservice.domain.exception.BookNotFoundException
import com.polarbookshop.catalogservice.security.SecurityConfiguration
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.context.annotation.Import
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(controllers = [BookController::class])
@Import(SecurityConfiguration::class)
internal class BookControllerMvcTests @Autowired constructor(
	private val mockMvc: MockMvc,
) {
	@MockitoBean
	private lateinit var bookService: BookService

	@MockitoBean
	private lateinit var jwtDecoder: JwtDecoder

	@Test
	fun contextLoads() {
	}

	@Test
	fun whenGetBookNotExistingThenShouldReturn404() {
		// GIVEN
		val isbn = "73737313940"
		given(bookService.viewBookDetails(isbn)).willThrow(BookNotFoundException::class.java)

		// WHEN
		mockMvc
			.perform(get("/books/$isbn"))
			// THEN
			.andExpect(status().isNotFound)
	}

	@Test
	fun whenDeleteBookWithEmployeeRoleThenShouldReturn204() {
		// GIVEN
		val isbn = "73737313940"

		// WHEN
		mockMvc
			.perform(
				delete("/books/$isbn")
					.with(
						SecurityMockMvcRequestPostProcessors
							.jwt()
							.authorities(SimpleGrantedAuthority("ROLE_employee"))
					)
			)
			// THEN
			.andExpect(status().isNoContent)
	}

	@Test
	fun whenDeleteBookWithCustomerRoleThenShouldReturn403() {
		// GIVEN
		val isbn = "73737313940"

		// WHEN
		mockMvc
			.perform(
				delete("/books/$isbn")
					.with(
						SecurityMockMvcRequestPostProcessors
							.jwt()
							.authorities(SimpleGrantedAuthority("ROLE_customer"))
					)
			)
			// THEN
			.andExpect(status().isForbidden)
	}

	@Test
	fun whenDeleteBookNotAuthenticatedThenShouldReturn401() {
		// GIVEN
		val isbn = "73737313940"

		// WHEN
		mockMvc
			.perform(delete("/books/$isbn"))
			// THEN
			.andExpect(status().isUnauthorized)
	}
}
