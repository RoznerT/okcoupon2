package com.okcoupon.okcoupon.service;

import com.okcoupon.okcoupon.beans.Company;
import com.okcoupon.okcoupon.beans.Coupon;
import com.okcoupon.okcoupon.exceptions.NoCompaniesException;
import org.assertj.core.api.Assertions;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GuestServiceTest {

    @Autowired
    private GuestService guestServiceUnderTest;

    private final int INDEX_NUMBER = 0;

    @Test
    @DisplayName("GET ALL COUPONS SUCCESS - should be a List of coupons")
    void getAllCouponsInSystemTest() {
        Assertions.assertThat(guestServiceUnderTest.getAllCouponsInSystem()).isInstanceOf(List.class);
        Assertions.assertThat(guestServiceUnderTest.getAllCouponsInSystem().get(INDEX_NUMBER)).isInstanceOf(Coupon.class);
    }
}