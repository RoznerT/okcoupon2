package com.okcoupon.okcoupon.advise;

import com.fasterxml.jackson.core.JsonParseException;
import com.okcoupon.okcoupon.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ControllerAdvice
public class CouponSystemAdvise {
    /**
     * method invoked when Invalid-User-Exception has thrown
     * response status returns from the server is UNAUTHORIZED
     * @param err Invalid-User-Exception, holds an error message that describes the error occurred
     * @return object, an instance of ErrorDetail.Class that has two fields: Error-Title, Error-Description
     */
    @ExceptionHandler(value = {InvalidUserException.class})
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public ErrorDetail invalidUserMessage(Exception err) {
        return new ErrorDetail("Invalid user! ", err.getMessage());
    }

    /**
     * method invoked when Not-Found-Exception has thrown
     * response status returns from the server is NOT_FOUND
     * @param err Not-Found-Exception, holds an error message that describes the error occurred
     * @return object, an instance of ErrorDetail.Class that has two fields: Error-Title, Error-Description
     */
    @ExceptionHandler(value = {NotFoundException.class})
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public ErrorDetail notFoundMessage(Exception err) {
        return new ErrorDetail("Not exists! ", err.getMessage());
    }

    /**
     * method invoked when Duplicate-Item-Exception has thrown
     * response status returns from the server is BAD_REQUEST
     * @param err Duplicate-Item-Exception, holds an error message that describes the error occurred
     * @return object, an instance of ErrorDetail.Class that has two fields: Error-Title, Error-Description
     */
    @ExceptionHandler(value = {DuplicateItemException.class})
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorDetail duplicateItemMessage(Exception err) {
        return new ErrorDetail("Bad request, duplicate details! ", err.getMessage());
    }

    /**
     * method invoked when Duplicate-Name-Exception has thrown
     * response status returns from the server is BAD_REQUEST
     * @param err Duplicate-Name-Exception, holds an error message that describes the error occurred
     * @return object, an instance of ErrorDetail.Class that has two fields: Error-Title, Error-Description
     */
    @ExceptionHandler(value = {DuplicateNameException.class})
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorDetail duplicateNameMessage(Exception err) {
        return new ErrorDetail("Bad request, duplicate company name! ", err.getMessage());
    }

    /**
     * method invoked when Duplicate-Name-Exception has thrown
     * response status returns from the server is BAD_REQUEST
     * @param err Duplicate-Email-Exception, holds an error message that describes the error occurred
     * @return object, an instance of ErrorDetail.Class that has two fields: Error-Title, Error-Description
     */
    @ExceptionHandler(value = {DuplicateEmailException.class})
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorDetail duplicateEmailMessage(Exception err) {
        return new ErrorDetail("Bad request, duplicate Email! ", err.getMessage());
    }

    /**
     * method invoked when Update-Id-Exception has thrown
     * response status returns from the server is BAD_REQUEST
     * @param err Update-Id-Exception, holds an error message that describes the error occurred
     * @return object, an instance of ErrorDetail.Class that has two fields: Error-Title, Error-Description
     */
    @ExceptionHandler(value = {UpdateIDException.class})
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorDetail updateIdMessage(Exception err) {
        return new ErrorDetail("Bad request, it's illegal to update the id! ", err.getMessage());
    }
    /**
     * method invoked when Update-Name-Exception has thrown
     * response status returns from the server is BAD_REQUEST
     * @param err Update-Name-Exception, holds an error message that describes the error occurred
     * @return object, an instance of ErrorDetail.Class that has two fields: Error-Title, Error-Description
     */
    @ExceptionHandler(value = {UpdateNameException.class})
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorDetail updateNameMessage(Exception err) {
        return new ErrorDetail("Bad request, it's illegal to update the name! ", err.getMessage());
    }
    /**
     * method invoked when Invalid-Email-Exception has thrown
     * response status returns from the server is BAD_REQUEST
     * @param err Invalid-Email-Exception, holds an error message that describes the error occurred
     * @return object, an instance of ErrorDetail.Class that has two fields: Error-Title, Error-Description
     */
    @ExceptionHandler(value = {InvalidEmailException.class})
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorDetail invalidEmailMessage(Exception err) {
        return new ErrorDetail("Bad request, invalid email! ", err.getMessage());
    }
    /**
     * method invoked when Expired-Coupon-Exception has thrown
     * response status returns from the server is BAD_REQUEST
     * @param err Expired-Coupon-Exception, holds an error message that describes the error occurred
     * @return object, an instance of ErrorDetail.Class that has two fields: Error-Title, Error-Description
     */
    @ExceptionHandler(value = {ExpiredCouponException.class})
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorDetail expiresCouponMessage(Exception err) {
        return new ErrorDetail("Bad request, coupon is expired! ", err.getMessage());
    }
    /**
     * method invoked when No-Coupon-Category-Exception has thrown
     * response status returns from the server is BAD_REQUEST
     * @param err No-Coupon-Category-Exception, holds an error message that describes the error occurred
     * @return object, an instance of ErrorDetail.Class that has two fields: Error-Title, Error-Description
     */
    @ExceptionHandler(value = {NoCouponsCategoryException.class})
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorDetail noCouponsByCategoryMessage(Exception err) {
        return new ErrorDetail("Sorry! ", err.getMessage());
    }
    /**
     * method invoked when No-Coupon-Price-Exception has thrown
     * response status returns from the server is BAD_REQUEST
     * @param err No-Coupon-Price-Exception, holds an error message that describes the error occurred
     * @return object, an instance of ErrorDetail.Class that has two fields: Error-Title, Error-Description
     */
    @ExceptionHandler(value = {NoCouponsPriceException.class})
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorDetail noCouponsByPriceMessage(Exception err) {
        return new ErrorDetail("Sorry! ", err.getMessage());
    }
    /**
     * method invoked when Coupon-Not-Found-Exception has thrown
     * response status returns from the server is BAD_REQUEST
     * @param err Coupon-Not-Found-Exception, holds an error message that describes the error occurred
     * @return object, an instance of ErrorDetail.Class that has two fields: Error-Title, Error-Description
     */
    @ExceptionHandler(value = {CouponNotFoundException.class})
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorDetail couponNotFoundMessage(Exception err) {
        return new ErrorDetail("Sorry! ", err.getMessage());
    }
    /**
     * method invoked when No-Coupons-Left-Exception has thrown
     * response status returns from the server is BAD_REQUEST
     * @param err No-Coupons-Left-Exception, holds an error message that describes the error occurred
     * @return object, an instance of ErrorDetail.Class that has two fields: Error-Title, Error-Description
     */
    @ExceptionHandler(value = {NoCouponsLeftException.class})
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorDetail noCouponsMessage(Exception err) {
        return new ErrorDetail("Sorry! ", err.getMessage());
    }
    /**
     * method invoked when Same-Purchase-Exception has thrown
     * response status returns from the server is BAD_REQUEST
     * @param err Same-Purchase-Exception, holds an error message that describes the error occurred
     * @return object, an instance of ErrorDetail.Class that has two fields: Error-Title, Error-Description
     */
    @ExceptionHandler(value = {SamePurchaseException.class})
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorDetail samePurchaseMessage(Exception err) {
        return new ErrorDetail("Sorry you can't buy this coupon! ", err.getMessage());
    }
    /**
     * method invoked when No-Coupon-Purchase-Exception has thrown
     * response status returns from the server is BAD_REQUEST
     * @param err No-Coupon-Purchase-Exception, holds an error message that describes the error occurred
     * @return object, an instance of ErrorDetail.Class that has two fields: Error-Title, Error-Description
     */
    @ExceptionHandler(value = {NoCouponPurchasesException.class})
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorDetail NoPurchaseMessage(Exception err) {
        return new ErrorDetail("Sorry! ", err.getMessage());
    }
    /**
     * method invoked when No-Coupon-Company-Exception has thrown
     * response status returns from the server is BAD_REQUEST
     * @param err No-Coupon-Company-Exception, holds an error message that describes the error occurred
     * @return object, an instance of ErrorDetail.Class that has two fields: Error-Title, Error-Description
     */
    @ExceptionHandler(value = {NoCouponsCompanyException.class})
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorDetail NoCouponsCompanyMessage(Exception err) {
        return new ErrorDetail("No Coupons! ", err.getMessage());
    }
    /**
     * method invoked when No-Coupon-Customer-Exception has thrown
     * response status returns from the server is BAD_REQUEST
     * @param err No-Coupon-Customer-Exception, holds an error message that describes the error occurred
     * @return object, an instance of ErrorDetail.Class that has two fields: Error-Title, Error-Description
     */
    @ExceptionHandler(value = {NoCouponsCustomerException.class})
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorDetail NoCouponsCustomerMessage(Exception err) {
        return new ErrorDetail("No Coupons! ", err.getMessage());
    }
    /**
     * method invoked when No-Companies-Exception has thrown
     * response status returns from the server is BAD_REQUEST
     * @param err No-Companies-Exception, holds an error message that describes the error occurred
     * @return object, an instance of ErrorDetail.Class that has two fields: Error-Title, Error-Description
     */
    @ExceptionHandler(value = {NoCompaniesException.class})
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorDetail NoCompaniesMessage(Exception err) {
        return new ErrorDetail("No Companies in the system! ", err.getMessage());
    }
    /**
     * method invoked when No-Customers-Exception has thrown
     * response status returns from the server is BAD_REQUEST
     * @param err No-Customers-Exception, holds an error message that describes the error occurred
     * @return object, an instance of ErrorDetail.Class that has two fields: Error-Title, Error-Description
     */
    @ExceptionHandler(value = {NoCustomersException.class})
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorDetail NoCustomersMessage(Exception err) {
        return new ErrorDetail("No Customers in the system! ", err.getMessage());
    }
    /**
     * method invoked when No-Permission-Exception has thrown
     * response status returns from the server is BAD_REQUEST
     * @param err No-Permission-Exception, holds an error message that describes the error occurred
     * @return object, an instance of ErrorDetail.Class that has two fields: Error-Title, Error-Description
     */
    @ExceptionHandler(value = {NoPermissionException.class})
    @ResponseStatus(code = HttpStatus.FORBIDDEN)
    public ErrorDetail NoPermissionMessage(Exception err) {
        return new ErrorDetail("Access denied!", err.getMessage());
    }
    /**
     * method invoked when Uncompleted-Fields-Exception has thrown
     * response status returns from the server is BAD_REQUEST
     * @param err Uncompleted-Fields-Exception, holds an error message that describes the error occurred
     * @return object, an instance of ErrorDetail.Class that has two fields: Error-Title, Error-Description
     */
    @ExceptionHandler(value = {UncompletedFieldsException.class})
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorDetail UncompletedFieldsException(Exception err) {
        return new ErrorDetail("Uncompleted fields!", err.getMessage());
    }
    /**
     * method invoked when Unknown-Role-Exception has thrown
     * response status returns from the server is BAD_REQUEST
     * @param err Unknown-Role-Exception, holds an error message that describes the error occurred
     * @return object, an instance of ErrorDetail.Class that has two fields: Error-Title, Error-Description
     */
    @ExceptionHandler(value = {UnknownRoleException.class})
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorDetail UnknownRoleException(Exception err){
        return new ErrorDetail("Bad Input!", err.getMessage());
    }
    /**
     * method invoked when Unknown-Category-Exception has thrown
     * response status returns from the server is BAD_REQUEST
     * @param err Unknown-Category-Exception, holds an error message that describes the error occurred
     * @return object, an instance of ErrorDetail.Class that has two fields: Error-Title, Error-Description
     */
    @ExceptionHandler(value = {unknownCategoryException.class})
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorDetail unknownCategoryException(Exception err){
        return new ErrorDetail("Bad Input!", err.getMessage());
    }

    /**
     * method invoked when Json-Parse-Exception has thrown
     * response status returns from the server is BAD_REQUEST
     * @param err Json-Parse-Exception, holds an error message that describes the error occurred
     * @return object, an instance of ErrorDetail.Class that has two fields: Error-Title, Error-Description
     */
    @ExceptionHandler(value = {JsonParseException.class})
    @ResponseStatus(code = HttpStatus.UNPROCESSABLE_ENTITY)
    public ErrorDetail jsonParseException(Exception err){
        return new ErrorDetail("Bad input!", "please check the content and verify all fields written properly with write punctuation");
    }

    @ExceptionHandler(value = {JWTexpiredException.class})
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorDetail jwtExpiredException(Exception err){
        return new ErrorDetail("Error with Token ", err.getMessage());
    }
}
