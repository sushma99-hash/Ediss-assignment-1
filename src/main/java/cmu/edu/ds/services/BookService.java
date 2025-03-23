package cmu.edu.ds.services;

import cmu.edu.ds.model.Book;
import cmu.edu.ds.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

@Service
public class BookService {

    private static final Logger logger = LoggerFactory.getLogger(BookService.class);

    @Autowired
    private BookRepository bookRepository;

    public Book addBook(Book book) {
        logger.info("Adding book: {}", book);
        if (bookRepository.existsById(book.getISBN())) {
            logger.warn("ISBN already exists: {}", book.getISBN());
            throw new IllegalArgumentException("This ISBN already exists in the system.");
        }
        return bookRepository.save(book);
    }

    public Book updateBook(String ISBN, Book bookDetails) {
        // Validate book details first
        validateBook(bookDetails);

        // Check if book exists
        if (!bookRepository.existsById(ISBN)) {
            throw new RuntimeException("Book not found");
        }

        // Make sure ISBN matches
        bookDetails.setISBN(ISBN);

        // Save the updated book
        return bookRepository.save(bookDetails);
    }

    public Optional<Book> getBookByISBN(String ISBN) {
        return bookRepository.findById(ISBN);
    }

    private void validateBook(Book book) {
        if (book.getISBN() == null || book.getISBN().trim().isEmpty()) {
            throw new IllegalArgumentException("ISBN is mandatory");
        }
        if (book.getTitle() == null || book.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Title is mandatory");
        }
        if (book.getAuthor() == null || book.getAuthor().trim().isEmpty()) {
            throw new IllegalArgumentException("Author is mandatory");
        }
        if (book.getDescription() == null || book.getDescription().trim().isEmpty()) {
            throw new IllegalArgumentException("Description is mandatory");
        }
        if (book.getGenre() == null || book.getGenre().trim().isEmpty()) {
            throw new IllegalArgumentException("Genre is mandatory");
        }
        if (book.getPrice() == null) {
            throw new IllegalArgumentException("Price is mandatory");
        }
        if (book.getQuantity() < 0) {
            throw new IllegalArgumentException("Quantity must be greater than or equal to 0");
        }
    }
}