package com.savvycom.controller;

import com.savvycom.convert.CustomerConvert;
import com.savvycom.dto.APIResponse;
import com.savvycom.dto.CustomerDTO;
import com.savvycom.entity.Contract;
import com.savvycom.entity.Customer;
import com.savvycom.repository.ContractRepository;
import com.savvycom.repository.CustomerRepository;
import com.savvycom.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lam.le
 * @created 26/08/2021
 */

@CrossOrigin
@RestController
@RequestMapping("/api/customer")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    ContractRepository contractRepository;

    private static final String message = "Success";

    // customer list - done
    @GetMapping("/getAll")
    @PreAuthorize("hasRole('ROLE_HR') or hasRole('ROLE_SALE') or hasRole('ROLE_DM') or hasRole('ROLE_PM')")
    public ResponseEntity<APIResponse<List<CustomerDTO>>> getAllCustomer() {
        List<CustomerDTO> customerDTOList = customerService.getAllCustomer();
        APIResponse<List<CustomerDTO>> response = new APIResponse<>();
        response.setMessage(message);
        response.setStatus(HttpStatus.OK.value());
        response.setData(customerDTOList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getAll/country")
    public ResponseEntity<APIResponse<List>> getAllCountry() {
        List allCountry = customerRepository.getAllCountry();
        APIResponse<List> response = new APIResponse<>();
        response.setMessage(message);
        response.setStatus(HttpStatus.OK.value());
        response.setData(allCountry);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getAll/salespic")
    public ResponseEntity<APIResponse<List>> getAllSalesPic() {
        List allCountry = customerRepository.getAllSalesPic();
        APIResponse<List> response = new APIResponse<>();
        response.setMessage(message);
        response.setStatus(HttpStatus.OK.value());
        response.setData(allCountry);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<CustomerDTO>> getCustomerByNo(@PathVariable("id") Long id) {
        Customer customer = customerRepository.getCustomerByNo(id);
        CustomerDTO customerDTO = CustomerConvert.entityToDTO(customer);
        APIResponse<CustomerDTO> response = new APIResponse<>();
        response.setData(customerDTO);
        response.setStatus(HttpStatus.OK.value());
        response.setMessage(message);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // page
    @GetMapping("/customerpage")
    public ResponseEntity<APIResponse<Page<CustomerDTO>>> getCustomerPage(
            @RequestParam(defaultValue = "0", required = false) Integer page,
            @RequestParam(defaultValue = "5", required = false) Integer size,
            @RequestParam(defaultValue = "ASC", required = false) String sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("no").descending());
        Page<Customer> customer = customerRepository.getCustomers(pageable);
        Page<CustomerDTO> customerDTOPage = customer.map(CustomerConvert::entityToDTO);
        APIResponse<Page<CustomerDTO>> responseData = new APIResponse<>();
        responseData.setStatus(HttpStatus.OK.value());
        responseData.setMessage("Paging Success!");
        responseData.setData(customerDTOPage);
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    // search by CustomerId or CustomerName
    @GetMapping("/search")
    public ResponseEntity<APIResponse<List<CustomerDTO>>> searchCustomer(
            @RequestParam(defaultValue = "", required = false) String search) {
        if (search.isEmpty()) {
            List<CustomerDTO> customerDTOList = customerService.getAllCustomer();
            APIResponse<List<CustomerDTO>> response = new APIResponse<>();
            response.setMessage(message);
            response.setStatus(HttpStatus.OK.value());
            response.setData(customerDTOList);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            List<Customer> customer = customerRepository.getCustomerByCustomerIdContainingOrNameContaining(search, search);
            APIResponse<List<CustomerDTO>> responseData = new APIResponse<>();
            List<CustomerDTO> customerDTOList = new ArrayList<>();
            for (Customer c : customer) {
                CustomerDTO customer1 = CustomerConvert.entityToDTO(c);
                customerDTOList.add(customer1);
            }
            responseData.setStatus(HttpStatus.OK.value());
            responseData.setMessage(message);
            responseData.setData(customerDTOList);
            return new ResponseEntity<>(responseData, HttpStatus.OK);
        }
    }

    @GetMapping("/checklinked/{id}")
    public ResponseEntity<APIResponse<Boolean>> searchCustomer(
            @PathVariable Long id) {
        Customer customer = customerRepository.getCustomerByNo(id);
        List<Contract> contract = contractRepository.findContractByCustomerName(customer.getName());
        if (!contract.isEmpty()) {
            APIResponse response = new APIResponse<>();
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("This customer has already been linked to contracts!");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            APIResponse response = new APIResponse<>();
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("This customer hasn't linked to any contracts yet!");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ROLE_SALE') or hasRole('ROLE_DM') or hasRole('ROLE_PM')")
    public ResponseEntity<APIResponse<CustomerDTO>> createCustomer(@Valid @RequestBody CustomerDTO customerDTO) {
        Customer customer = new Customer();
        customer.setNo(customerDTO.getNo());
        customer.setCustomerId(customerDTO.getCustomerId());
        customer.setName(customerDTO.getName());
        customer.setCountry(customerDTO.getCountry());
        customer.setSalesPic(customerDTO.getSalesPic());
        customer.setCreateDate(customerDTO.getCreateDate());
        customerRepository.save(customer);
        APIResponse<CustomerDTO> response = new APIResponse<>();
        response.setData(customerDTO);
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Success");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ROLE_SALE') or hasRole('ROLE_DM')")
    public ResponseEntity<APIResponse<CustomerDTO>> updateCustomer(@Valid @RequestBody CustomerDTO customerDTO, @PathVariable Long id) {
        CustomerDTO customerDTO1 = customerService.updateCustomer(customerDTO, id);
        APIResponse<CustomerDTO> response = new APIResponse<>();
        response.setData(customerDTO1);
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Updated Successfully!");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Transactional
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_SALE') or hasRole('ROLE_DM')")
    public ResponseEntity deleteCustomerByNo(@PathVariable("id") Long no) {
        Customer customer = customerRepository.getCustomerByNo(no);
        List<Contract> contract = contractRepository.findContractByCustomerName(customer.getName());
        if (!contract.isEmpty()) {
            APIResponse response = new APIResponse<>();
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("This customer has already been linked to contracts!");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            customerRepository.deleteByNo(no);
            APIResponse response = new APIResponse<>();
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Delete Successfully!");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    // filter by
    @GetMapping("/filter/{country}")
    public ResponseEntity<APIResponse<List<CustomerDTO>>> getCustomerByCountry(@PathVariable String country) {
        List<Customer> customer = customerRepository.getCustomerByCountry(country);
        List<CustomerDTO> customerDTOList = new ArrayList<>();
        for (Customer c : customer) {
            CustomerDTO customer1 = CustomerConvert.entityToDTO(c);
            customerDTOList.add(customer1);
        }
        APIResponse<List<CustomerDTO>> response = new APIResponse<>();
        response.setData(customerDTOList);
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Success!");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/filters/{sales_pic}")
    public ResponseEntity<APIResponse<List<CustomerDTO>>> getCustomerBySalesPic(@PathVariable String sales_pic) {
        List<Customer> customers = customerRepository.getCustomerBySalesPic(sales_pic);
        List<CustomerDTO> customerDTOList = new ArrayList<>();
        for (Customer c : customers) {
            CustomerDTO customerDTO = CustomerConvert.entityToDTO(c);
            customerDTOList.add(customerDTO);
        }
        APIResponse<List<CustomerDTO>> response = new APIResponse<>();
        response.setData(customerDTOList);
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Success!");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}