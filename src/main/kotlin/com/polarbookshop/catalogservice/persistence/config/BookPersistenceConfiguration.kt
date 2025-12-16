package com.polarbookshop.catalogservice.persistence.config

import com.polarbookshop.catalogservice.persistence.BookJdbcPersistenceAdapter
import com.polarbookshop.catalogservice.persistence.repository.BookRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jdbc.repository.config.EnableJdbcAuditing

@EnableJdbcAuditing
@Configuration(proxyBeanMethods = false)
class BookPersistenceConfiguration {

	@Bean
	fun bookJdbcPersistenceAdapter(
		bookRepository: BookRepository,
	) = BookJdbcPersistenceAdapter(
		bookRepository,
	)
}
