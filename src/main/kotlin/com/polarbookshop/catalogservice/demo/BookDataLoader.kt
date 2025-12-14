package com.polarbookshop.catalogservice.demo

import com.polarbookshop.catalogservice.domain.Book
import com.polarbookshop.catalogservice.domain.BookRepository
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.annotation.Profile
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
@Profile("testdata")
class BookDataLoader(
	private val bookRepository: BookRepository,
) {

	@EventListener(ApplicationReadyEvent::class)
	fun loadBookTestData() {
		bookRepository.apply {
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
