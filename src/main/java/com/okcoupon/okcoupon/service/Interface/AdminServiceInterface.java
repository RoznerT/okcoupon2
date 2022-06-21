package com.okcoupon.okcoupon.service.Interface;

import com.okcoupon.okcoupon.beans.Company;
import com.okcoupon.okcoupon.beans.Coupon;
import com.okcoupon.okcoupon.beans.Customer;

import java.util.List;

public interface AdminServiceInterface {

    int login(String email, String password);
    void addCompany(Company company);
    void updateCompany(Company company);
    void deleteCompany(int companyId);
    Company getOneCompany(int companyId);
    List<Company> getAllCompanies();
    void addCustomer(Customer customer);
    void updateCustomer(Customer customer);
    void deleteCustomer(int customerId);
    Customer getOneCustomer(int customerId);
    List<Customer> getAllCustomers();
    List<Coupon> getCompanyCoupons(int companyId);
    List<Coupon> getCustomerCoupons(int customerId);
    void deleteExpiredCoupon();
}
