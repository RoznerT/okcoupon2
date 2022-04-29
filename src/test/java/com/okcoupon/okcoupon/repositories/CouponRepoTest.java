package com.okcoupon.okcoupon.repositories;

import com.okcoupon.okcoupon.beans.Category;
import com.okcoupon.okcoupon.beans.Company;
import com.okcoupon.okcoupon.beans.Coupon;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@ContextConfiguration
@RunWith(SpringRunner.class)
@SpringBootTest()
class CouponRepoTest {

    @Autowired
    private CouponRepo couponRepoUnderTest;

    @Autowired
    private PurchaseRepo purchaseRepo;

    @Autowired
    ApplicationContext ctx;

    private final int INDEX_NUMBER = 0;
    private final int COMPANY_ID = 2;
    private int COUNT_COUPONS = 27;  //need to change if add more coupons to dataBase in MockDataBuilder.class
    private final int WRONG_ID = 42;

    public Coupon newCoupon = Coupon.builder()
            .id(0)
            .category(Category.ENTERTAINMENT)
            .title("wow! new coupon")
            .description("fresh fresh fresh")
            .startDate(Date.valueOf(LocalDate.now().minusDays(2)))
            .endDate(Date.valueOf(LocalDate.now().plusDays(2)))
            .price(222)
            .image("^-^")
            .amount(2)
            .company(Company.builder()
                    .id(2)
                    .name("gucci")
                    .email("gucci@gmail.com")
                    .password("12345").build())
            .build();

    public Coupon couponToGet = Coupon.builder()
            .id(6)
            .category(Category.ELECTRICITY)
            .title("ps5 from china")
            .description("controller coupon")
            .startDate(Date.valueOf(LocalDate.now().minusDays(1)))
            .endDate(Date.valueOf(LocalDate.now().plusDays(1)))
            .price(0.99)
            .image(":)))")
            .amount(4)
            .company(Company.builder()
                    .id(2)
                    .name("gucci")
                    .email("gucci@gmail.com")
                    .password("12345").build())
            .build();

    @Test
    public void contextLoads() {
        Assertions.assertThat(ctx).isNotNull();
    }

    @BeforeEach
    void setUp() {
        this.COUNT_COUPONS = (int) couponRepoUnderTest.count();
        Assertions.assertThat(couponRepoUnderTest.findById(WRONG_ID)).isEmpty();
        Assertions.assertThat(couponRepoUnderTest.getById(couponToGet.getId())).isNotNull();
    }

    @Test
    void findByCompanyIdAndCategoryTest() {
        int expected = couponRepoUnderTest.findByCompanyIdAndCategory(couponToGet.getCompany().getId(), couponToGet.getCategory()).get(INDEX_NUMBER).getId();
        boolean expected2 = couponRepoUnderTest.findByCompanyIdAndCategory(WRONG_ID, couponToGet.getCategory()).isEmpty();
        Assertions.assertThat(expected).isEqualTo(couponToGet.getId());
        Assertions.assertThat(expected2).isTrue();
    }

    @Test
    void findByCompanyIdAndPriceLessThanTest() {
        int PRICE_LESS_THEN = 1;
        int expected = couponRepoUnderTest.findByCompanyIdAndPriceLessThan(couponToGet.getCompany().getId(), PRICE_LESS_THEN).get(INDEX_NUMBER).getId();
        boolean expected2 = couponRepoUnderTest.findByCompanyIdAndPriceLessThan(WRONG_ID, couponToGet.getPrice()).isEmpty();
        Assertions.assertThat(expected).isEqualTo(couponToGet.getId());
        Assertions.assertThat(expected2).isTrue();
    }

    @Test
    void findByCompanyIdTest() {
        int expected = couponRepoUnderTest.findByCompanyId(COMPANY_ID).size();
        int COUPONS_OF_COMPANY = 5; //check against dataBase, change the value if you need.
        Assertions.assertThat(expected).isEqualTo(COUPONS_OF_COMPANY);
    }

    @Test
    void existsByTitleAndCompanyIdTest() {
        boolean expected = couponRepoUnderTest.existsByTitleAndCompanyId(couponToGet.getTitle(), COMPANY_ID);
        boolean expected2 = couponRepoUnderTest.existsByTitleAndCompanyId(newCoupon.getTitle(), WRONG_ID);
        Assertions.assertThat(expected).isTrue();
        Assertions.assertThat(expected2).isFalse();

    }

    @Test
    void getByTitleTest() {
        Coupon expected = couponRepoUnderTest.getByTitle(couponToGet.getTitle()).get(INDEX_NUMBER);
        Assertions.assertThat(expected).isInstanceOf(Coupon.class);
        Assertions.assertThat(expected.getId()).isEqualTo(couponToGet.getId());
        Assertions.assertThat(expected.getCompany().getId()).isEqualTo(couponToGet.getCompany().getId()); //no need more test because coupon id is unique (primary key)
    }

    @Test
    void deleteByIdTest() {
        couponRepoUnderTest.saveAndFlush(newCoupon);
        Coupon expected = couponRepoUnderTest.getByTitle(newCoupon.getTitle()).get(INDEX_NUMBER);
        Assertions.assertThat(couponRepoUnderTest.existsById(expected.getId())).isTrue();

        couponRepoUnderTest.deleteById(expected.getId());
        Assertions.assertThat(couponRepoUnderTest.count()).isEqualTo(COUNT_COUPONS);
        Assertions.assertThat(couponRepoUnderTest.existsById(expected.getId())).isFalse();
    }

    @Test
    void eraseCouponTest() { //erase coupon that expired
        Coupon coupon = couponRepoUnderTest.getByTitle("mcdonald's").get(INDEX_NUMBER);
        coupon.setEndDate(Date.valueOf(LocalDate.now().minusDays(1)));
        couponRepoUnderTest.save(coupon);
        couponRepoUnderTest.eraseCoupon();
        Assertions.assertThat(couponRepoUnderTest.count()).isEqualTo(COUNT_COUPONS-1);

        coupon.setEndDate(Date.valueOf(LocalDate.now().plusMonths(1)));
        couponRepoUnderTest.save(coupon);
    }

    @Test
    void getByIdTest() {
        Coupon expected = couponRepoUnderTest.getById(couponToGet.getId());
        Assertions.assertThat(expected).isInstanceOf(Coupon.class);
        Assertions.assertThat(expected.getTitle()).isEqualTo(couponToGet.getTitle());
        Assertions.assertThat(expected.getCompany().getId()).isEqualTo(couponToGet.getCompany().getId()); //no need more test because coupon title is unique for company coupon
    }

    @Test
    void findAllTest() {
        List<Coupon> expected = couponRepoUnderTest.findAll();
        Assertions.assertThat(expected.size()).isEqualTo(COUNT_COUPONS);
    }

    @Test
    void saveTest() {
        couponRepoUnderTest.saveAndFlush(newCoupon);
        Coupon expected = couponRepoUnderTest.getByTitle(newCoupon.getTitle()).get(INDEX_NUMBER);
        Assertions.assertThat(expected).isInstanceOf(Coupon.class);
        Assertions.assertThat(expected.getId()).isNotZero();
        couponRepoUnderTest.deleteById(expected.getId());
    }

    @Test
    void updateTest() {
        Coupon coupon = couponRepoUnderTest.getById(couponToGet.getId());
        coupon.setDescription("descriptionUpdateTest");
        couponRepoUnderTest.save(coupon);

        Coupon expected = couponRepoUnderTest.getById(coupon.getId());
        Assertions.assertThat(expected).isInstanceOf(Coupon.class);
        Assertions.assertThat(expected.getDescription()).isEqualTo(coupon.getDescription());
    }
}