package com.savvycom.services.impl;

import com.savvycom.convert.CustomerConvert;
import com.savvycom.dto.CustomerDTO;
import com.savvycom.entity.Customer;
import com.savvycom.exception.NotFoundException;
import com.savvycom.repository.CustomerRepository;
import com.savvycom.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    CustomerRepository customerRepository;

    @Override
    public List<CustomerDTO> getAllCustomer() {
        List<Customer> customerList = customerRepository.findAll();
        if (customerList.isEmpty()) {
            throw new NotFoundException("Not found Customer");
        }
        List<CustomerDTO> customerDTOList = new ArrayList<>();
        for (Customer customer : customerList) {
            CustomerDTO customerDTO = new CustomerDTO();
            customerDTO.setNo(customer.getNo());
            customerDTO.setCustomerId(customer.getCustomerId());
            customerDTO.setName(customer.getName());
            customerDTO.setCountry(customer.getCountry());
            customerDTO.setSalesPic(customer.getSalesPic());
            customerDTO.setCreateDate(customer.getCreateDate());
            customerDTOList.add(customerDTO);
        }
        return customerDTOList;
    }

    @Override
    public CustomerDTO createCustomer(CustomerDTO customerDTO) {
        Customer c = customerRepository.findCustomerByName(customerDTO.getName());
        if (c == null) {
            Customer customer = CustomerConvert.DTOToEntity(customerDTO);
            return CustomerConvert.entityToDTO(customerRepository.save(customer));
        }
        return null;
    }

    @Override
    public CustomerDTO updateCustomer(CustomerDTO customerDTO, Long id) {
        Optional<Customer> c = customerRepository.findCustomerByNo(id);
        if (c.isPresent()) {
            Customer customer = CustomerConvert.DTOToEntity(customerDTO);
            customer.setNo(id);
            return CustomerConvert.entityToDTO(customerRepository.save(customer));
        }
        throw new NotFoundException("Id not found");
    }

    @Override
    public CustomerDTO getCustomerById(Long id) {
        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isPresent()) {
            CustomerDTO customerDTO = CustomerConvert.entityToDTO(customer.get());
            return customerDTO;
        }
        throw new NotFoundException("Id Not found!");
    }

    @Override
    public boolean deleteCustomerByNo(Long id) {
        customerRepository.deleteByNo(id);
        return true;
    }
}
