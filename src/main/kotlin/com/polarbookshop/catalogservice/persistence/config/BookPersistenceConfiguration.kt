package com.polarbookshop.catalogservice.persistence.config

import com.polarbookshop.catalogservice.persistence.InMemoryBookRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration(proxyBeanMethods = false)
class BookPersistenceConfiguration {

	@Bean
	fun inMemoryBookRepository() = InMemoryBookRepository()
}
