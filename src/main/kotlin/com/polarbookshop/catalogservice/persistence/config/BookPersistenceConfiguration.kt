package com.polarbookshop.catalogservice.persistence.config

import com.polarbookshop.catalogservice.persistence.BookJdbcPersistenceAdapter
import com.polarbookshop.catalogservice.persistence.repository.BookRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.domain.AuditorAware
import org.springframework.data.jdbc.repository.config.EnableJdbcAuditing
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import java.util.Optional

@EnableJdbcAuditing
@Configuration(proxyBeanMethods = false)
class BookPersistenceConfiguration {

	@Bean
	fun bookJdbcPersistenceAdapter(
		bookRepository: BookRepository,
	) = BookJdbcPersistenceAdapter(
		bookRepository,
	)

	@Bean
	fun auditorAware(): AuditorAware<String> =
		AuditorAware<String> {
			Optional
				.ofNullable(SecurityContextHolder.getContext()?.authentication)
				.filter(Authentication::isAuthenticated)
				.map(Authentication::getName)
		}
}
