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

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    private CustomerService customerService;

    @PostMapping
    public ResponseEntity<?> addCustomer(@RequestBody @Valid Customer customer) {
        logger.info("Adding customer: {}", customer);
        try {
            Customer savedCustomer = customerService.addCustomer(customer);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedCustomer);
        } catch (IllegalArgumentException e) {
            logger.error("Error adding customer: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public Optional<Customer> getCustomerById(@PathVariable Long id) {
        logger.info("Retrieving customer by ID: {}", id);
        return customerService.getCustomerById(id);
    }

    @GetMapping
    public Optional<Customer> getCustomerByUserId(@RequestParam String userId) {
        logger.info("Retrieving customer by userId: {}", userId);
        return customerService.getCustomerByUserId(userId);
    }
}