package com.okcoupon.okcoupon.restTemplate;

import com.okcoupon.okcoupon.auth.ClientType;
import com.okcoupon.okcoupon.auth.UserDetails;
import com.okcoupon.okcoupon.beans.Company;
import com.okcoupon.okcoupon.beans.Coupon;
import com.okcoupon.okcoupon.beans.Customer;
import com.okcoupon.okcoupon.exceptions.ClientErrorException;
import com.okcoupon.okcoupon.exceptions.InvalidEmailException;
import com.okcoupon.okcoupon.exceptions.ServerErrorException;
import com.okcoupon.okcoupon.utils.ConsoleColors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.*;

@Component
@Order(2)
public class AdministratorRestTemplate implements CommandLineRunner {

    @Autowired
    private RestTemplate restTemplate;

    private String token;

    final static String login = "http://localhost:8080/administrator/Login";
    final static String allCompanies = "http://localhost:8080/administrator/allCompanies";
    final static String allCustomers = "http://localhost:8080/administrator/allCustomers";
    final static String getCompany = "http://localhost:8080/administrator/getCompany/{id}";
    final static String getCustomer = "http://localhost:8080/administrator/getCustomer/{id}";
    final static String companyCoupons = "http://localhost:8080/administrator/getCompanyCoupons/{id}";
    final static String customerCoupons = "http://localhost:8080/administrator/getCustomerPurchase/{id}";
    final static String newCompany = "http://localhost:8080/administrator/newCompany";
    final static String newCustomer = "http://localhost:8080/administrator/newCustomer";
    final static String updateCompany = "http://localhost:8080/administrator/updateCompany";
    final static String updateCustomer = "http://localhost:8080/administrator/updateCustomer";
    final static String deleteCompany = "http://localhost:8080/administrator/deleteCompany/{id}";
    final static String deleteCustomer = "http://localhost:8080/administrator/deleteCustomer/{id}";

    private HttpEntity<?> getHttpEntity(String token, Object companyOrCustomer) {
        HttpEntity<?> httpEntity;
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization", this.token);
        if (companyOrCustomer instanceof Company || companyOrCustomer instanceof Customer) {
            httpEntity = new HttpEntity<>(companyOrCustomer, httpHeaders);
        } else httpEntity = new HttpEntity<>(httpHeaders);
        return httpEntity;
    }

    private void updateToken(ResponseEntity<?> responseEntity){
        String responseTokenHeader = responseEntity.getHeaders().getFirst("Authorization");
        if (responseTokenHeader != null && responseTokenHeader.startsWith("Bearer")) {
            this.token = responseTokenHeader.substring(7);
        }
    }


    private void login(UserDetails userDetails) {
        try {
            ResponseEntity<Void> object = restTemplate.exchange(login, HttpMethod.POST, new HttpEntity<>(userDetails), void.class);
            if (object.getStatusCode().is4xxClientError()) {
                throw new ClientErrorException(ConsoleColors.RED + "Client error:" + object.getStatusCode().name() + ConsoleColors.RESET);
            }
            if (object.getStatusCode().is5xxServerError()) {
                throw new ServerErrorException(ConsoleColors.RED + "Server error:" + object.getStatusCode().name() + ConsoleColors.RESET);
            }
            this.token= object.getHeaders().getFirst("Authorization");
            System.out.println(ConsoleColors.PURPLE_BACKGROUND + userDetails.getUserName() + " logged through restTemplate " + LocalDate.now() + "\nThe token: " + token + ConsoleColors.RESET);
        } catch (ServerErrorException | ClientErrorException error) {
            System.out.println(error.getMessage());
        }
    }

    private Company getCompany(int id) {
        try {
            Map<String, String> param = new HashMap<>();
            param.put("id", String.valueOf(id));
            ResponseEntity<Company> getOneCompany = restTemplate.exchange(getCompany, HttpMethod.GET, getHttpEntity(this.token, null), Company.class, param);
            if (getOneCompany.getStatusCode().is4xxClientError()) {
                throw new ClientErrorException(ConsoleColors.RED + "Client error:" + getOneCompany.getStatusCode().name() + ConsoleColors.RESET);
            }
            if (getOneCompany.getStatusCode().is5xxServerError()) {
                throw new ServerErrorException(ConsoleColors.RED + "Server error:" + getOneCompany.getStatusCode().name() + ConsoleColors.RESET);
            }
            Company company = getOneCompany.getBody();
            System.out.println(ConsoleColors.BLUE + "company through restTemplate");
            System.out.println(company + ConsoleColors.RESET);
            updateToken(getOneCompany);
            return company;
        } catch (ServerErrorException | ClientErrorException error) {
            System.out.println(error.getMessage());
        }
        return null;
    }

    private Customer getCustomer(int id) {
        try {
            Map<String, String> param = new HashMap<>();
            param.put("id", String.valueOf(id));
            ResponseEntity<Customer> getOneCustomer = restTemplate.exchange(getCustomer, HttpMethod.GET, getHttpEntity(this.token, null), Customer.class, param);
            if (getOneCustomer.getStatusCode().is4xxClientError()) {
                throw new ClientErrorException(ConsoleColors.RED + "Client error:" + getOneCustomer.getStatusCode().name() + ConsoleColors.RESET);
            }
            if (getOneCustomer.getStatusCode().is5xxServerError()) {
                throw new ServerErrorException(ConsoleColors.RED + "Server error:" + getOneCustomer.getStatusCode().name() + ConsoleColors.RESET);
            }
            Customer customer = getOneCustomer.getBody();
            System.out.println(ConsoleColors.BLUE + "customer through restTemplate");
            System.out.println(customer + ConsoleColors.RESET);
            updateToken(getOneCustomer);
            return customer;
        } catch (ServerErrorException | ClientErrorException error) {
            System.out.println(error.getMessage());
        }
        return null;
    }

    private List<Company> allCompanies() {
        try {
            ResponseEntity<Company[]> getAllCompanies = restTemplate.exchange(allCompanies, HttpMethod.GET, getHttpEntity(this.token, null), Company[].class);
            if (getAllCompanies.getStatusCode().is4xxClientError()) {
                throw new ClientErrorException(ConsoleColors.RED + "Client error:" + getAllCompanies.getStatusCode().name() + ConsoleColors.RESET);
            }
            if (getAllCompanies.getStatusCode().is5xxServerError()) {
                throw new ServerErrorException(ConsoleColors.RED + "Server error:" + getAllCompanies.getStatusCode().name() + ConsoleColors.RESET);
            }
            List<Company> companies = Arrays.asList(getAllCompanies.getBody());
            System.out.println(ConsoleColors.BLUE + "all companies through restTemplate");
            companies.forEach(System.out::println);
            System.out.println(ConsoleColors.RESET);
            updateToken(getAllCompanies);
            return companies;
        } catch (ServerErrorException | ClientErrorException error) {
            System.out.println(error.getMessage());
        }
        return null;
    }

    private List<Customer> allCustomers() {
        try {
            ResponseEntity<Customer[]> getAllCustomers = restTemplate.exchange(allCustomers, HttpMethod.GET, getHttpEntity(this.token, null), Customer[].class);
            if (getAllCustomers.getStatusCode().is4xxClientError()) {
                throw new ClientErrorException(ConsoleColors.RED + "Client error:" + getAllCustomers.getStatusCode().name() + ConsoleColors.RESET);
            }
            if (getAllCustomers.getStatusCode().is5xxServerError()) {
                throw new ServerErrorException(ConsoleColors.RED + "Server error:" + getAllCustomers.getStatusCode().name() + ConsoleColors.RESET);
            }
            List<Customer> customers = Arrays.asList(getAllCustomers.getBody());
            System.out.println(ConsoleColors.BLUE + "all customers through restTemplate");
            customers.forEach(System.out::println);
            System.out.println(ConsoleColors.RESET);
            updateToken(getAllCustomers);
            return customers;
        } catch (ServerErrorException | ClientErrorException error) {
            System.out.println(error.getMessage());
        }
        return null;
    }

    private void customerCoupons(int id) {
        try {
            Map<String, String> param = new HashMap<>();
            param.put("id", String.valueOf(id));
            ResponseEntity<Coupon[]> getCustomerCoupons = restTemplate.exchange(customerCoupons, HttpMethod.GET, getHttpEntity(this.token, null), Coupon[].class, param);
            if (getCustomerCoupons.getStatusCode().is4xxClientError()) {
                throw new ClientErrorException(ConsoleColors.RED + "Client error:" + getCustomerCoupons.getStatusCode().name() + ConsoleColors.RESET);
            }
            if (getCustomerCoupons.getStatusCode().is5xxServerError()) {
                throw new ServerErrorException(ConsoleColors.RED + "Server error:" + getCustomerCoupons.getStatusCode().name() + ConsoleColors.RESET);
            }
            List<Coupon> customerCoupons = Arrays.asList(getCustomerCoupons.getBody());
            System.out.println(ConsoleColors.BLUE + "customer coupons through restTemplate");
            customerCoupons.forEach(Coupon::toPrint);
            System.out.println(ConsoleColors.RESET);
            updateToken(getCustomerCoupons);
        } catch (ServerErrorException | ClientErrorException error) {
            System.out.println(error.getMessage());
        }
    }

    private List<Coupon> companyCoupons(int id) {
        try {
            Map<String, String> param = new HashMap<>();
            param.put("id", String.valueOf(id));
            ResponseEntity<Coupon[]> getCompanyCoupons = restTemplate.exchange(companyCoupons, HttpMethod.GET, getHttpEntity(this.token, null), Coupon[].class, param);
            if (getCompanyCoupons.getStatusCode().is4xxClientError()) {
                throw new ClientErrorException(ConsoleColors.RED + "Client error:" + getCompanyCoupons.getStatusCode().name() + ConsoleColors.RESET);
            }
            if (getCompanyCoupons.getStatusCode().is5xxServerError()) {
                throw new ServerErrorException(ConsoleColors.RED + "Server error:" + getCompanyCoupons.getStatusCode().name() + ConsoleColors.RESET);
            }
            List<Coupon> companyCoupons = Arrays.asList(getCompanyCoupons.getBody());
            System.out.println(ConsoleColors.BLUE + "company coupons through restTemplate");
            companyCoupons.forEach(Coupon::toPrint);
            System.out.println(ConsoleColors.RESET);
            updateToken(getCompanyCoupons);
            return companyCoupons;
        } catch (ServerErrorException | ClientErrorException error) {
            System.out.println(error.getMessage());
        }
        return null;
    }

    private void addCompany(Company company) {
        try {
            ResponseEntity<Void> object = restTemplate.exchange(newCompany, HttpMethod.POST, getHttpEntity(this.token, company), void.class);
            if (object.getStatusCode().is5xxServerError()) {
                throw new ServerErrorException(ConsoleColors.RED + "Server error:" + object.getStatusCode().name() + ConsoleColors.RESET);
            }
            if (object.getStatusCode().is4xxClientError()) {
                throw new ClientErrorException(ConsoleColors.RED + "Client error:" + object.getStatusCode().name() + ConsoleColors.RESET);
            }
            System.out.println(ConsoleColors.BLUE + company + "added successfully through restTemplate" + ConsoleColors.RESET);
            updateToken(object);
        } catch (ServerErrorException | ClientErrorException error) {
            System.out.println(error.getMessage());
        }
    }

    private void addCustomer(Customer customer) {
        try {
            ResponseEntity<Void> object = restTemplate.exchange(newCustomer, HttpMethod.POST, getHttpEntity(this.token, customer), void.class);
            if (object.getStatusCode().is5xxServerError()) {
                throw new ServerErrorException(ConsoleColors.RED + "Server error:" + object.getStatusCode().name() + ConsoleColors.RESET);
            }
            if (object.getStatusCode().is4xxClientError()) {
                throw new ClientErrorException(ConsoleColors.RED + "Client error:" + object.getStatusCode().name() + ConsoleColors.RESET);
            }
            System.out.println(ConsoleColors.BLUE + customer + "added successfully through restTemplate" + ConsoleColors.RESET);
            updateToken(object);
        } catch (ServerErrorException | ClientErrorException error) {
            System.out.println(error.getMessage());
        }
    }

    private void updateCompany(Company company) {
        try {
            ResponseEntity<Void> object = restTemplate.exchange(updateCompany, HttpMethod.PUT, getHttpEntity(this.token, company), void.class);
            if (object.getStatusCode().is5xxServerError()) {
                throw new ServerErrorException(ConsoleColors.RED + "Server error:" + object.getStatusCode().name() + ConsoleColors.RESET);
            }
            if (object.getStatusCode().is4xxClientError()) {
                throw new ClientErrorException(ConsoleColors.RED + "Client error:" + object.getStatusCode().name() + ConsoleColors.RESET);
            }
            System.out.println(ConsoleColors.BLUE + company + "updated successfully through restTemplate" + ConsoleColors.RESET);
            updateToken(object);
        } catch (ServerErrorException | ClientErrorException error) {
            System.out.println(error.getMessage());
        }
    }

    private void updateCustomer(Customer customer) {
        try {
            ResponseEntity<Void> object = restTemplate.exchange(updateCustomer, HttpMethod.PUT, getHttpEntity(this.token, customer), void.class);
            if (object.getStatusCode().is5xxServerError()) {
                throw new ServerErrorException(ConsoleColors.RED + "Server error:" + object.getStatusCode().name() + ConsoleColors.RESET);
            }
            if (object.getStatusCode().is4xxClientError()) {
                throw new ClientErrorException(ConsoleColors.RED + "Client error:" + object.getStatusCode().name() + ConsoleColors.RESET);
            }
            System.out.println(ConsoleColors.BLUE + customer + "updated successfully through restTemplate" + ConsoleColors.RESET);
            updateToken(object);
        } catch (ServerErrorException | ClientErrorException error) {
            System.out.println(error.getMessage());
        }
    }

    private void deleteCompany(int id) {
        try {
            Map<String, String> param = new HashMap<>();
            param.put("id", String.valueOf(id));
            ResponseEntity<Void> object = restTemplate.exchange(deleteCompany, HttpMethod.DELETE, getHttpEntity(this.token, null), void.class, param);
            if (object.getStatusCode().is5xxServerError()) {
                throw new ServerErrorException(ConsoleColors.RED + "Server error:" + object.getStatusCode().name() + ConsoleColors.RESET);
            }
            if (object.getStatusCode().is4xxClientError()) {
                throw new ClientErrorException(ConsoleColors.RED + "Client error:" + object.getStatusCode().name() + ConsoleColors.RESET);
            }
            System.out.println(ConsoleColors.BLUE + "company  " + id + " deleted successfully through restTemplate" + ConsoleColors.RESET);
            updateToken(object);
        } catch (ServerErrorException | ClientErrorException error) {
            System.out.println(error.getMessage());
        }
    }

    private void deleteCustomer(int id) {
        try {
            Map<String, String> param = new HashMap<>();
            param.put("id", String.valueOf(id));
            ResponseEntity<Void> object = restTemplate.exchange(deleteCustomer, HttpMethod.DELETE, getHttpEntity(this.token, null), void.class, param);
            if (object.getStatusCode().is5xxServerError()) {
                throw new ServerErrorException(ConsoleColors.RED + "Server error:" + object.getStatusCode().name() + ConsoleColors.RESET);
            }
            if (object.getStatusCode().is4xxClientError()) {
                throw new ClientErrorException(ConsoleColors.RED + "Client error:" + object.getStatusCode().name() + ConsoleColors.RESET);
            }
            System.out.println(ConsoleColors.BLUE + "customer  " + id + " deleted successfully through restTemplate" + ConsoleColors.RESET);
            updateToken(object);
        } catch (ServerErrorException | ClientErrorException error) {
            System.out.println(error.getMessage());
        }
    }

    @Override
    public void run(String... args) {

        try {
            UserDetails userDetails = UserDetails.builder()
                    .userName("admin@admin.com")
                    .userPass("admin")
                    .clientType(ClientType.ADMIN.getType()).build();
            login(userDetails);
        } catch (Exception error) {
            System.out.println(error.getMessage());
        }

        try {
            allCompanies();
        } catch (Exception error) {
            System.out.println(error.getMessage());
        }

        try {
            allCustomers();
        } catch (Exception error) {
            System.out.println(error.getMessage());
        }
        try {
            getCompany(4);
        } catch (Exception error) {
            System.out.println(error.getMessage());
        }

        try {
            getCustomer(1);
        } catch (Exception error) {
            System.out.println(error.getMessage());
        }

        try {
            addCompany(Company.builder().email("taltul@gmailcom").name("tal").password("oliver&nala").build());
        } catch (Exception error) {
            System.out.println(error.getMessage());
        }

        try {
            addCustomer(Customer.builder().email("barakush@gmail.com").firstName("barak").lastName("ush").password("okCoupon").build());
        } catch (Exception error) {
            System.out.println(error.getMessage());
        }

        try {
            Company company = getCompany(6);
            company.setPassword("deleteMe");
            company.setEmail("byeTal@");
            updateCompany(company);
        } catch (Exception error) {
            System.out.println(error.getMessage());
        }

        try {
            Customer customer = getCustomer(6);
            customer.setPassword("1234");
            customer.setEmail("byeBarak@");
            updateCustomer(customer);
        } catch (Exception error) {
            System.out.println(error.getMessage());
        }

        try {
            deleteCompany(6);
        } catch (Exception error) {
            System.out.println(error.getMessage());
        }

        try {
            deleteCustomer(6);
        } catch (Exception error) {
            System.out.println(error.getMessage());
        }

        try {
            companyCoupons(1);
        } catch (Exception error) {
            System.out.println(error.getMessage());
        }

        try {
            customerCoupons(1);
        } catch (Exception error) {
            System.out.println(error.getMessage());
        }
    }
}



