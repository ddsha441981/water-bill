package com.cwc.water.payload;

import java.util.ArrayList;
import java.util.List;

import com.cwc.water.model.Address;

import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class CustomerResponse {
	
	private Long customerId;
	private String customerName;
	private String customerMobile;
	private Boolean customerIsActive;
	
private List<Address> address = new ArrayList();
}
