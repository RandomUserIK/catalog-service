package com.polarbookshop.catalogservice.web

import com.polarbookshop.catalogservice.domain.model.Book
import com.polarbookshop.catalogservice.domain.BookService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("books")
class BookController(
	private val bookService: BookService,
) {

	@GetMapping
	fun getBooks() =
		bookService.viewBookList()

	@GetMapping("{isbn}")
	fun getByIsbn(@PathVariable isbn: String) =
		bookService.viewBookDetails(isbn)

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	fun post(@Valid @RequestBody book: Book) =
		bookService.addBookToCatalog(book)

	@DeleteMapping("{isbn}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	fun delete(@PathVariable isbn: String) =
		bookService.removeBookFromCatalog(isbn)

	@PutMapping("{isbn}")
	fun put(@PathVariable isbn: String, @Valid @RequestBody book: Book) =
		bookService.editBookDetails(isbn, book)
}
