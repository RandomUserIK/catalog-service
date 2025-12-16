package com.polarbookshop.catalogservice.demo

import com.polarbookshop.catalogservice.domain.BookCrudApi
import com.polarbookshop.catalogservice.domain.model.Book
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.annotation.Profile
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
@Profile("testdata")
class BookDataLoader(
	private val bookCrudApi: BookCrudApi,
) {

	@EventListener(ApplicationReadyEvent::class)
	fun loadBookTestData() {
		bookCrudApi.apply {
			deleteAll()
			save(
				Book(
					isbn = "1234567890",
					title = "Blood Meridian",
					author = "Cormac McCarthy",
					price = 9.90,
				),
			)

			save(
				Book(
					isbn = "1234567891",
					title = "Sapiens",
					author = "Yuval Noah Harari",
					price = 10.90,
				),
			)
		}
	}
}
