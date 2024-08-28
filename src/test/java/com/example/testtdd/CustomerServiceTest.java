package com.example.testtdd;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


import static org.mockito.Mockito.*;

@SpringBootTest
public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    @BeforeClass
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAddCustomer_EmailAlreadyExists() {
        Customer customer = new Customer();
        customer.setEmail("nql4901@gmail.com");

        when(customerRepository.findByEmail(customer.getEmail())).thenReturn(Optional.of(customer));

        try {
            customerService.addCustomer(customer);
            Assert.fail("Expected exception was not thrown");
        } catch (Exception e) {
            Assert.assertEquals(e.getMessage(), "Email đã tồn tại.");
        }

        verify(customerRepository, never()).save(customer);
    }

    @Test
    public void testAddCustomer() throws Exception {
        Customer customer = new Customer();
        customer.setName("nql");
        customer.setNumber("123456");
        customer.setEmail("nql4901@gmail.com");

        when(customerRepository.save(customer)).thenReturn(customer);

        Customer result = customerService.addCustomer(customer);

        Assert.assertNotNull(result);
        Assert.assertEquals(result.getName(), "nql");
        verify(customerRepository, times(1)).save(customer);
    }

    @Test
    public void testGetAllCustomers() {
        Customer customer1 = new Customer();
        customer1.setName("nql");
        customer1.setNumber("123456");
        customer1.setEmail("nql4901@gmail.com");

        Customer customer2 = new Customer();
        customer2.setName("bruno");
        customer2.setNumber("123456");
        customer2.setEmail("bruno@gmail.com");

        List<Customer> customers = Arrays.asList(customer1, customer2);

        when(customerRepository.findAll())
                .thenReturn(customers);

        List<Customer> result = customerService.getAllCustomers();

        Assert.assertEquals(result.size(), 2);
        verify(customerRepository, times(1))
                .findAll();
    }
}