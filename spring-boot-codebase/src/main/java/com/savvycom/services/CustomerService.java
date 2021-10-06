package com.savvycom.services;

import com.savvycom.dto.CustomerDTO;

import java.util.List;

public interface CustomerService {
    List<CustomerDTO> getAllCustomer();

    CustomerDTO createCustomer(CustomerDTO customerDTO);

    CustomerDTO updateCustomer(CustomerDTO customerDTO, Long id);

    CustomerDTO getCustomerById(Long id);

    boolean deleteCustomerByNo(Long id);
}
