package com.okcoupon.okcoupon.restTemplate;

import com.okcoupon.okcoupon.auth.ClientType;
import com.okcoupon.okcoupon.auth.UserDetails;
import com.okcoupon.okcoupon.beans.Category;
import com.okcoupon.okcoupon.beans.Coupon;
import com.okcoupon.okcoupon.beans.Customer;
import com.okcoupon.okcoupon.exceptions.ClientErrorException;
import com.okcoupon.okcoupon.exceptions.ServerErrorException;
import com.okcoupon.okcoupon.utils.ConsoleColors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Order(4)
public class CustomerRestTemplate implements CommandLineRunner {

    @Autowired
    private RestTemplate restTemplate;

    private String token;

    final static String login = "http://localhost:8080/customer/Login";
    final static String newPurchase = "http://localhost:8080/customer/newPurchase/{couponId}";
    final static String allCoupons = "http://localhost:8080/customer/allCouponsCustomer";
    final static String couponsByCategory = "http://localhost:8080/customer/CustomerCouponsByCategory{category}";
    final static String couponsByPrice = "http://localhost:8080/customer/CustomerCouponsByPrice{price}";
    final static String customerDetails = "http://localhost:8080/customer/customerDetails";
    final static String getAllCouponsInSystem = "http://localhost:8080/customer/allCouponsInSystem";

    private HttpEntity<?> getHttpEntity(String token, Object couponOrCustomer) {
        HttpEntity<?> httpEntity;
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization", this.token);
        if (couponOrCustomer instanceof Coupon || couponOrCustomer instanceof Customer) {
            httpEntity = new HttpEntity<>(couponOrCustomer, httpHeaders);
        } else httpEntity = new HttpEntity<>(httpHeaders);
        return httpEntity;
    }

    private void updateToken(ResponseEntity<?> responseEntity) {
        String responseTokenHeader = responseEntity.getHeaders().getFirst("Authorization");
        if (responseTokenHeader != null && responseTokenHeader.startsWith("Bearer")) {
            this.token = responseTokenHeader.substring(7);
        }
    }

    private void login(UserDetails userDetails) {
        try {
            ResponseEntity<?> object = restTemplate.exchange(login, HttpMethod.POST, new HttpEntity<>(userDetails), void.class);
            if (object.getStatusCode().is4xxClientError()) {
                throw new ClientErrorException(ConsoleColors.RED + "Client error:" + object.getStatusCode().name() + ConsoleColors.RESET);
            }
            if (object.getStatusCode().is5xxServerError()) {
                throw new ServerErrorException(ConsoleColors.RED + "Server error:" + object.getStatusCode().name() + ConsoleColors.RESET);
            }
            this.token = object.getHeaders().getFirst("Authorization");
            System.out.println(ConsoleColors.PURPLE_BACKGROUND + userDetails.getUserName() + " logged through restTemplate " + LocalDate.now() + "\nThe token: " + token + ConsoleColors.RESET);
        } catch (ServerErrorException | ClientErrorException error) {
            System.out.println(error.getMessage());
        }
    }

    private void newPurchase(int couponId) {
        try {
            Map<String, String> param = new HashMap<>();
            param.put("couponId", String.valueOf(couponId));
            ResponseEntity<Void> object = restTemplate.exchange(newPurchase, HttpMethod.POST, getHttpEntity(this.token, null), void.class, param);
            if (object.getStatusCode().is5xxServerError()) {
                throw new ServerErrorException(ConsoleColors.RED + "Server error:" + object.getStatusCode().name() + ConsoleColors.RESET);
            }
            if (object.getStatusCode().is4xxClientError()) {
                throw new ClientErrorException(ConsoleColors.RED + "Client error:" + object.getStatusCode().name() + ConsoleColors.RESET);
            }
            System.out.println(ConsoleColors.PURPLE_BRIGHT + "coupon " + couponId + " successfully purchased for " + getCustomerDetails().getFirstName() + " through restTemplate" + ConsoleColors.RESET);
            updateToken(object);
        } catch (ServerErrorException | ClientErrorException error) {
            System.out.println(error.getMessage());
        }
    }

    private List<Coupon> allCoupons() {
        try {
            ResponseEntity<Coupon[]> getAllCoupons = restTemplate.exchange(allCoupons, HttpMethod.GET, getHttpEntity(this.token, null), Coupon[].class);
            if (getAllCoupons.getStatusCode().is5xxServerError()) {
                throw new ServerErrorException(ConsoleColors.RED + "Server error:" + getAllCoupons.getStatusCode().name() + ConsoleColors.RESET);
            }
            if (getAllCoupons.getStatusCode().is4xxClientError()) {
                throw new ClientErrorException(ConsoleColors.RED + "Client error:" + getAllCoupons.getStatusCode().name() + ConsoleColors.RESET);
            }
            List<Coupon> coupons = Arrays.asList(getAllCoupons.getBody());
            System.out.println(ConsoleColors.PURPLE_BRIGHT + "all coupons of customer through restTemplate");
            coupons.forEach(Coupon::toPrint);
            System.out.println(ConsoleColors.RESET);
            updateToken(getAllCoupons);
            return coupons;
        } catch (ServerErrorException | ClientErrorException error) {
            System.out.println(error.getMessage());
        }
        return null;
    }

    private List<Coupon> couponsByCategory(Category category) {
        try {
            Map<String, String> param = new HashMap<>();
            param.put("category", category.getCategoryType());
            ResponseEntity<Coupon[]> getCouponsByCategory = restTemplate.exchange(couponsByCategory, HttpMethod.GET, getHttpEntity(this.token, null), Coupon[].class, param);
            if (getCouponsByCategory.getStatusCode().is5xxServerError()) {
                throw new ServerErrorException(ConsoleColors.RED + "Server error:" + getCouponsByCategory.getStatusCode().name());
            }
            if (getCouponsByCategory.getStatusCode().is4xxClientError()) {
                throw new ClientErrorException("Client error:" + getCouponsByCategory.getStatusCode().name());
            }
            List<Coupon> coupons = Arrays.asList(getCouponsByCategory.getBody());
            System.out.println(ConsoleColors.PURPLE_BRIGHT + "all coupon  Of customer  by category through restTemplate");
            coupons.forEach(Coupon::toPrint);
            System.out.print(ConsoleColors.RESET);
            updateToken(getCouponsByCategory);
            return coupons;
        } catch (ServerErrorException | ClientErrorException error) {
            System.out.println(error.getMessage());
        }
        return null;
    }

    private List<Coupon> couponsByPrice(int price) {
        try {
            Map<String, String> param = new HashMap<>();
            param.put("price", String.valueOf(price));
            ResponseEntity<Coupon[]> getCouponsByPrice = restTemplate.exchange(couponsByPrice, HttpMethod.GET, getHttpEntity(this.token, null), Coupon[].class, param);
            if (getCouponsByPrice.getStatusCode().is5xxServerError()) {
                throw new ServerErrorException("Server error:" + getCouponsByPrice.getStatusCode().name());
            }
            if (getCouponsByPrice.getStatusCode().is4xxClientError()) {
                throw new ClientErrorException("Client error:" + getCouponsByPrice.getStatusCode().name());
            }
            List<Coupon> coupons = Arrays.asList(getCouponsByPrice.getBody());
            System.out.println(ConsoleColors.PURPLE_BRIGHT + "all coupon  Of customer  by price through restTemplate");
            coupons.forEach(Coupon::toPrint);
            System.out.print(ConsoleColors.RESET);
            updateToken(getCouponsByPrice);
            return coupons;
        } catch (ServerErrorException | ClientErrorException error) {
            System.out.println(error.getMessage());
        }
        return null;
    }

    private Customer getCustomerDetails() throws ServerErrorException, ClientErrorException {
        try {
            ResponseEntity<Customer> getDetails = restTemplate.exchange(customerDetails, HttpMethod.GET, getHttpEntity(this.token, null), Customer.class);
            if (getDetails.getStatusCode().is5xxServerError()) {
                throw new ServerErrorException("Server error:" + getDetails.getStatusCode().name() + ConsoleColors.RESET);
            }
            if (getDetails.getStatusCode().is4xxClientError()) {
                throw new ClientErrorException(ConsoleColors.RED + "Client error:" + getDetails.getStatusCode().name() + ConsoleColors.RESET);
            }
            Customer customer = getDetails.getBody();
            System.out.println(ConsoleColors.PURPLE_BRIGHT + "customer details through restTemplate\n" + customer + ConsoleColors.RESET);
            updateToken(getDetails);
            return customer;
        } catch (ServerErrorException | ClientErrorException error) {
            System.out.println(error.getMessage());
        }
        return null;
    }

    private List<Coupon> allCouponsInSystem() {
        try {
            ResponseEntity<Coupon[]> getCoupons = restTemplate.getForEntity(getAllCouponsInSystem, Coupon[].class);
            if (getCoupons.getStatusCode().is5xxServerError()) {
                throw new ServerErrorException("Server error:" + getCoupons.getStatusCode().name() + ConsoleColors.RESET);
            }
            if (getCoupons.getStatusCode().is4xxClientError()) {
                throw new ClientErrorException(ConsoleColors.RED + "Client error:" + getCoupons.getStatusCode().name() + ConsoleColors.RESET);
            }
            List<Coupon> coupons = Arrays.asList(getCoupons.getBody());
            System.out.println(ConsoleColors.PURPLE_BRIGHT + "all coupons in system through restTemplate");
            coupons.forEach(Coupon::toPrint);
            System.out.print(ConsoleColors.RESET);
            return coupons;
        } catch (ServerErrorException | ClientErrorException error) {
            System.out.println(error.getMessage());
        }
        return null;
    }

    @Override
    public void run(String... args) {

        try {
            UserDetails userDetails = UserDetails.builder()
                    .userName("tomer@gmail.com")
                    .userPass("1234")
                    .clientType(ClientType.CUSTOMER.getType()).build();
            login(userDetails);
        } catch (Exception error) {
            System.out.println(error.getMessage());
        }

        try {
            allCoupons();
        } catch (Exception error) {
            System.out.println(error.getMessage());
        }

        try {
            couponsByCategory(Category.ELECTRICITY);
        } catch (Exception error) {
            System.out.println(error.getMessage());
        }

        try {
            couponsByPrice(400);
        } catch (Exception error) {
            System.out.println(error.getMessage());
        }

        try {
            getCustomerDetails();
        } catch (Exception error) {
            System.out.println(error.getMessage());
        }

        try {
            newPurchase(1);
        } catch (Exception error) {
            System.out.println(error.getMessage());
        }

        try {
            allCouponsInSystem();
        } catch (Exception error) {
            System.out.println(error.getMessage());
        }
    }
}

