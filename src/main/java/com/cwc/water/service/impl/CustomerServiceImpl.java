package com.cwc.water.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cwc.water.exception.CustomerNotFoundException;
import com.cwc.water.model.Address;
import com.cwc.water.model.Customer;
import com.cwc.water.payload.AddressRequest;
import com.cwc.water.payload.CustomerRequest;
import com.cwc.water.payload.CustomerResponse;
import com.cwc.water.repository.CustomerRepository;
import com.cwc.water.service.CustomerService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private final CustomerRepository customerRepository;

	@Override
	public CustomerResponse addCustomerDetails(CustomerRequest customerRequest) {
		Customer customer = Customer.builder()
				.customerName(customerRequest.getCustomerName())
				.customerMobile(customerRequest.getCustomerMobile())
				.customerIsActive(customerRequest.getCustomerIsActive())
				.address(customerRequest.getAddress())
				.build();
		
		customerRequest.setCustomerInAddresses(customer);
		
		customer = this.customerRepository.save(customer);

		// Convert Customer to CustomerResponse
		return mapCustomerToResponse(customer);

	}
	
	
	

	@Override
	public List<CustomerResponse> findAllCustomers() {
		List<Customer> customers = this.customerRepository.findAll();

		List<CustomerResponse> list = customers.stream().map(customer -> mapCustomerToResponse(customer)).toList();
		return list;

	}

	@Override
	public CustomerResponse updateSingleCustomer(CustomerRequest customerRequest, Long customerId) {
		Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
		if (optionalCustomer.isPresent()) {
			Customer existingCustomer = optionalCustomer.get();

			// update customer details
			existingCustomer.setCustomerName(customerRequest.getCustomerName());
			existingCustomer.setCustomerMobile(customerRequest.getCustomerMobile());
			existingCustomer.setCustomerIsActive(customerRequest.getCustomerIsActive());
			existingCustomer.setAddress(customerRequest.getAddress());
			
//			customerRequest.setCustomerInAddresses(existingCustomer);
			

			// save the updated cutomer
			customerRepository.save(existingCustomer);

			// Convert updated Customer to CustomerResponse
			return mapCustomerToResponse(existingCustomer);
		} else {
			throw new CustomerNotFoundException("Customer not found with id: " + customerId);
		}

	}

	@Override
	public CustomerResponse findSingleCustomer(Long customerId) {
		Optional<Customer> singleCustomer = customerRepository.findById(customerId);
		if (singleCustomer.isPresent()) {
			return mapCustomerToResponse(singleCustomer.get());
		} else {
			throw new CustomerNotFoundException("Customer not found with id: " + customerId);
		}

	}

	@Override
	public void deleteSingleCustomer(Long customerId) {
		Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
		if (optionalCustomer.isPresent()) {
			customerRepository.deleteById(customerId);
		} else {
			throw new CustomerNotFoundException("Customer not found with id: " + customerId);
		}

	}

	@Override
	public List<CustomerResponse> findCustomersByName(String name) {
		List<Customer> customers = customerRepository.findByCustomerNameIgnoreCase(name);
		return customers.stream().map(this::mapCustomerToResponse).collect(Collectors.toList());
	}

	@Override
	public List<CustomerResponse> findCustomersByMobileNumber(String mobileNumber) {
		List<Customer> customers = customerRepository.findByCustomerMobile(mobileNumber);
		return customers.stream().map(this::mapCustomerToResponse).collect(Collectors.toList());
	}

	private CustomerResponse mapCustomerToResponse(Customer customer) {
		CustomerResponse customerResponse = CustomerResponse.builder()
				.customerId(customer.getCustomerId())
				.customerName(customer.getCustomerName())
				.customerMobile(customer.getCustomerMobile())
				.customerIsActive(customer.getCustomerIsActive())
				.address(customer.getAddress())
				.build();
		return customerResponse;
	}

}
