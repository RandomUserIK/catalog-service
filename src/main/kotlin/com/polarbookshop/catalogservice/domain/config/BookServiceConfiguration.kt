package com.polarbookshop.catalogservice.domain.config

import com.polarbookshop.catalogservice.domain.BookRepository
import com.polarbookshop.catalogservice.domain.BookService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration(proxyBeanMethods = false)
class BookServiceConfiguration {

	@Bean
	fun bookService(
		bookRepository: BookRepository,
	) = BookService(
		bookRepository,
	)
}