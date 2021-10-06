package com.savvycom.services.impl;

import com.savvycom.convert.ContractConvert;
import com.savvycom.dto.ContractDTO;
import com.savvycom.entity.Contract;
import com.savvycom.entity.ContractStatus;
import com.savvycom.exception.NotFoundException;
import com.savvycom.repository.ContractRepository;
import com.savvycom.services.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author lam.le
 * @created 01/09/2021
 */
@Service
public class ContractServiceImpl implements ContractService {
    @Autowired
    ContractRepository contractRepository;

    @Override
    public List<ContractDTO> getAllContract() {
        List<Contract> contractList = contractRepository.findAll();
        if (contractList.isEmpty()) {
            throw new NotFoundException("Not found Contract");
        }
        List<ContractDTO> contractDTOList = new ArrayList<>();
        for (Contract contract : contractList) {
            ContractDTO contractDTO = ContractConvert.entityToDTO(contract);
            contractDTOList.add(contractDTO);
        }
        return contractDTOList;
    }

    @Override
    public ContractDTO getContractById(Long id) {
        Optional<Contract> contract = contractRepository.findById(id);
        if (contract.isPresent()) {
            ContractDTO contractDTO = ContractConvert.entityToDTO(contract.get());
            return contractDTO;
        }
        throw new NotFoundException("Id Not Found");
    }

    @Override
    public Page<ContractDTO> getContractByPage(String searchValue, Integer pageNo, Integer pageSize, String sortBy) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());
        Page<Contract> pageResult = contractRepository.findBySearchValue(searchValue, pageable);
        return pageResult.map(ContractConvert::entityToDTO);
    }

    @Transactional(rollbackFor = NotFoundException.class)
    @Override
    public ContractDTO changeStatusContract(Long id) {
        Optional<Contract> contract = contractRepository.findById(id);
        if (contract.isPresent()) {
            switch (contract.get().getStatus()) {
                case 0:
                    contract.get().setStatus(ContractStatus.CONTRACT_CANCEL.getValue());
                    break;
                case 1:
                    contract.get().setStatus(ContractStatus.CONTRACT_DONE.getValue());
                    break;
                default:
                    break;
            }
            return ContractConvert.entityToDTO(contractRepository.save(contract.get()));
        }
        throw new NotFoundException("NotFound.id");
    }

    @Override
    @Transactional(rollbackFor = NotFoundException.class)
    public ContractDTO cancelContract(Long id) {
        Optional<Contract> contract = contractRepository.findById(id);
        if (contract.isPresent()) {
            if (contract.get().getStatus() == ContractStatus.CONTRACT_NEW.getValue()) {
                contract.get().setStatus(ContractStatus.CONTRACT_CANCEL.getValue());
            } else if (contract.get().getStatus() == ContractStatus.CONTRACT_CANCEL.getValue()) {
                contract.get().setStatus(ContractStatus.CONTRACT_NEW.getValue());
            }
            return ContractConvert.entityToDTO(contractRepository.save(contract.get()));
        }
        throw new NotFoundException("NotFound.id");
    }
}
