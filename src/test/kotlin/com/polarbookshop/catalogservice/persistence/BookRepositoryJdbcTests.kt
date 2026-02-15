package com.polarbookshop.catalogservice.persistence

import com.polarbookshop.catalogservice.domain.model.Book
import com.polarbookshop.catalogservice.persistence.config.BookPersistenceConfiguration
import com.polarbookshop.catalogservice.persistence.model.BookEntity
import com.polarbookshop.catalogservice.persistence.model.toEntity
import com.polarbookshop.catalogservice.persistence.repository.BookRepository
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.context.annotation.Import
import org.springframework.data.jdbc.core.JdbcAggregateTemplate
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.ActiveProfiles

@DataJdbcTest
@Import(BookPersistenceConfiguration::class)
@AutoConfigureTestDatabase(
	replace = AutoConfigureTestDatabase.Replace.NONE,
)
@ActiveProfiles("integration")
internal class BookRepositoryJdbcTests @Autowired constructor(
	private val bookRepository: BookRepository,
	private val jdbcAggregateTemplate: JdbcAggregateTemplate,
) {
	@Test
	fun contextLoads() {
	}

	@Test
	fun findBookByIsbnWhenExisting() {
		// GIVEN
		val bookIsbn = "1234561237"
		val domain = Book(
			isbn = bookIsbn,
			title = "Blood Meridian",
			author = "Cormac McCarthy",
			price = 9.90,
		)
		val entity = domain.toEntity()

		// WHEN
		jdbcAggregateTemplate.insert<BookEntity>(entity)
		val actualBook = bookRepository.findByIsbn(bookIsbn)

		// THEN
		actualBook shouldNotBe null
		actualBook?.isbn shouldBe bookIsbn
	}

	@Test
	fun whenCreateBookNotAuthenticatedThenNoAuditMetadata() {
		// GIVEN
		val bookIsbn = "1234561237"
		val domain = Book(
			isbn = bookIsbn,
			title = "Blood Meridian",
			author = "Cormac McCarthy",
			price = 9.90,
		)
		val entity = domain.toEntity()

		// WHEN
		val result = bookRepository.save(entity)

		// THEN
		result.apply {
			createdBy shouldBe null
			lastModifiedBy shouldBe null
		}
	}

	@Test
	@WithMockUser("john")
	fun whenCreateBookAuthenticatedThenAuditMetadata() {
		// GIVEN
		val bookIsbn = "1234561237"
		val domain = Book(
			isbn = bookIsbn,
			title = "Blood Meridian",
			author = "Cormac McCarthy",
			price = 9.90,
		)
		val entity = domain.toEntity()

		// WHEN
		val result = bookRepository.save(entity)

		// THEN
		result.apply {
			createdBy shouldBe "john"
			lastModifiedBy shouldBe "john"
		}
	}
}
