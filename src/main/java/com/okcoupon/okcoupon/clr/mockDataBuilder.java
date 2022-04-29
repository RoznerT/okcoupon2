package com.okcoupon.okcoupon.clr;

import com.okcoupon.okcoupon.beans.Category;
import com.okcoupon.okcoupon.beans.Company;
import com.okcoupon.okcoupon.beans.Coupon;
import com.okcoupon.okcoupon.beans.Customer;
import com.okcoupon.okcoupon.service.AdminService;
import com.okcoupon.okcoupon.service.CompanyService;
import com.okcoupon.okcoupon.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDate;
import java.util.HashSet;


@Component
@Order(1)
public class mockDataBuilder implements CommandLineRunner {
    @Autowired
    AdminService adminService;
    @Autowired
    CompanyService companyService;
    @Autowired
    CustomerService customerService;

    private int setEndDate() {
        return (int) ((Math.random() * 50) + 15);
    }

    private int setStartDate() {
        return (int) ((Math.random() * 5));
    }


    private int amountSetter() {
        return (int) (Math.random() * 20 + 5);
    }


    /// companies:///
    public Company hyundai = Company.builder()
            .name("hyundai")
            .email("hyundai@gmail.com")
            .password("1234")
            .build();
    public Company gucci = Company.builder()
            .name("gucci")
            .email("gucci@gmail.com")
            .password("12345")
            .build();
    public Company ibm = Company.builder()
            .name("ibm")
            .email("ibm@gmail.com")
            .password("123456")
            .build();
    public Company facebook = Company.builder()
            .name("facebook")
            .email("facebook@gmail.com")
            .password("1234567")
            .build();
    public Company google = Company.builder()
            .name("google")
            .email("google@walla.com")
            .password("12345678")
            .build();

    ///coupons///


    public Coupon tvHouse = Coupon.builder()
            .category(Category.ELECTRICITY)
            .title("airpods")
            .description("airpods pro")
            .startDate(Date.valueOf(LocalDate.now().minusDays(setStartDate())))
            .endDate(Date.valueOf(LocalDate.now().plusDays(setEndDate())))
            .price(487)
            .image(":)))")
            .amount(amountSetter())
            .company(hyundai)
            .build();


    public Coupon zarra = Coupon.builder()
            .category(Category.FASHION)
            .title("one+one")
            .description("buy one get the second hinam")
            .startDate(Date.valueOf(LocalDate.now().minusDays(setStartDate())))
            .endDate(Date.valueOf(LocalDate.now().plusDays(setEndDate())))
            .price(100.0)
            .image(":)))")
            .amount(amountSetter())
            .company(hyundai)
            .build();

    public Coupon sony = Coupon.builder()
            .category(Category.ELECTRICITY)
            .title("ps5 from china")
            .description("controller coupon")
            .startDate(Date.valueOf(LocalDate.now().minusDays(1)))
            .endDate(Date.valueOf(LocalDate.now().plusDays(1)))
            .price(0.99)
            .image(":)))")
            .amount(4)
            .company(gucci)
            .build();

    public Coupon totto = Coupon.builder()
            .category(Category.RESTAURANT)
            .title(" desert coupon")
            .description("yam yam")
            .startDate(Date.valueOf(LocalDate.now().minusDays(setStartDate())))
            .endDate(Date.valueOf(LocalDate.now().plusDays(setEndDate())))
            .price(80.0)
            .image(":)))")
            .amount(amountSetter())
            .company(hyundai)
            .build();

    public Coupon skiDeal = Coupon.builder()
            .category(Category.VACATION)
            .title("ski trip")
            .description("eize keff")
            .startDate(Date.valueOf(LocalDate.now().minusDays(setStartDate())))
            .endDate(Date.valueOf(LocalDate.now().plusDays(setEndDate())))
            .price(1000.0)
            .image(":)))")
            .amount(amountSetter())
            .company(hyundai)
            .build();

    public Coupon cinemacity = Coupon.builder()
            .category(Category.ENTERTAINMENT)
            .title("vip lounge")
            .description("kama food")
            .startDate(Date.valueOf(LocalDate.now().minusDays(setStartDate())))
            .endDate(Date.valueOf(LocalDate.now().plusDays(setEndDate())))
            .price(60.0)
            .image(":)))")
            .amount(amountSetter())
            .company(hyundai)
            .build();

    public Coupon musicPerformanceClub = Coupon.builder()
            .category(Category.ENTERTAINMENT)
            .title("vip lounge")
            .description("vip accesses ")
            .startDate(Date.valueOf(LocalDate.now().minusDays(setStartDate())))
            .endDate(Date.valueOf(LocalDate.now().plusDays(setEndDate())))
            .price(125.0)
            .image(":)))")
            .amount(amountSetter())
            .company(gucci)
            .build();

    public Coupon tatooMania = Coupon.builder()
            .category(Category.FASHION)
            .title("one + one ")
            .description("second tatto is free ")
            .startDate(Date.valueOf(LocalDate.now().minusDays(setStartDate())))
            .endDate(Date.valueOf(LocalDate.now().plusDays(setEndDate())))
            .price(210.10)
            .image(":)))")
            .amount(amountSetter())
            .company(gucci)
            .build();

    public Coupon gymBoxer = Coupon.builder()
            .category(Category.ENTERTAINMENT)
            .title("free month ")
            .description("free month membership ")
            .startDate(Date.valueOf(LocalDate.now().minusDays(setStartDate())))
            .endDate(Date.valueOf(LocalDate.now().plusDays(setEndDate())))
            .price(30.0)
            .image(":)))")
            .amount(amountSetter())
            .company(ibm)
            .build();

    public Coupon hairForYou = Coupon.builder()
            .category(Category.FASHION)
            .title("hair cut")
            .description("free hir cut ")
            .startDate(Date.valueOf(LocalDate.now().minusDays(setStartDate())))
            .endDate(Date.valueOf(LocalDate.now().plusDays(setEndDate())))
            .price(20.0)
            .image(":)))")
            .amount(amountSetter())
            .company(ibm)
            .build();

    public Coupon led = Coupon.builder()
            .category(Category.ELECTRICITY)
            .title("30 meter led strip")
            .description(" buy 20 bubble lamp get 30 meter led for free")
            .startDate(Date.valueOf(LocalDate.now().minusDays(setStartDate())))
            .endDate(Date.valueOf(LocalDate.now().plusDays(setEndDate())))
            .price(340.0)
            .image(":)))")
            .amount(amountSetter())
            .company(gucci)
            .build();

    public Coupon steakHouse = Coupon.builder()
            .category(Category.RESTAURANT)
            .title("couple meat")
            .description("get 3 steaks 2 ribs 5 starters and 2 deserts ")
            .startDate(Date.valueOf(LocalDate.now().minusDays(setStartDate())))
            .endDate(Date.valueOf(LocalDate.now().plusDays(setEndDate())))
            .price(550.0)
            .image(":)))")
            .amount(amountSetter())
            .company(gucci)
            .build();

    public Coupon romanticVacation = Coupon.builder()
            .category(Category.VACATION)
            .title("vacation")
            .description(" romantic weakened in the nature ")
            .startDate(Date.valueOf(LocalDate.now().minusDays(setStartDate())))
            .endDate(Date.valueOf(LocalDate.now().plusDays(setEndDate())))
            .price(1000.0)
            .image(":)))")
            .amount(amountSetter())
            .company(ibm)
            .build();

    public Coupon vacationInTheCity = Coupon.builder()
            .category(Category.VACATION)
            .title("hotel in tel aviv")
            .description("weakened in Hilton hotel ")
            .startDate(Date.valueOf(LocalDate.now().minusDays(setStartDate())))
            .endDate(Date.valueOf(LocalDate.now().plusDays(setEndDate())))
            .price(2000.0)
            .image(":)))")
            .amount(amountSetter())
            .company(facebook)
            .build();

    public Coupon shoesMania = Coupon.builder()
            .category(Category.FASHION)
            .title("nike shoes")
            .description("second pair for free")
            .startDate(Date.valueOf(LocalDate.now().minusDays(setStartDate())))
            .endDate(Date.valueOf(LocalDate.now().plusDays(setEndDate())))
            .price(100.0)
            .image(":)))")
            .amount(amountSetter())
            .company(facebook)
            .build();

    public Coupon electra = Coupon.builder()
            .category(Category.ELECTRICITY)
            .title("mini air condition")
            .description("air condition for small spaces")
            .startDate(Date.valueOf(LocalDate.now().minusDays(setStartDate())))
            .endDate(Date.valueOf(LocalDate.now().plusDays(setEndDate())))
            .price(180.0)
            .image(":)))")
            .amount(amountSetter())
            .company(ibm)
            .build();

    public Coupon jimbori = Coupon.builder()
            .category(Category.ENTERTAINMENT)
            .title("jimbo")
            .description("5 entries to the jumbori")
            .startDate(Date.valueOf(LocalDate.now().minusDays(setStartDate())))
            .endDate(Date.valueOf(LocalDate.now().plusDays(setEndDate())))
            .price(140)
            .image(":)))")
            .amount(amountSetter())
            .company(facebook)
            .build();

    public Coupon weRun = Coupon.builder()
            .category(Category.ENTERTAINMENT)
            .title(" tennis lessons ")
            .description("5 tennis lessons")
            .startDate(Date.valueOf(LocalDate.now().minusDays(setStartDate())))
            .endDate(Date.valueOf(LocalDate.now().plusDays(setEndDate())))
            .price(448)
            .image(":)))")
            .amount(amountSetter())
            .company(google)
            .build();

    public Coupon luxuryWatches = Coupon.builder()
            .category(Category.FASHION)
            .title(" Rollex ")
            .description("Rollex db321")
            .startDate(Date.valueOf(LocalDate.now().minusDays(setStartDate())))
            .endDate(Date.valueOf(LocalDate.now().plusDays(setEndDate())))
            .price(32122)
            .image(":)))")
            .amount(amountSetter())
            .company(google)
            .build();

    public Coupon tripAdvice = Coupon.builder()
            .category(Category.VACATION)
            .title(" into the wild ")
            .description("3 days trip in the desert ")
            .startDate(Date.valueOf(LocalDate.now().minusDays(setStartDate())))
            .endDate(Date.valueOf(LocalDate.now().plusDays(setEndDate())))
            .price(1456)
            .image(":)))")
            .amount(amountSetter())
            .company(google)
            .build();

    public Coupon chocolateFactory = Coupon.builder()
            .category(Category.RESTAURANT)
            .title("chocolate adventure")
            .description("tour on chocolate factory")
            .startDate(Date.valueOf(LocalDate.now().minusDays(setStartDate())))
            .endDate(Date.valueOf(LocalDate.now().plusDays(setEndDate())))
            .price(118)
            .image(":)))")
            .amount(amountSetter())
            .company(ibm)
            .build();

    public Coupon wineAndFINE = Coupon.builder()
            .category(Category.RESTAURANT)
            .title("wine fest")
            .description(" drink all day ")
            .startDate(Date.valueOf(LocalDate.now().minusDays(setStartDate())))
            .endDate(Date.valueOf(LocalDate.now().plusDays(setEndDate())))
            .price(90)
            .image(":)))")
            .amount(amountSetter())
            .company(facebook)
            .build();

    public Coupon noaORmergi = Coupon.builder()
            .category(Category.ENTERTAINMENT)
            .title("noa or mergi")
            .description("2 vip tickets for Noa Kirel or Mergi concert")
            .startDate(Date.valueOf(LocalDate.now().minusDays(setStartDate())))
            .endDate(Date.valueOf(LocalDate.now().plusDays(setEndDate())))
            .price(510)
            .image(":)))")
            .amount(amountSetter())
            .company(google)
            .build();

    public Coupon socks4U = Coupon.builder()
            .category(Category.FASHION)
            .title("socks for you")
            .description("5 pairs of socks in different colors")
            .startDate(Date.valueOf(LocalDate.now().minusDays(setStartDate())))
            .endDate(Date.valueOf(LocalDate.now().plusDays(setEndDate())))
            .price(130)
            .image(":)))")
            .amount(amountSetter())
            .company(google)
            .build();

    public Coupon noSecond = Coupon.builder()
            .category(Category.RESTAURANT)
            .title("eyal ein sheni lo")
            .description("very very yumi food for a cute couple")
            .startDate(Date.valueOf(LocalDate.now().minusDays(setStartDate())))
            .endDate(Date.valueOf(LocalDate.now().plusDays(setEndDate())))
            .price(1200)
            .image(":)))")
            .amount(amountSetter())
            .company(google)
            .build();

    public Coupon expiredCoupon = Coupon.builder()
            .category(Category.RESTAURANT)
            .title("mcdonald's")
            .description("one meal")
            .startDate(Date.valueOf(LocalDate.now().minusDays(setStartDate())))
            .endDate(Date.valueOf(LocalDate.now().plusDays(1)))
            .price(0.99)
            .image(":)))")
            .amount(amountSetter())
            .company(google)
            .build();

    public Coupon noAmountCoupon = Coupon.builder()
            .category(Category.RESTAURANT)
            .title("macBook Air m1")
            .description("Apple original")
            .startDate(Date.valueOf(LocalDate.now().minusDays(setStartDate())))
            .endDate(Date.valueOf(LocalDate.now().plusDays(1)))
            .price(99)
            .image(":)))")
            .amount(0)
            .company(google)
            .build();

    ///customers///

    public Customer itzik = Customer.builder()
            .firstName("itzik")
            .lastName("seaman-tof")
            .email("itzik@gmail.com")
            .password("1234")
            .build();
    public Customer tomer = Customer.builder()
            .firstName("tomer")
            .lastName("shimony")
            .email("tomer@gmail.com")
            .password("1234")
            .build();
    public Customer yuri = Customer.builder()
            .firstName("yuri")
            .lastName("roeeeei")
            .email("yuri@gmail.com")
            .password("1234")
            .build();
    public Customer barak = Customer.builder()
            .firstName("barak")
            .lastName("hamdani")
            .email("barak@gmail.com")
            .password("1234")
            .build();
    public Customer asi = Customer.builder()
            .firstName("asi")
            .lastName("taragano")
            .email("asi@gmail.com")
            .password("1234")
            .build();
    public Customer zeevNoCoupons = Customer.builder()
            .firstName("zeevNoCoupons")
            .lastName("mindali")
            .email("zeev@gmail.com")
            .password("zeev")
            .build();

    @Override
    public void run(String... args) throws Exception {
        adminService.addCompany(hyundai);
        adminService.addCompany(gucci);
        adminService.addCompany(ibm);
        adminService.addCompany(facebook);
        adminService.addCompany(google);

        adminService.addCustomer(itzik);
        adminService.addCustomer(tomer);
        adminService.addCustomer(yuri);
        adminService.addCustomer(barak);
        adminService.addCustomer(asi);
        adminService.addCustomer(zeevNoCoupons);

        companyService.addCoupon(tvHouse);
        companyService.addCoupon(zarra);
        companyService.addCoupon(totto);
        companyService.addCoupon(skiDeal);
        companyService.addCoupon(cinemacity);
        companyService.addCoupon(sony);
        companyService.addCoupon(musicPerformanceClub);
        companyService.addCoupon(tatooMania);
        companyService.addCoupon(gymBoxer);
        companyService.addCoupon(hairForYou);
        companyService.addCoupon(led);
        companyService.addCoupon(steakHouse);
        companyService.addCoupon(romanticVacation);
        companyService.addCoupon(vacationInTheCity);
        companyService.addCoupon(shoesMania);
        companyService.addCoupon(electra);
        companyService.addCoupon(jimbori);
        companyService.addCoupon(weRun);
        companyService.addCoupon(luxuryWatches);
        companyService.addCoupon(tripAdvice);
        companyService.addCoupon(chocolateFactory);
        companyService.addCoupon(wineAndFINE);
        companyService.addCoupon(noaORmergi);
        companyService.addCoupon(socks4U);
        companyService.addCoupon(noSecond);

        companyService.addCoupon(expiredCoupon);
        this.expiredCoupon.setEndDate(Date.valueOf(LocalDate.now().minusYears(1)));
        this.expiredCoupon.setPurchases(new HashSet<>());
        companyService.updateCoupon(expiredCoupon);

        companyService.addCoupon(noAmountCoupon);

        customerService.purchaseCoupon(adminService.getOneCustomer(1),1);
        customerService.purchaseCoupon(adminService.getOneCustomer(1),2);
        customerService.purchaseCoupon(adminService.getOneCustomer(1),3);
        customerService.purchaseCoupon(adminService.getOneCustomer(1),4);
        customerService.purchaseCoupon(adminService.getOneCustomer(1),5);
        customerService.purchaseCoupon(adminService.getOneCustomer(2),6);
        customerService.purchaseCoupon(adminService.getOneCustomer(2),7);
        customerService.purchaseCoupon(adminService.getOneCustomer(2),8);
        customerService.purchaseCoupon(adminService.getOneCustomer(2),9);
        customerService.purchaseCoupon(adminService.getOneCustomer(2),10);
        customerService.purchaseCoupon(adminService.getOneCustomer(3),11);
        customerService.purchaseCoupon(adminService.getOneCustomer(3),12);
        customerService.purchaseCoupon(adminService.getOneCustomer(3),13);
        customerService.purchaseCoupon(adminService.getOneCustomer(3),14);
        customerService.purchaseCoupon(adminService.getOneCustomer(3),15);
        customerService.purchaseCoupon(adminService.getOneCustomer(4),16);
        customerService.purchaseCoupon(adminService.getOneCustomer(4),17);
        customerService.purchaseCoupon(adminService.getOneCustomer(4),18);
        customerService.purchaseCoupon(adminService.getOneCustomer(4),19);
        customerService.purchaseCoupon(adminService.getOneCustomer(4),20);
        customerService.purchaseCoupon(adminService.getOneCustomer(5),21);
        customerService.purchaseCoupon(adminService.getOneCustomer(5),22);
        customerService.purchaseCoupon(adminService.getOneCustomer(5),23);
        customerService.purchaseCoupon(adminService.getOneCustomer(5),24);
        customerService.purchaseCoupon(adminService.getOneCustomer(5),25);








    }
}
