package com.okcoupon.okcoupon.repositories;

import com.okcoupon.okcoupon.beans.*;
import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@ContextConfiguration
@RunWith(SpringRunner.class)
@SpringBootTest()
public class PurchaseRepoTest {

    @Autowired
    private PurchaseRepo purchaseRepoUnderTest;

    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private CouponRepo couponRepo;

    @Autowired
    ApplicationContext ctx;

    private final int INDEX_NUMBER = 0;
    private int COUNT_PURCHASES = 0;
    private final int WRONG_ID = 42;

    Purchase purchaseToGet = Purchase.builder()
            .purchaseID(1)
            .coupon(Coupon.builder()
                    .category(Category.ELECTRICITY)
                    .title("airpods")
                    .description("airpods pro")
                    .startDate(Date.valueOf(LocalDate.now().minusDays(2)))
                    .endDate(Date.valueOf(LocalDate.now().plusDays(2)))
                    .price(487)
                    .image(":)))")
                    .amount(22)
                    .company(Company.builder()
                            .id(1)
                            .name("hyundai")
                            .email("hyundai@gmail.com")
                            .password("1234").build())
                    .build())
            .customer(Customer.builder()
                    .id(1)
                    .firstName("itzik")
                    .lastName("seaman-tof")
                    .email("itzik@gmail.com")
                    .password("1234")
                    .build())
            .build();

    public Customer zeevNoCoupons = Customer.builder()
            .id(6)
            .firstName("zeevNoCoupons")
            .lastName("mindali")
            .email("zeev@gmail.com")
            .password("zeev")
            .build();

    @Test
    public void contextLoads() {
        Assertions.assertThat(ctx).isNotNull();
    }

    @Before
    @Test
    public void setUp() {
        COUNT_PURCHASES = (int) purchaseRepoUnderTest.count();
        Assertions.assertThat(purchaseRepoUnderTest.findById(WRONG_ID)).isEmpty();
        Assertions.assertThat(purchaseRepoUnderTest.getById(purchaseToGet.getPurchaseID())).isNotNull();
    }

    @Test
    public void findByCustomerId() {
        purchaseRepoUnderTest.saveAndFlush(new Purchase(zeevNoCoupons ,couponRepo.getById(1)));
        Purchase expected = (Purchase) Arrays.stream(purchaseRepoUnderTest.getByCustomerId(zeevNoCoupons.getId()).toArray()).findFirst().get();
        boolean expected2 = purchaseRepoUnderTest.getByCustomerId(WRONG_ID).isEmpty();
        Assertions.assertThat(expected.getCoupon().getId()).isEqualTo(1);
        Assertions.assertThat(expected2).isTrue();

        purchaseRepoUnderTest.deleteByPurchaseId(expected.getPurchaseID());
    }

    @Test
    public void getByCustomerId() {
        purchaseRepoUnderTest.saveAndFlush(new Purchase(zeevNoCoupons ,couponRepo.getById(1)));
        Purchase expected = (Purchase) Arrays.stream(purchaseRepoUnderTest.getByCustomerId(zeevNoCoupons.getId()).toArray()).findFirst().get();
        boolean expected2 = purchaseRepoUnderTest.getByCustomerId(WRONG_ID).isEmpty();
        Assertions.assertThat(expected.getCoupon().getId()).isEqualTo(1);
        Assertions.assertThat(expected2).isTrue();

        purchaseRepoUnderTest.deleteByPurchaseId(expected.getPurchaseID());
    }

    @Test
    public void existsByCouponId(){
        Assertions.assertThat(purchaseRepoUnderTest.existsByCouponId(1)).isTrue();
        Assertions.assertThat(purchaseRepoUnderTest.existsByCouponId(WRONG_ID)).isFalse();
    }

    @Test
    public void deleteByCouponId() {
        Assertions.assertThat(purchaseRepoUnderTest.existsById(purchaseToGet.getPurchaseID())).isTrue();

        purchaseRepoUnderTest.deleteByCouponId(purchaseToGet.getCoupon().getId());
        Assertions.assertThat(purchaseRepoUnderTest.existsByCouponId(purchaseToGet.getCoupon().getId())).isFalse();
    }

    @Test
    public void deleteByCustomerId() {
        purchaseRepoUnderTest.saveAndFlush(new Purchase(zeevNoCoupons ,couponRepo.getById(1)));
        Purchase expected = purchaseRepoUnderTest.findByCustomerId(zeevNoCoupons.getId()).get(INDEX_NUMBER);
        Assertions.assertThat(purchaseRepoUnderTest.existsById(expected.getPurchaseID())).isTrue();

        purchaseRepoUnderTest.deleteByCustomerId(expected.getCustomer().getId());
        Assertions.assertThat(purchaseRepoUnderTest.existsById(expected.getPurchaseID())).isFalse();
    }

    @Test @After
    public void deleteByIdTest() {
        purchaseRepoUnderTest.saveAndFlush(new Purchase(zeevNoCoupons,couponRepo.getById(1)));
        Purchase expected = purchaseRepoUnderTest.findByCustomerId(zeevNoCoupons.getId()).get(INDEX_NUMBER);
        Assertions.assertThat(purchaseRepoUnderTest.existsById(expected.getPurchaseID())).isTrue();

        purchaseRepoUnderTest.deleteByPurchaseId(expected.getPurchaseID());
        Assertions.assertThat(purchaseRepoUnderTest.existsById(expected.getPurchaseID())).isFalse();
        Assertions.assertThat(purchaseRepoUnderTest.findById(expected.getPurchaseID()).isEmpty()).isTrue();

        purchaseRepoUnderTest.saveAndFlush(new Purchase(customerRepo.getById(1),couponRepo.getById(1)));
    }

    @Test
    public void getByIdTest() {
        Purchase expected = purchaseRepoUnderTest.getById(purchaseToGet.getPurchaseID());
        Assertions.assertThat(expected).isInstanceOf(Purchase.class);
        Assertions.assertThat(expected.getPurchaseID()).isEqualTo(purchaseToGet.getPurchaseID());
        Assertions.assertThat(expected.getCoupon().getTitle()).isEqualTo(purchaseToGet.getCoupon().getTitle());
        Assertions.assertThat(expected.getCustomer().getId()).isEqualTo(purchaseToGet.getCustomer().getId());
    }

    @Test
    public void findAllTest() {
        List<Purchase> expected = purchaseRepoUnderTest.findAll();
        Assertions.assertThat(expected.size()).isEqualTo(COUNT_PURCHASES);
    }

    @Test
    public void saveTest() {
        purchaseRepoUnderTest.saveAndFlush(new Purchase(customerRepo.getById(zeevNoCoupons.getId()),couponRepo.getById(1)));
        Purchase expected = purchaseRepoUnderTest.findByCustomerId(zeevNoCoupons.getId()).get(INDEX_NUMBER);
        Assertions.assertThat(expected).isInstanceOf(Purchase.class);
        Assertions.assertThat(expected.getPurchaseID()).isNotZero();

        purchaseRepoUnderTest.deleteByPurchaseId(expected.getPurchaseID());
    }

}