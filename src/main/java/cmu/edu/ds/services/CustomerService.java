package cmu.edu.ds.services;


//import models.Customer;
import cmu.edu.ds.model.Customer;
import cmu.edu.ds.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;
//import repositories.CustomerRepository;

import java.net.URI;
import java.util.*;

@Service
public class CustomerService {

    /**
     * Injects the CustomerRepository to handle database operations.
     */
    @Autowired
    private CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }


    /**
     * Adds a new customer to the system if the userId doesn't already exist.
     *
     * @param customer The customer to be added
     * @param uriBuilder Builder for creating the location URI in the response
     * @return ResponseEntity with appropriate status code, headers, and body:
     *         - 201 Created with location header and customer data if successful
     *         - 422 Unprocessable Entity with error message if userId already exists
     * @throws RuntimeException If the database operation fails to insert the customer
     */
    public ResponseEntity<?> addCustomer(Customer customer, UriComponentsBuilder uriBuilder) {
        // Check if the userId already exists
        Optional<Customer> existingCustomer = customerRepository.getCustomerByUserId(customer.getUserId());
        if (existingCustomer.isPresent()) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "This user ID already exists in the system.");
            return ResponseEntity.status(422).body(errorResponse);
        }

        int rowsAffected = customerRepository.addCustomer(customer);

        if (rowsAffected > 0) {
            long id = customerRepository.getCustomerByUserId(customer.getUserId()).get().getId();
            customer.setId(id);

            URI location = uriBuilder
                    .path("/customers/{id}")
                    .buildAndExpand(id)
                    .toUri();

            return ResponseEntity
                    .created(location)
                    .body(customer);
        } else {
            throw new RuntimeException("Failed to save customer");
        }
    }

    public ResponseEntity<?> getCustomerById(Long id) {
        Optional<Customer> customer;
        if (id == null || id <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Invalid customer ID"));
        }
        try {
            customer = customerRepository.getCustomerById(id);
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Customer with ID " + id + " not found.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Invalid customer ID.");
        }

        return customer.<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Customer not found")));
    }


    public ResponseEntity<?> getCustomerByUserId(String userId) {
        if (userId == null || userId.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Invalid customer ID"));
        }

        Optional<Customer> customer;
        try {
            customer = customerRepository.getCustomerByUserId(userId);
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Customer with User ID " + userId + " not found."));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Invalid customer ID."));
        }

        return customer.<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Customer not found")));
    }



}