package com.polarbookshop.catalogservice.web

import com.polarbookshop.catalogservice.domain.BookNotFoundException
import com.polarbookshop.catalogservice.domain.BookService
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(controllers = [BookController::class])
internal class BookControllerMvcTests @Autowired constructor(
	private val mockMvc: MockMvc,
) {
	@MockitoBean
	private lateinit var bookService: BookService

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
}
