package com.example.testtdd;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
@SpringBootTest
public class CustomerControllerTest {

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private CustomerController customerController;

    @BeforeClass
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAddCustomer_EmailAlreadyExists() throws Exception {
        Customer customer = new Customer();
        customer.setEmail("nql4901@gmail.com");

        when(customerService.addCustomer(customer)).thenThrow(new Exception("Email đã tồn tại."));

        ResponseEntity<?> response = customerController.addCustomer(customer);

        Assert.assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
        Assert.assertEquals(response.getBody(), "Email đã tồn tại.");
        verify(customerService, times(1)).addCustomer(customer);
    }

    @Test
    public void testAddCustomer() throws Exception {
        Customer customer = new Customer();
        customer.setName("nql");
        customer.setNumber("123456");
        customer.setEmail("nql4901@gmail.com");

        when(customerService.addCustomer(customer)).thenReturn(customer);

        ResponseEntity<Customer> response = customerController.addCustomer(customer);

        Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
        Assert.assertEquals(response.getBody().getName(), "nql");
        verify(customerService, times(1)).addCustomer(customer);
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

        when(customerService.getAllCustomers()).thenReturn(customers);

        ResponseEntity<List<Customer>> response = customerController.getAllCustomers();

        Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
        Assert.assertEquals(response.getBody().size(), 2);
        verify(customerService, times(1)).getAllCustomers();
    }
}