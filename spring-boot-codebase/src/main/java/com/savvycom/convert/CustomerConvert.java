package com.savvycom.convert;

import com.savvycom.dto.CustomerDTO;
import com.savvycom.entity.Customer;

public class CustomerConvert {
    public static CustomerDTO entityToDTO(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setNo(customer.getNo());
        customerDTO.setCustomerId(customer.getCustomerId());
        customerDTO.setName(customer.getName());
        customerDTO.setCountry(customer.getCountry());
        customerDTO.setSalesPic(customer.getSalesPic());
        customerDTO.setCreateDate(customer.getCreateDate());
        return customerDTO;
    }

    public static Customer DTOToEntity(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        customer.setNo(customerDTO.getNo());
        customer.setCustomerId(customerDTO.getCustomerId());
        customer.setName(customerDTO.getName());
        customer.setCountry(customerDTO.getCountry());
        customer.setSalesPic(customerDTO.getSalesPic());
        customer.setCreateDate(customerDTO.getCreateDate());
        return customer;
    }
}
