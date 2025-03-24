package cmu.edu.ds.controller;

import cmu.edu.ds.model.Book;
import cmu.edu.ds.services.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//import static cmu.edu.ds.controller.CustomerController.logger;

/**
 * REST controller that handles HTTP requests related to book operations.
 * Maps to the "/books" endpoint.
 */
@RestController
@RequestMapping("/books")
public class BookController {
    private static final Logger logger = LoggerFactory.getLogger(BookController.class);

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
    public ResponseEntity<?> addBook(@Valid @RequestBody Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error -> {
                String fieldName = error.getField();
                String errorMessage = error.getDefaultMessage();
                errors.put(fieldName, errorMessage);
            });
            return ResponseEntity.badRequest().body(errors);
        }

        try {
            Book savedBook = bookService.addBook(book);
            return ResponseEntity.created(URI.create("/books/" + savedBook.getISBN()))
                    .body(savedBook);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.unprocessableEntity()
                    .body(Map.of("message", e.getMessage()));
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
    public ResponseEntity<?> updateBook(@PathVariable String ISBN, @Valid @RequestBody Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    errors.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }
        try {
            // Check if ISBNs match
            if (!ISBN.equals(book.getISBN())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Map.of("message", "ISBN in URL does not match ISBN in request body"));
            }

            // Set the ISBN to ensure it matches the path variable
            book.setISBN(ISBN);
            // Attempt to update the book with the given ISBN
            Book updatedBook = bookService.updateBook(ISBN, book);
            // Return HTTP 200 OK with the updated book if successful
            return ResponseEntity.ok(updatedBook);
        } catch (IllegalArgumentException e) {
            // For validation errors
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", e.getMessage()));
        } catch (RuntimeException e) {
            // For book not found
            if (e.getMessage().contains("not found")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("message", "Book not found"));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Map.of("message", e.getMessage()));
            }
        } catch (Exception e) {
            // Handle any other exceptions
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "An error occurred while updating the book."));
        }
    }

    /**
     * Handles GET requests to retrieve a book by its ISBN.
     * This endpoint handles the path /books/{ISBN}
     *
     * @param ISBN The ISBN identifier of the book to retrieve
     * @return ResponseEntity with the found book or error message
     */
    @GetMapping("/{ISBN}")
    public ResponseEntity<?> getBook(@PathVariable String ISBN) {
        Optional<Book> book = bookService.getBookByISBN(ISBN);
        return book.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Alternative endpoint to retrieve a book by its ISBN.
     * This endpoint handles the path /books/isbn/{ISBN}
     *
     * @param ISBN The ISBN identifier of the book to retrieve
     * @return ResponseEntity with the found book or error message
     */
    @GetMapping("/isbn/{ISBN}")
    public ResponseEntity<?> getBookByIsbn(@PathVariable String ISBN) {
        return getBook(ISBN);
    }
}