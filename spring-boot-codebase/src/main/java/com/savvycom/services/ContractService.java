package com.savvycom.services;

import com.savvycom.dto.ContractDTO;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author lam.le
 * @created 01/09/2021
 */

public interface ContractService {
    List<ContractDTO> getAllContract();

    ContractDTO getContractById(Long id);

    Page<ContractDTO> getContractByPage(String searchValue, Integer pageNo, Integer pageSize, String sortBy);

    ContractDTO changeStatusContract(Long id);

    ContractDTO cancelContract(Long id);

}
