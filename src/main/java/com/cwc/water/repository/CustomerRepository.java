package com.cwc.water.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cwc.water.model.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>{

	List<Customer> findByCustomerNameIgnoreCase(String name);

	List<Customer> findByCustomerMobile(String mobileNumber);

}
