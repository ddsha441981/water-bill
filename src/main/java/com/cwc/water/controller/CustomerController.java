package com.cwc.water.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cwc.water.payload.CustomerRequest;
import com.cwc.water.payload.CustomerResponse;
import com.cwc.water.service.CustomerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/water")
@RequiredArgsConstructor
@Slf4j
public class CustomerController {
	
	@Autowired
	private final CustomerService customerService;

	@PostMapping
	public ResponseEntity<CustomerResponse> addCustomerDetails(@RequestBody CustomerRequest customerRequest){
		 String rawJson = convertObjectToJsonString(customerRequest);
		 log.info("Received JSON data: {}", rawJson);
		CustomerResponse addesCustomerDetails = this.customerService.addCustomerDetails(customerRequest);
		return new ResponseEntity<>(addesCustomerDetails,HttpStatus.CREATED);
	}
	
	
	private String convertObjectToJsonString(Object object) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            // Handle exception or log it if needed
            return null;
        }
    }
	
	@PutMapping("/{customerId}")
	public ResponseEntity<CustomerResponse> updateCustomerDetails(@RequestBody CustomerRequest customerRequest,@PathVariable("customerId") Long customerId){
		CustomerResponse updatedCustomerDetails = this.customerService.updateSingleCustomer(customerRequest,customerId);
		return new ResponseEntity<>(updatedCustomerDetails,HttpStatus.CREATED);
	}
	
	@GetMapping
	public ResponseEntity<List<CustomerResponse>> getAllCustomers(){
		List<CustomerResponse>  allCustomers = this.customerService.findAllCustomers();
		return new ResponseEntity<List<CustomerResponse>>(allCustomers,HttpStatus.FOUND);
	}
	
	
	@GetMapping("/{customerId}")
	public ResponseEntity <CustomerResponse> getSingleCustomer(@PathVariable("customerId") Long customerId){
		CustomerResponse  customer = this.customerService.findSingleCustomer(customerId);
		return new ResponseEntity<CustomerResponse>(customer,HttpStatus.FOUND);
	}
	
	@DeleteMapping("/{customerId}")
	public ResponseEntity <CustomerResponse> deleteCustomer(@PathVariable("customerId") Long customerId){
		this.customerService.deleteSingleCustomer(customerId);
		return new ResponseEntity<CustomerResponse>(HttpStatus.NO_CONTENT);
	}
	
	
	 @GetMapping("/byName")
	    public ResponseEntity<List<CustomerResponse>> findCustomersByName(@RequestParam String name) {
	        List<CustomerResponse> customers = customerService.findCustomersByName(name);
	        return new ResponseEntity<>(customers, HttpStatus.OK);
	    }

	    @GetMapping("/byMobileNumber")
	    public ResponseEntity<List<CustomerResponse>> findCustomersByMobileNumber(@RequestParam String mobileNumber) {
	        List<CustomerResponse> customers = customerService.findCustomersByMobileNumber(mobileNumber);
	        return new ResponseEntity<>(customers, HttpStatus.OK);
	    }
	
}
