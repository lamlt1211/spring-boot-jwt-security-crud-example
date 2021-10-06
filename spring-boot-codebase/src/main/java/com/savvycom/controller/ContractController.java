package com.savvycom.controller;

import com.savvycom.convert.ContractConvert;
import com.savvycom.dto.APIResponse;
import com.savvycom.dto.ContractDTO;
import com.savvycom.entity.Contract;
import com.savvycom.entity.Customer;
import com.savvycom.repository.ContractRepository;
import com.savvycom.repository.CustomerRepository;
import com.savvycom.services.ContractService;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author lam.le
 * @created 01/09/2021
 */

@CrossOrigin
@RestController
@RequestMapping("/api/contract")
public class ContractController {
    @Autowired
    private ContractService contractService;

    @Autowired
    private ContractRepository contractRepository;

    @Autowired
    private CustomerRepository customerRepository;

    // done
    @GetMapping("/getAll")
    @PreAuthorize("hasRole('ROLE_SALE')")
    public ResponseEntity<APIResponse<List<ContractDTO>>> getAllContract() {
        List<ContractDTO> contractDTOList = contractService.getAllContract();
        APIResponse<List<ContractDTO>> response = new APIResponse<>();
        response.setMessage("Success");
        response.setStatus(HttpStatus.OK.value());
        response.setData(contractDTOList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_SALE')")
    public ResponseEntity<APIResponse<ContractDTO>> getContractByNo(@PathVariable("id") Long id) {
        Contract contract = contractRepository.getContractByNo(id);
        ContractDTO contractDTO = ContractConvert.entityToDTO(contract);
        APIResponse<ContractDTO> response = new APIResponse<>();
        response.setData(contractDTO);
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Success");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // get all sales pic - done
    @GetMapping("/getAll/salespic")
    public ResponseEntity<APIResponse<List>> getAllSalesPic() {
        List allCountry = contractRepository.getAllSalesPic();
        APIResponse<List> response = new APIResponse<>();
        response.setMessage("Success");
        response.setStatus(HttpStatus.OK.value());
        response.setData(allCountry);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/contractpage")
    public ResponseEntity<APIResponse<Page<ContractDTO>>> getContractPage(
            @RequestParam(defaultValue = "0", required = false) Integer page,
            @RequestParam(defaultValue = "5", required = false) Integer size,
            @RequestParam(defaultValue = "ASC", required = false) String sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("no").descending());
        Page<Contract> contracts = contractRepository.getContracts(pageable);
        Page<ContractDTO> contractDTOPage = contracts.map(ContractConvert::entityToDTO);
        APIResponse<Page<ContractDTO>> responseData = new APIResponse<>();
        responseData.setStatus(HttpStatus.OK.value());
        responseData.setMessage("Paging Success!");
        responseData.setData(contractDTOPage);
        System.out.println(contracts);
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    // search by ContractId or ContractName - done
    @GetMapping("/search")
    public ResponseEntity<APIResponse<List<ContractDTO>>> searchContract(
            @RequestParam(defaultValue = "", required = false) String search) {
        List<Contract> contract = contractRepository.getContractByContractIdOrCustomer_Name(search, search);
        APIResponse<List<ContractDTO>> responseData = new APIResponse<>();
        List<ContractDTO> contractDTOList = new ArrayList<>();
        for (Contract c : contract) {
            ContractDTO contractDTO = ContractConvert.entityToDTO(c);
            contractDTOList.add(contractDTO);
        }
        responseData.setStatus(HttpStatus.OK.value());
        responseData.setMessage("Success");
        responseData.setData(contractDTOList);
        System.out.println(contract);
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    // create - done
    @PostMapping("/create")
    @PreAuthorize("hasRole('ROLE_SALE')")
    public ResponseEntity<APIResponse<ContractDTO>> createContract(@Valid @RequestBody ContractDTO contractDTO) {
        Customer customer = customerRepository.getCustomerByName(contractDTO.getCustomerName());
        Contract contract = new Contract();
        contract.setNo(contractDTO.getNo());
        contract.setContractId(contractDTO.getContractId());
        contract.setCustomer(customer);
        contract.setContractType(contractDTO.getContractType());
        contract.setSalesPic(contractDTO.getSalesPic());
        contract.setCreateDate(contractDTO.getCreateDate());
        contract.setStatus(contractDTO.getStatus());
        contractRepository.save(contract);
        APIResponse<ContractDTO> response = new APIResponse<>();
        response.setData(contractDTO);
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Create Success");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // done
    @PutMapping("/update/{id}")
    public ResponseEntity<APIResponse<ContractDTO>> updateContract(@RequestBody ContractDTO contractDTO, @PathVariable("id") Long no) {
        Customer customer = customerRepository.getCustomerByName(contractDTO.getCustomerName());
        Contract contract = ContractConvert.dtoToEntity(contractDTO);
        contract.setCustomer(customer);
        contractRepository.save(contract);
        APIResponse<ContractDTO> response = new APIResponse<>();
        response.setData(contractDTO);
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Updated Successfully!");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Transactional
    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteContractByNo(@PathVariable("id") Long no) {
        Contract contract = contractRepository.getContractByNo(no);
        if (contract.getCustomer() != null) {
            APIResponse response = new APIResponse<>();
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("This contracts has already been linked to request!");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            contractRepository.deleteByNo(no);
            APIResponse response = new APIResponse<>();
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Delete Successfully!");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    // done
    @GetMapping("/filter/{sales_pic}")
    public ResponseEntity<APIResponse<List<ContractDTO>>> getContractBySalesPic(@PathVariable String sales_pic) {
        List<Contract> contracts = contractRepository.getContractBySalesPic(sales_pic);
        List<ContractDTO> contractDTOList = new ArrayList<>();
        for (Contract c : contracts) {
            ContractDTO contractDTO = ContractConvert.entityToDTO(c);
            contractDTOList.add(contractDTO);
        }
        APIResponse<List<ContractDTO>> response = new APIResponse<>();
        response.setData(contractDTOList);
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Success!");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // filter by type
    @GetMapping("/filters/{type}")
    public ResponseEntity<APIResponse<List<ContractDTO>>> getContractByType(@PathVariable String type) {
        List<Contract> contracts = contractRepository.getContractByContractType(type);
        List<ContractDTO> contractDTOList = new ArrayList<>();
        for (Contract c : contracts) {
            ContractDTO contractDTO = ContractConvert.entityToDTO(c);
            contractDTOList.add(contractDTO);
        }
        APIResponse<List<ContractDTO>> response = new APIResponse<>();
        response.setData(contractDTOList);
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Success!");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //filter by date
    @GetMapping("/filterByDate")
    public ResponseEntity<APIResponse<List<ContractDTO>>> filterByDate(@RequestParam(name = "start") String start, @RequestParam(name = "end") String end) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = format.parse(start);
        Date date1 = format.parse(end);
        List<Contract> contracts = contractRepository.getContractByCreateDateBetween(date, date1);
        List<ContractDTO> contractDTOList = new ArrayList<>();
        for (Contract c : contracts) {
            ContractDTO contractDTO = ContractConvert.entityToDTO(c);
            contractDTOList.add(contractDTO);
        }
        APIResponse<List<ContractDTO>> response = new APIResponse<>();
        response.setData(contractDTOList);
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Success!");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/change/{id}")
    public ResponseEntity<APIResponse<ContractDTO>> changeStatusContract(@PathVariable("id") Long id) {
        ContractDTO contractDTO = contractService.changeStatusContract(id);
        APIResponse<ContractDTO> response = new APIResponse<>();
        response.setMessage("Success");
        response.setStatus(HttpStatus.OK.value());
        response.setData(contractDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
