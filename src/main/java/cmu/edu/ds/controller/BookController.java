package cmu.edu.ds.controller;

import cmu.edu.ds.model.Books;
import cmu.edu.ds.services.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;


@RestController
@RequestMapping("/books")
@Validated
public class BookController {

    @Autowired
    private BookService bookService;

    // Add Book
    @PostMapping
    public ResponseEntity<?> addBook(@RequestBody @Valid Books book, UriComponentsBuilder uriBuilder) {
        return bookService.addBook(book, uriBuilder);
    }

    // Update Book
    @PutMapping("/{isbn}")
    public ResponseEntity<?> updateBook(@PathVariable String isbn, @RequestBody @Valid Books book) {
        return bookService.updateBook(isbn, book);
    }

    // Get Book by ISBN
    @GetMapping("/{isbn}")
    public ResponseEntity<?> getBookByIsbn(@PathVariable String isbn) {
        return bookService.getBookByIsbn(isbn);
    }

    // Retrieve Book using alternate route (same response)
    @GetMapping("/isbn/{isbn}")
    public ResponseEntity<?> getBookByIsbnAlternative(@PathVariable String isbn) {
        return bookService.getBookByIsbn(isbn);
    }
}

