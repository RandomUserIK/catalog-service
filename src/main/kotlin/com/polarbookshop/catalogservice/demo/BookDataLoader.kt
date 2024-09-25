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
        bookRepository.save(
            Book(
                "1234567891",
                "Northern Lights",
                "Lyra Silverstar",
                9.90
            )
        )
        bookRepository.save(
            Book(
                "1234567892",
                "Polar Journey",
                "Iorek Polarson",
                12.90
            )
        )
    }
}
