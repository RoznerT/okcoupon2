package com.okcoupon.okcoupon.exceptions;

public class JWTexpiredException extends Exception {
    public JWTexpiredException() {
        super("Your token is expired");
    }

    public JWTexpiredException(String message) {
        super(message);
    }
}
