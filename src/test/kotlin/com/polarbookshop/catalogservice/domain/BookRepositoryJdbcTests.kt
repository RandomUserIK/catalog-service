package com.polarbookshop.catalogservice.domain

import com.polarbookshop.catalogservice.config.DataConfig
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.context.annotation.Import
import org.springframework.data.jdbc.core.JdbcAggregateTemplate
import org.springframework.test.context.ActiveProfiles

@DataJdbcTest
@Import(DataConfig::class)
@AutoConfigureTestDatabase(
    replace = AutoConfigureTestDatabase.Replace.NONE,
)
@ActiveProfiles("integration")
class BookRepositoryJdbcTests(
    @Autowired private val bookRepository: BookRepository,
    @Autowired private val jdbcAggregateTemplate: JdbcAggregateTemplate,
) {
    @Test
    fun findBookByIsbnWhenExisting() {
        // GIVEN
        val bookIsbn = "1234561237"
        val book = Book.of(bookIsbn, "Title", "Author", 12.90)

        // WHEN
        jdbcAggregateTemplate.insert(book)
        val result = bookRepository.findByIsbn(bookIsbn)

        // THEN
        result shouldNotBe null
        result!!.apply {
            isbn shouldBe bookIsbn
        }
    }
}
