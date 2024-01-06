package com.cwc.water.service;

import java.util.List;

import com.cwc.water.payload.CustomerRequest;
import com.cwc.water.payload.CustomerResponse;

public interface CustomerService {

	public CustomerResponse addCustomerDetails(CustomerRequest customerRequest);

	public CustomerResponse updateSingleCustomer(CustomerRequest customerRequest,Long customerId);

	public CustomerResponse findSingleCustomer(Long customerId);

	public void deleteSingleCustomer(Long customerId);

	public List<CustomerResponse> findAllCustomers();

	public List<CustomerResponse> findCustomersByName(String name);

	public List<CustomerResponse> findCustomersByMobileNumber(String mobileNumber);

}
