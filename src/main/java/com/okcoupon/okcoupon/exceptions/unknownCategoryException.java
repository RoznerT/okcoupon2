package com.okcoupon.okcoupon.exceptions;

public class unknownCategoryException extends Exception{
    public unknownCategoryException() {
        super("Unknown Category. please try again");
    }

    public unknownCategoryException(String message) {
        super(message);
    }
}
