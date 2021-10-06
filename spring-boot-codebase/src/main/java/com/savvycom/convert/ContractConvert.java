package com.savvycom.convert;

import com.savvycom.dto.ContractDTO;
import com.savvycom.entity.Contract;

/**
 * @author lam.le
 * @created 01/09/2021
 */
public class ContractConvert {
    public static ContractDTO entityToDTO(Contract contract) {
        ContractDTO contractDTO = new ContractDTO();
        contractDTO.setNo(contract.getNo());
        contractDTO.setContractId(contract.getContractId());
        contractDTO.setContractType(contract.getContractType());
        contractDTO.setSalesPic(contract.getSalesPic());
        contractDTO.setCustomerName(contract.getCustomer().getName());
        contractDTO.setCreateDate(contract.getCreateDate());
        contractDTO.setStatus(contract.getStatus());
        return contractDTO;
    }

    public static Contract dtoToEntity(ContractDTO contractDTO) {
        Contract contract = new Contract();
        contract.setNo(contractDTO.getNo());
        contract.setContractId(contractDTO.getContractId());
        contract.setContractType(contractDTO.getContractType());
        contract.setSalesPic(contractDTO.getSalesPic());
        contract.setCreateDate(contractDTO.getCreateDate());
        contract.setStatus(contractDTO.getStatus());
        return contract;
    }
}
