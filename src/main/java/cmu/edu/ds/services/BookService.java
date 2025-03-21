package cmu.edu.ds.services;

import cmu.edu.ds.model.Book;
import cmu.edu.ds.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public Book addBook(Book book) {
        if (bookRepository.existsById(book.getISBN())) {
            throw new IllegalArgumentException("This ISBN already exists in the system.");
        }
        return bookRepository.save(book);
    }

    public Book updateBook(String ISBN, Book bookDetails) {
        Book book = bookRepository.findById(ISBN)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        book.setTitle(bookDetails.getTitle());
        book.setAuthor(bookDetails.getAuthor());
        book.setDescription(bookDetails.getDescription());
        book.setGenre(bookDetails.getGenre());
        book.setPrice(bookDetails.getPrice());
        book.setQuantity(bookDetails.getQuantity());

        return bookRepository.save(book);
    }

    public Optional<Book> getBookByISBN(String ISBN) {
        return bookRepository.findById(ISBN);
    }
}