package com.okcoupon.okcoupon.controller;

import com.okcoupon.okcoupon.exceptions.InvalidUserException;
import com.okcoupon.okcoupon.exceptions.JWTexpiredException;
import com.okcoupon.okcoupon.exceptions.NoCouponsCompanyException;
import com.okcoupon.okcoupon.exceptions.NoCouponsPriceException;
import com.okcoupon.okcoupon.service.GuestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("guest")
public class GuestController {

    @Autowired
    GuestService guestService;
    /**
     * Get-method (READ) that called when the user expect to get all coupons in the system
     * @return ResponseEntity with unique Token sends via the header part, also The HttpStatus which is 200-OK
     * The List of the coupons passed throw the body part
     */
    @GetMapping("/allCouponsInSystem")
    public ResponseEntity<?> getAllCouponsInSystem() {
        return new ResponseEntity<>(guestService.getAllCouponsInSystem(), HttpStatus.ACCEPTED);
    }

    @GetMapping("/CouponsByPrice{price}")
    public ResponseEntity<?> getCouponsByMaxPrice( @PathVariable double price) throws InvalidUserException, NoCouponsPriceException, JWTexpiredException, NoCouponsCompanyException {
        return new ResponseEntity<>(guestService.getCouponByMaxPrice(price), HttpStatus.ACCEPTED);
    }
}
