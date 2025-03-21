package cmu.edu.ds.controller;

import cmu.edu.ds.model.Book;
import cmu.edu.ds.services.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping
    public ResponseEntity<?> addBook(@Valid @RequestBody Book book) {
        System.out.println("Received book: " + book); // Debugging: Print the book object
        try {
            Book savedBook = bookService.addBook(book);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedBook);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("{ \"message\": \"" + e.getMessage() + "\" }");
        }
    }

    @PutMapping("/{ISBN}")
    public ResponseEntity<?> updateBook(@PathVariable String ISBN, @RequestBody Book book) {
        try {
            Book updatedBook = bookService.updateBook(ISBN, book);
            return ResponseEntity.ok(updatedBook);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{ \"message\": \"Book not found\" }");
        }
    }

    @GetMapping("/{ISBN}")
    public ResponseEntity<?> getBook(@PathVariable String ISBN) {
        Optional<Book> book = bookService.getBookByISBN(ISBN);
        return book.<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("message", "Book not found")));
    }

}