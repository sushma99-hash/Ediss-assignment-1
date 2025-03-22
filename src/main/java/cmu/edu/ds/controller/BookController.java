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

/**
 * REST controller that handles HTTP requests related to book operations.
 * Maps to the "/books" endpoint.
 */
@RestController
@RequestMapping("/books")
public class BookController {

    /**
     * Autowired book service to handle business logic operations.
     */
    @Autowired
    private BookService bookService;

    /**
     * Handles POST requests to create a new book.
     *
     * @param book The book object to be created, validated using annotations
     * @return ResponseEntity with the created book or error message
     */
    @PostMapping
    public ResponseEntity<?> addBook(@Valid @RequestBody Book book) {
        System.out.println("Received book: " + book); // Debugging: Print the book object
        try {
            // Attempt to save the book through the service layer
            Book savedBook = bookService.addBook(book);
            // Return HTTP 201 CREATED status with the saved book in response body
            return ResponseEntity.status(HttpStatus.CREATED).body(savedBook);
        } catch (IllegalArgumentException e) {
            // Return HTTP 422 UNPROCESSABLE_ENTITY if validation fails
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("{ \"message\": \"" + e.getMessage() + "\" }");
        }
    }

    /**
     * Handles PUT requests to update an existing book.
     *
     * @param ISBN The ISBN identifier of the book to update
     * @param book The updated book information
     * @return ResponseEntity with the updated book or error message
     */
    @PutMapping("/{ISBN}")
    public ResponseEntity<?> updateBook(@PathVariable String ISBN, @RequestBody Book book) {
        try {
            // Attempt to update the book with the given ISBN
            Book updatedBook = bookService.updateBook(ISBN, book);
            // Return HTTP 200 OK with the updated book if successful
            return ResponseEntity.ok(updatedBook);
        } catch (RuntimeException e) {
            // Return HTTP 404 NOT_FOUND if the book doesn't exist
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{ \"message\": \"Book not found\" }");
        }
    }

    /**
     * Handles GET requests to retrieve a book by its ISBN.
     *
     * @param ISBN The ISBN identifier of the book to retrieve
     * @return ResponseEntity with the found book or error message
     */
    @GetMapping("/{ISBN}")
    public ResponseEntity<?> getBook(@PathVariable String ISBN) {
        // Attempt to find the book by ISBN
        Optional<Book> book = bookService.getBookByISBN(ISBN);
        // Use Java 8 Optional to handle the case where the book is found or not
        return book.<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("message", "Book not found")));
    }
}