package com.okcoupon.okcoupon.restTemplate;

import com.okcoupon.okcoupon.beans.Coupon;
import com.okcoupon.okcoupon.exceptions.ClientErrorException;
import com.okcoupon.okcoupon.exceptions.ServerErrorException;
import com.okcoupon.okcoupon.utils.ConsoleColors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Component
@Order(5)
public class GuestRestTemplate implements CommandLineRunner {

    @Autowired
    private RestTemplate restTemplate;

    final static String getAllCouponsInSystem = "http://localhost:8080/guest/allCouponsInSystem";

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
            System.out.println(ConsoleColors.GREEN_BRIGHT + "---------------------HELLO GUEST----------------\n here all coupons in system through restTemplate");
            coupons.forEach(Coupon::toPrint);
            System.out.print(ConsoleColors.RESET);
            return coupons;
        } catch (ServerErrorException | ClientErrorException error) {
            System.out.println(error.getMessage());
        }
        return null;
    }

    @Override
    public void run(String... args) throws Exception {
        try {
            allCouponsInSystem();
        } catch (Exception error) {
            System.out.println(error.getMessage());
        }
    }
}
