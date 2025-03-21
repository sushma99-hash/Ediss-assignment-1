package cmu.edu.ds.services;


import cmu.edu.ds.model.Customer;
import cmu.edu.ds.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public Customer addCustomer(Customer customer) {
        if (customerRepository.findByUserId(customer.getUserId()).isPresent()) {
            throw new IllegalArgumentException("This user ID already exists in the system.");
        }
        return customerRepository.save(customer);
    }

    public Optional<Customer> getCustomerById(Long id) {
        return customerRepository.findById(id);
    }

    public Optional<Customer> getCustomerByUserId(String userId) {
        return customerRepository.findByUserId(userId);
    }
}