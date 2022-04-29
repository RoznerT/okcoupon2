package com.okcoupon.okcoupon.restTemplate;

import com.okcoupon.okcoupon.auth.ClientType;
import com.okcoupon.okcoupon.auth.UserDetails;
import com.okcoupon.okcoupon.beans.Category;
import com.okcoupon.okcoupon.beans.Company;
import com.okcoupon.okcoupon.beans.Coupon;
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
import java.util.*;

@Component
@Order(3)


public class CompanyRestTemplate implements CommandLineRunner {

    @Autowired
    private RestTemplate restTemplate;

    private String token;

    final static String loginUrl = "http://localhost:8080/company/Login";
    final static String allCouponsUrl = "http://localhost:8080/company/allCouponsCompany";
    final static String couponsByCategoryUrl = "http://localhost:8080/company/CompanyCouponsByCategory{category}";
    final static String couponByPriceUrl = "http://localhost:8080/company/CompanyCouponsByPrice{price}";
    final static String companyDetailsUrl = "http://localhost:8080/company/companyDetails";
    final static String deleteCouponUrl = "http://localhost:8080/company/deleteCoupon/{id}";
    final static String newCouponUrl = "http://localhost:8080/company/newCoupon";
    final static String updateCouponUrl = "http://localhost:8080/company/updateCoupon";

    private HttpEntity<?> getHttpEntity(String token, Object object) {
        HttpEntity<?> httpEntity;
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization", this.token);
        if (object instanceof Coupon) {
            httpEntity = new HttpEntity<>(object, httpHeaders);
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
            ResponseEntity<Void> object = restTemplate.exchange(loginUrl, HttpMethod.POST, new HttpEntity<>(userDetails), void.class);

            if (object.getStatusCode().is4xxClientError()) {
                throw new ClientErrorException("Client error:" + object.getStatusCode().name());
            }
            if (object.getStatusCode().is5xxServerError()) {
                throw new ServerErrorException("Server error:" + object.getStatusCode().name());
            }
            this.token= object.getHeaders().getFirst("Authorization");
            System.out.println(userDetails.getUserName() + " logged through restTemplate " +
                    LocalDate.now() + ConsoleColors.PURPLE_BACKGROUND + "\nThe token: " + token + ConsoleColors.RESET);
        } catch (ServerErrorException | ClientErrorException error) {
            System.out.println(error.getMessage());
        }
    }

    private List<Coupon> allCoupons() {
        try {
            ResponseEntity<Coupon[]> getAllCoupons = restTemplate.exchange(allCouponsUrl, HttpMethod.GET, getHttpEntity(this.token, null), Coupon[].class);
            List<Coupon> coupons = Arrays.asList(getAllCoupons.getBody());
            if (getAllCoupons.getStatusCode().is4xxClientError()) {
                throw new ClientErrorException("Client error:" + getAllCoupons.getStatusCode().name());
            }
            if (getAllCoupons.getStatusCode().is5xxServerError()) {
                throw new ServerErrorException("Server error:" + getAllCoupons.getStatusCode().name());
            }
            System.out.println(ConsoleColors.CYAN + "all coupon  Of company   through restTemplate");
            coupons.forEach(Coupon::toPrint);
            System.out.print(ConsoleColors.RESET);
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
            ResponseEntity<Coupon[]> getCouponsByCategory = restTemplate.exchange(couponsByCategoryUrl, HttpMethod.GET, getHttpEntity(this.token, null), Coupon[].class, param);
            List<Coupon> coupons = Arrays.asList(getCouponsByCategory.getBody());
            if (getCouponsByCategory.getStatusCode().is4xxClientError()) {
                throw new ClientErrorException("Client error:" + getCouponsByCategory.getStatusCode().name());
            }
            if (getCouponsByCategory.getStatusCode().is5xxServerError()) {
                throw new ServerErrorException("Server error:" + getCouponsByCategory.getStatusCode().name());
            }

            System.out.println(ConsoleColors.CYAN + "all coupon  Of company  by category through restTemplate");
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
            ResponseEntity<Coupon[]> getCouponsByPrice = restTemplate.exchange(couponByPriceUrl, HttpMethod.GET, getHttpEntity(this.token, null), Coupon[].class, param);
            List<Coupon> coupons = Arrays.asList(getCouponsByPrice.getBody());
            if (getCouponsByPrice.getStatusCode().is4xxClientError()) {
                throw new ClientErrorException("Client error:" + getCouponsByPrice.getStatusCode().name());
            }
            if (getCouponsByPrice.getStatusCode().is5xxServerError()) {
                throw new ServerErrorException("Server error:" + getCouponsByPrice.getStatusCode().name());
            }
            System.out.println(ConsoleColors.CYAN + "all coupon  Of company by price through restTemplate");
            coupons.forEach(Coupon::toPrint);
            System.out.print(ConsoleColors.RESET);
            updateToken(getCouponsByPrice);
            return coupons;
        } catch (ServerErrorException | ClientErrorException error) {
            System.out.println(error.getMessage());
        }
        return null;
    }

    private Company getCompanyDetails() throws ClientErrorException, ServerErrorException {
        try {
            ResponseEntity<Company> getCompanyDetails = restTemplate.exchange(companyDetailsUrl, HttpMethod.GET, getHttpEntity(this.token, null), Company.class);
            System.out.println(ConsoleColors.CYAN + "company details through restTemplate\n" + getCompanyDetails + ConsoleColors.RESET);
            if (getCompanyDetails.getStatusCode().is4xxClientError()) {
                throw new ClientErrorException("Client error:" + getCompanyDetails.getStatusCode().name());
            }
            if (getCompanyDetails.getStatusCode().is5xxServerError()) {
                throw new ServerErrorException("Server error:" + getCompanyDetails.getStatusCode().name());
            }
            updateToken(getCompanyDetails);
            return getCompanyDetails.getBody();
        } catch (ServerErrorException | ClientErrorException error) {
            System.out.println(error.getMessage());
        }
        return null;
    }

    private void addCoupon(Coupon coupon) {
        try {
            ResponseEntity<Void> object = restTemplate.exchange(newCouponUrl, HttpMethod.POST, getHttpEntity(this.token, coupon), void.class);
            if (object.getStatusCode().is5xxServerError()) {
                throw new ServerErrorException("Server error:" + object.getStatusCode().name());
            }
            if (object.getStatusCode().is4xxClientError()) {
                throw new ClientErrorException("Client error:" + object.getStatusCode().name());
            }
            System.out.println(ConsoleColors.CYAN + "coupon " + coupon.getTitle() + " added successfully" + ConsoleColors.RESET);
            updateToken(object);
        } catch (ServerErrorException | ClientErrorException error) {
            System.out.println(error.getMessage());
        }
    }

    private void updateCoupon(Coupon coupon) {
        try {
            ResponseEntity<Void> object = restTemplate.exchange(updateCouponUrl, HttpMethod.PUT, getHttpEntity(this.token, coupon), void.class);
            if (object.getStatusCode().is5xxServerError()) {
                throw new ServerErrorException("Server error:" + object.getStatusCode().name());
            }
            if (object.getStatusCode().is4xxClientError()) {
                throw new ClientErrorException("Client error:" + object.getStatusCode().name());
            }
            System.out.println(ConsoleColors.CYAN + "coupon" + coupon.getId() + " updated successfully" + ConsoleColors.RESET);
            updateToken(object);

        } catch (ServerErrorException | ClientErrorException error) {
            System.out.println(error.getMessage());
        }
    }

    private void deleteCoupon(int id) {
        try {
            Map<String, String> param = new HashMap<>();
            param.put("id", String.valueOf(id));
            ResponseEntity<Void> object = restTemplate.exchange(deleteCouponUrl, HttpMethod.DELETE, getHttpEntity(this.token, null), void.class, param);
            if (object.getStatusCode().is5xxServerError()) {
                throw new ServerErrorException("Server error:" + object.getStatusCode().name());
            }
            if (object.getStatusCode().is4xxClientError()) {
                throw new ClientErrorException("Client error:" + object.getStatusCode().name());
            }
            System.out.println(ConsoleColors.CYAN + "coupon" + id + " deleted successfully" + ConsoleColors.RESET);
            updateToken(object);
        } catch (ServerErrorException | ClientErrorException error) {
            System.out.println(error.getMessage());
        }
    }


    @Override
    public void run(String... args) {

        try {UserDetails userDetails = UserDetails.builder()
                .userName("hyundai@gmail.com")
                .userPass("1234")
                .clientType(ClientType.COMPANY.getType()).build();
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
            couponsByCategory(Category.FASHION);
        } catch (Exception error) {
            System.out.println(error.getMessage());
        }

        try {
            couponsByPrice(1000);
        } catch (Exception error) {
            System.out.println(error.getMessage());
        }

        try {
            getCompanyDetails();
        } catch (Exception error) {
            System.out.println(error.getMessage());
        }

        try {
            Coupon coupon = allCoupons().stream().findFirst().get();
            coupon.setTitle("new coupon");
            coupon.setId(0);
            addCoupon(coupon);

        } catch (Exception error) {
            System.out.println(error.getMessage());
        }

        try {
            Coupon coupon1 = allCoupons().stream().findFirst().get();
            coupon1.setTitle("work porfavor!!!!!");
            coupon1.setDescription("change change");
            updateCoupon(coupon1);

        } catch (Exception error) {
            System.out.println(error.getMessage());
        }

        try {
            deleteCoupon(2);
        } catch (Exception error) {
            System.out.println(error.getMessage());
        }
    }
}


