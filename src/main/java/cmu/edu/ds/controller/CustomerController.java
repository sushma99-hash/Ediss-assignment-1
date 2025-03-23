package cmu.edu.ds.controller;

import cmu.edu.ds.model.Customer;
import cmu.edu.ds.services.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
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
    public ResponseEntity<?> addCustomer(@Valid @RequestBody Customer customer, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    errors.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }
        try {
            Customer savedCustomer = customerService.addCustomer(customer);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .header("Location", "/customers/" + savedCustomer.getId())
                    .body(savedCustomer);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body(Map.of("message", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "An error occurred while adding the customer."));
        }
    }

    /**
     * Handles GET requests to retrieve a customer by their ID.
     *
     * @param id The unique identifier of the customer to retrieve
     * @return ResponseEntity with the customer if found, or error message if not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getCustomerById(@PathVariable Long id) {
        try {
            // Attempt to find the customer by ID
            Optional<Customer> customer = customerService.getCustomerById(id);
            return customer.<ResponseEntity<?>>map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body(Map.of("message", "Customer not found")));
        } catch (Exception e) {
            // Handle any other exceptions
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "An error occurred while retrieving the customer."));
        }
    }

    /**
     * Handles GET requests to retrieve a customer by their userId.
     *
     * @param userId The userId associated with the customer to retrieve
     * @return ResponseEntity with the customer if found, or error message if not found
     */
    @GetMapping
    public ResponseEntity<?> getCustomerByUserId(@RequestParam String userId) {
        try {
            // Attempt to find the customer by user ID
            Optional<Customer> customer = customerService.getCustomerByUserId(userId);
            return customer.<ResponseEntity<?>>map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body(Map.of("message", "Customer not found")));
        } catch (Exception e) {
            // Handle any other exceptions
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "An error occurred while retrieving the customer."));
        }
    }

    /**
     * Exception handler for validation errors.
     * This will convert validation errors to a structured response.
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}