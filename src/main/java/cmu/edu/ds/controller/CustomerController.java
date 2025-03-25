package cmu.edu.ds.controller;//package controllers;

import cmu.edu.ds.model.Customer;
import cmu.edu.ds.services.CustomerService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping
    public ResponseEntity<?> addCustomer(@RequestBody @Valid Customer customer, UriComponentsBuilder uriBuilder) {
        return customerService.addCustomer(customer, uriBuilder);

    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getCustomerById(@PathVariable Long id) {
        return customerService.getCustomerById(id);
    }

    @GetMapping
    public ResponseEntity<?> getCustomerByUserId(@RequestParam @Email String userId) {
        return customerService.getCustomerByUserId(userId);
    }
}

