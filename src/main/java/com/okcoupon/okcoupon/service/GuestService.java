package com.okcoupon.okcoupon.service;

import com.okcoupon.okcoupon.beans.Coupon;
import com.okcoupon.okcoupon.exceptions.NoCouponsCompanyException;
import com.okcoupon.okcoupon.exceptions.NoCouponsPriceException;
import com.okcoupon.okcoupon.repositories.CouponRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GuestService {

    @Autowired
    private CouponRepo couponRepo;

    /**
     * Method that called when the user expect to get all coupons in the system
     * @return List of coupons
     */
    public List<Coupon> getAllCouponsInSystem(){
        return couponRepo.findAll();
    }

    public List<Coupon> getCouponByMaxPrice(double price) throws NoCouponsPriceException {
        List<Coupon> coupons = couponRepo.findByPriceLessThan(price);
        if (coupons.isEmpty()) {
            throw new NoCouponsPriceException();
        }
        return coupons;
    }
}
