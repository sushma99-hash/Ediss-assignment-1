package cmu.edu.ds.controller;


import cmu.edu.ds.model.Customer;
import cmu.edu.ds.services.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * REST controller that handles HTTP requests related to customer operations.
 * Maps to the "/customers" endpoint.
 */
@RestController
@RequestMapping("/customers")
public class CustomerController {

    /**
     * Logger instance for this controller to record operations and errors.
     */
    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    /**
     * Autowired customer service to handle business logic operations.
     */
    @Autowired
    private CustomerService customerService;

    /**
     * Handles POST requests to create a new customer.
     *
     * @param customer The customer object to be created, validated using annotations
     * @return ResponseEntity with the created customer or error message
     */
    @PostMapping
    public ResponseEntity<?> addCustomer(@RequestBody @Valid Customer customer) {
        // Log the customer information being added
        logger.info("Adding customer: {}", customer);
        try {
            // Attempt to save the customer through the service layer
            Customer savedCustomer = customerService.addCustomer(customer);
            // Return HTTP 201 CREATED status with the saved customer in response body
            return ResponseEntity.status(HttpStatus.CREATED).body(savedCustomer);
        } catch (IllegalArgumentException e) {
            // Log the error that occurred during customer creation
            logger.error("Error adding customer: {}", e.getMessage());
            // Return HTTP 422 UNPROCESSABLE_ENTITY if validation fails
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(e.getMessage());
        }
    }

    /**
     * Handles GET requests to retrieve a customer by their ID.
     *
     * @param id The unique identifier of the customer to retrieve
     * @return Optional containing the customer if found, or empty if not found
     */
    @GetMapping("/{id}")
    public Optional<Customer> getCustomerById(@PathVariable Long id) {
        // Log the customer ID being requested
        logger.info("Retrieving customer by ID: {}", id);
        // Attempt to find the customer by ID
        return customerService.getCustomerById(id);
    }

    /**
     * Handles GET requests to retrieve a customer by their userId.
     *
     * @param userId The userId associated with the customer to retrieve
     * @return Optional containing the customer if found, or empty if not found
     */
    @GetMapping
    public Optional<Customer> getCustomerByUserId(@RequestParam String userId) {
        // Log the userId being requested
        logger.info("Retrieving customer by userId: {}", userId);
        // Attempt to find the customer by userId
        return customerService.getCustomerByUserId(userId);
    }
}