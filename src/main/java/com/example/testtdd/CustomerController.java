package com.example.testtdd;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping
    public ResponseEntity<Customer> addCustomer(@RequestBody Customer customer) throws Exception {
        try {
            Customer savedCustomer = customerService.addCustomer(customer);
            return ResponseEntity.ok(savedCustomer);
        } catch (Exception e) {
            throw new Exception("Error");
        }
    }

    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomers() {
        List<Customer> customers = customerService.getAllCustomers();
        return ResponseEntity.ok(customers);
    }
}
