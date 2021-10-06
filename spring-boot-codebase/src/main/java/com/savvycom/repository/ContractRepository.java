package com.savvycom.repository;

import com.savvycom.entity.Contract;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author lam.le
 * @created 01/09/2021
 */
@Repository
public interface ContractRepository extends JpaRepository<Contract, Long> {
    List<Contract> findContractByCustomerName(String name);

    Contract getContractByNo(Long id);

    void deleteByNo(Long no);

    @Query("SELECT c FROM Customer c"
            + " WHERE (c.id LIKE %:searchValue% OR c.name LIKE %:searchValue%)")
    Page<Contract> findBySearchValue(@Param("searchValue") String searchValue, Pageable pageable);

    @Query("SELECT DISTINCT c.salesPic FROM Contract c")
    List getAllSalesPic();

    @Query("SELECT c FROM Contract c WHERE c.createDate BETWEEN:date1 AND:date2")
    List<Contract> getContractByCreateDateBetween(@Param("date1") Date start, @Param("date2") Date end);

    List<Contract> findAllByCreateDateBetween(Date start, Date end);

    List<Contract> getContractBySalesPic(String sales_pic);

    List<Contract> getContractByContractType(String type);

    List<Contract> getContractByContractIdOrCustomer_Name(String cid, String name);

    @Query("SELECT c FROM Contract c")
    Page<Contract> getContracts(Pageable pageable);
}
