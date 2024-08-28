package com.example.testtdd;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public Customer addCustomer(Customer customer) throws Exception {
        Optional<Customer> existingCustomer = customerRepository.findByEmail(customer.getEmail());

        if (existingCustomer.isPresent()) {
            throw new Exception("Email đã tồn tại.");
        }
        customer.setCreatedAt(LocalDateTime.now());
        return customerRepository.save(customer);
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }
}
