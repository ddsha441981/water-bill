package com.cwc.water.payload;

import java.util.ArrayList;
import java.util.List;

import com.cwc.water.model.Address;
import com.cwc.water.model.Customer;

import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
@ToString
public class CustomerRequest {
	
	private Long customerId; 
	private String customerName;
	private String customerMobile;
	private Boolean customerIsActive;


	private List<Address> address = new ArrayList();
	
	
//	Bidirectional Relationship Manually
	
	public void setCustomerInAddresses(Customer customer) {
        if (address != null) {
            address.forEach(address -> address.setCustomer(customer));
        }
    }
}
