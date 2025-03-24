package cmu.edu.ds.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

/**
 * Entity class representing a Book in the system.
 * Maps to the "books" table in the database.
 */
@Entity
@Table(name = "books")
@JsonPropertyOrder({
        "ISBN",
        "title",
        "Author",
        "description",
        "genre",
        "price",
        "quantity"
})
public class Book {
    /**
     * Unique identifier for the book.
     * Uses ISBN as the primary key instead of a generated ID.
     */
    @Id
    @NotBlank(message = "ISBN is mandatory")
    @JsonProperty("ISBN")  // Force this exact name in JSON
    private String isbn;

    /**
     * Title of the book.
     * Cannot be blank as enforced by validation.
     */
    @NotBlank(message = "Title is mandatory")
    private String title;

    /**
     * Author of the book.
     * Cannot be blank as enforced by validation.
     */
    @NotBlank(message = "Author is mandatory")
    @JsonProperty("Author")
    private String author;

    /**
     * Description of the book.
     * Cannot be blank as enforced by validation.
     */
    @NotBlank(message = "Description is mandatory")
    private String description;

    /**
     * Genre/category of the book.
     * Cannot be blank as enforced by validation.
     */
    @NotBlank(message = "Genre is mandatory")
    private String genre;

    /**
     * Price of the book.
     * Stored as a BigDecimal with 10 digits total, 2 of which are decimal places.
     * Must be a valid number with 2 decimal places.
     */
    @NotNull(message = "Price is mandatory")
    @DecimalMin(value = "0.0", inclusive = true, message = "Price must be positive")
    @Digits(integer = 8, fraction = 2, message = "Price must have at most 2 decimal places")
    @Column(precision = 10, scale = 2) // Precision and scale for decimal values
    private BigDecimal price;

    /**
     * Available quantity of the book in inventory.
     * Must be non-negative (0 or greater).
     */
    @Min(value = 0, message = "Quantity must be greater than or equal to 0")
    private int quantity;

    // Getters and setters
    /**
     * Sets the ISBN of the book.
     * @param isbn The International Standard Book Number
     */
    public void setISBN(String isbn) {
        this.isbn = isbn;
    }

    /**
     * Sets the title of the book.
     * @param title The book title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Sets the author of the book.
     * @param author The name of the author
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * Sets the description of the book.
     * @param description The book description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Sets the genre of the book.
     * @param genre The book genre
     */
    public void setGenre(String genre) {
        this.genre = genre;
    }

    /**
     * Sets the price of the book.
     * @param price The book price as BigDecimal
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * Sets the available quantity of the book.
     * @param quantity The number of copies available
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Gets the ISBN of the book.
     * @return The book's ISBN
     */
    public String getISBN() {
        return isbn;
    }

    /**
     * Gets the title of the book.
     * @return The book's title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Gets the author of the book.
     * @return The book's author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Gets the description of the book.
     * @return The book's description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets the genre of the book.
     * @return The book's genre
     */
    public String getGenre() {
        return genre;
    }

    /**
     * Gets the price of the book.
     * @return The book's price as BigDecimal
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * Gets the available quantity of the book.
     * @return The number of copies available
     */
    public int getQuantity() {
        return quantity;
    }
}