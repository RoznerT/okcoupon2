package com.okcoupon.okcoupon.repositories;

import com.okcoupon.okcoupon.beans.Customer;
import com.okcoupon.okcoupon.exceptions.InvalidEmailException;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@ContextConfiguration
@RunWith(SpringRunner.class)
@SpringBootTest()
public class CustomerRepoTest {

    @Autowired
    private CustomerRepo customerRepoUnderTest;

    @Autowired
    ApplicationContext ctx;

    private int COUNT_CUSTOMERS = 0;
    private final int WRONG_ID = 42; //need to change if add more companies to data-base in MockDataBuilder.class

    private Customer customerToGet = Customer.builder()
            .id(1)
            .firstName("itzik")
            .lastName("seaman-tof")
            .email("itzik@gmail.com")
            .password("1234")
            .build();

    private Customer newCustomer = Customer.builder()
            .id(0)
            .firstName("Nadav")
            .lastName("Honig")
            .email("nadavi@gmail.com")
            .password("1010")
            .build();

    @Test
    public void contextLoads() {
        Assertions.assertThat(ctx).isNotNull();
    }

    @Before
    @Test
    public void setUp() {
        COUNT_CUSTOMERS = (int) customerRepoUnderTest.count();
        Assertions.assertThat(customerRepoUnderTest.findById(WRONG_ID)).isEmpty();
        Assertions.assertThat(customerRepoUnderTest.getById(customerToGet.getId())).isNotNull();
    }

    @Test
    public void existsByEmailTest() {
        boolean expected = customerRepoUnderTest.existsByEmail(customerToGet.getEmail());
        boolean expected2 = customerRepoUnderTest.existsByEmail("wrongEmail@gmail.com");
        Assertions.assertThat(expected).isTrue();
        Assertions.assertThat(expected2).isFalse();
    }

    @Test
    public void existsByEmailAndPasswordTest() {
        boolean expected = customerRepoUnderTest.existsByEmailAndPassword(customerToGet.getEmail(), customerToGet.getPassword());
        boolean expected2 = customerRepoUnderTest.existsByEmailAndPassword("wrongEmail@gmail.com", "wrong");
        Assertions.assertThat(expected).isTrue();
        Assertions.assertThat(expected2).isFalse();
    }

    @Test
    public void findIdByEmailAndPasswordTest() {
        int expected = customerRepoUnderTest.findCustomerByEmailAndPassword(customerToGet.getEmail(), customerToGet.getPassword()).getId();
        Customer expected2 = customerRepoUnderTest.findCustomerByEmailAndPassword("wrongEmail@gmail.com", "wrong");
        Assertions.assertThat(expected).isEqualTo(customerToGet.getId());
        Assertions.assertThat(expected2).isEqualTo(null);
    }

    @Test
    public void deleteByIdTestTest() {
        customerRepoUnderTest.saveAndFlush(newCustomer);
        int expected = customerRepoUnderTest.findCustomerByEmailAndPassword(newCustomer.getEmail(),newCustomer.getPassword()).getId();
        Assertions.assertThat(customerRepoUnderTest.existsById(expected)).isTrue();

        customerRepoUnderTest.deleteById(expected);
        Assertions.assertThat(customerRepoUnderTest.count()).isEqualTo(COUNT_CUSTOMERS);
        Assertions.assertThat(customerRepoUnderTest.findById(expected).isEmpty());
    }

    @Test
    public void getByIdTest() {
        Customer expected = customerRepoUnderTest.getById(customerToGet.getId());
        Assertions.assertThat(expected).isInstanceOf(Customer.class);
        Assertions.assertThat(expected.getFirstName()).isEqualTo(customerToGet.getFirstName());
        Assertions.assertThat(expected.getLastName()).isEqualTo(customerToGet.getLastName());
        Assertions.assertThat(expected.getEmail()).isEqualTo(customerToGet.getEmail());
        Assertions.assertThat(expected.getPassword()).isEqualTo(customerToGet.getPassword());
    }

    @Test
    public void findAllTest() {
        List<Customer> expected = customerRepoUnderTest.findAll();
        Assertions.assertThat(expected.size()).isEqualTo(COUNT_CUSTOMERS);
    }

    @Test
    public void saveTest() {
        customerRepoUnderTest.saveAndFlush(newCustomer);
        Customer expected = customerRepoUnderTest.getById(newCustomer.getId());
        Assertions.assertThat(expected).isInstanceOf(Customer.class);
        Assertions.assertThat(expected.getId()).isNotZero();

        customerRepoUnderTest.deleteById(expected.getId());
    }

    @Test
    public void updateTest() {
        try {
            Customer customer = customerRepoUnderTest.getById(customerToGet.getId());
            customer.setEmail("emailUpdateTest@gmail.com");
            customerRepoUnderTest.save(customer);

            Customer expected = customerRepoUnderTest.getById(customer.getId());
            Assertions.assertThat(expected).isInstanceOf(Customer.class);
            Assertions.assertThat(expected.getEmail()).isEqualTo(customer.getEmail());

            customer.setEmail("itzik@gmail.com");
            customerRepoUnderTest.save(customer);
        } catch (InvalidEmailException err) {
            System.out.println(err.getMessage());
        }
    }


}