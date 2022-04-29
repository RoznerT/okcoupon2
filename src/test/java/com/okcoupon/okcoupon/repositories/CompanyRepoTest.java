package com.okcoupon.okcoupon.repositories;

import com.okcoupon.okcoupon.beans.Company;
import com.okcoupon.okcoupon.exceptions.InvalidEmailException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@ContextConfiguration
@RunWith(SpringRunner.class)
@SpringBootTest()
public class CompanyRepoTest {

    @Autowired
    private CompanyRepo companyRepoUnderTest;

    @Autowired
    ApplicationContext ctx;

    private int COUNT_COMPANIES = 0;  //need to change if add more companies to dataBase in MockDataBuilder.class
    private final int WRONG_ID = 42;

    private final Company newCompany = Company.builder()
            .id(0)
            .name("I'm new company")
            .email("newCompany@gmail.com")
            .password("new")
            .build();

    private final Company companyToGet = Company.builder()
            .id(1)
            .name("hyundai")
            .email("hyundai@gmail.com")
            .password("1234")
            .build();

    @Test
    public void contextLoads() {
        Assertions.assertThat(ctx).isNotNull();
    }

    @Before
    @Test
    public void setUp() {
            COUNT_COMPANIES = (int) companyRepoUnderTest.count();
            Assertions.assertThat(companyRepoUnderTest.findById(WRONG_ID)).isEmpty();
            Assertions.assertThat(companyRepoUnderTest.getById(companyToGet.getId())).isNotNull();
    }

    @Test
    public void existsByEmailAndPasswordTest() {
        boolean expected = companyRepoUnderTest.existsByEmailAndPassword(companyToGet.getEmail(), companyToGet.getPassword());
        boolean expected2 = companyRepoUnderTest.existsByEmailAndPassword("wrongEmail@gmail.com", "wrong");
        Assertions.assertThat(expected).isTrue();
        Assertions.assertThat(expected2).isFalse();
    }

    @Test
    public void existsByEmailAndNameTest() {
        boolean expected = companyRepoUnderTest.existsByEmailAndName(companyToGet.getEmail(), companyToGet.getName());
        boolean expected2 = companyRepoUnderTest.existsByEmailAndName("wrongEmail@gmail.com", "wrong");
        Assertions.assertThat(expected).isTrue();
        Assertions.assertThat(expected2).isFalse();
    }

    @Test
    public void existsByEmailTest() {
        boolean expected = companyRepoUnderTest.existsByEmail(companyToGet.getEmail());
        boolean expected2 = companyRepoUnderTest.existsByEmail("wrongEmail@gmail.com");
        Assertions.assertThat(expected).isTrue();
        Assertions.assertThat(expected2).isFalse();
    }

    @Test
    public void existsByNameTest() {
        boolean expected = companyRepoUnderTest.existsByName(companyToGet.getName());
        boolean expected2 = companyRepoUnderTest.existsByName("wrongName");
        Assertions.assertThat(expected).isTrue();
        Assertions.assertThat(expected2).isFalse();
    }

    @Test
    public void findCompanyByEmailAndPasswordTest() {
        int expected = companyRepoUnderTest.findCompanyByEmailAndPassword(companyToGet.getEmail(), companyToGet.getPassword()).getId();
        Company expected2 = companyRepoUnderTest.findCompanyByEmailAndPassword("wrongEmail@gmail.com", "wrong");
        Assertions.assertThat(expected).isEqualTo(companyToGet.getId());
        Assertions.assertThat(expected2).isEqualTo(null);
    }

    @Test
    public void existByIdTest() {
        boolean expected = companyRepoUnderTest.existsById(companyToGet.getId());
        boolean expected2 = companyRepoUnderTest.existsById(WRONG_ID);
        Assertions.assertThat(expected).isTrue();
        Assertions.assertThat(expected2).isFalse();
    }

    @Test
    public void deleteByIdTest() {
        companyRepoUnderTest.saveAndFlush(newCompany);
        int expected = companyRepoUnderTest.findCompanyByEmailAndPassword(newCompany.getEmail(),newCompany.getPassword()).getId();
        Assertions.assertThat(companyRepoUnderTest.existsById(expected)).isTrue();

        companyRepoUnderTest.deleteById(expected);
        Assertions.assertThat(companyRepoUnderTest.count()).isEqualTo(COUNT_COMPANIES);
        Assertions.assertThat(companyRepoUnderTest.existsById(expected)).isFalse();
    }

    @Test
    public void getByIdTest() {
        Company expected = companyRepoUnderTest.getById(companyToGet.getId());
        Assertions.assertThat(expected).isInstanceOf(Company.class);
        Assertions.assertThat(expected.getName()).isEqualTo(companyToGet.getName());
        Assertions.assertThat(expected.getEmail()).isEqualTo(companyToGet.getEmail());
        Assertions.assertThat(expected.getPassword()).isEqualTo(companyToGet.getPassword());
    }

    @Test
    public void findAllTest() {
        List<Company> expected = companyRepoUnderTest.findAll();
        Assertions.assertThat(expected.size()).isEqualTo(COUNT_COMPANIES);
    }

    @Test
    public void saveTest() {
        companyRepoUnderTest.saveAndFlush(newCompany);
        Company expected = companyRepoUnderTest.findCompanyByEmailAndPassword(newCompany.getEmail(),newCompany.getPassword());
        Assertions.assertThat(expected).isInstanceOf(Company.class);
        Assertions.assertThat(expected.getId()).isNotZero();

        companyRepoUnderTest.deleteById(expected.getId());

    }

    @Test
    public void updateTest() {
        try {
            Company company = companyRepoUnderTest.getById(companyToGet.getId());
            company.setEmail("emailUpdateTest@gmail.com");
            companyRepoUnderTest.save(company);

            Company expected = companyRepoUnderTest.getById(company.getId());
            Assertions.assertThat(expected).isInstanceOf(Company.class);
            Assertions.assertThat(expected.getEmail()).isEqualTo(company.getEmail());

            company.setEmail("hyundai@gmail.com");
            companyRepoUnderTest.save(company);
        } catch (InvalidEmailException err) {
            System.out.println(err.getMessage());
        }
    }
}