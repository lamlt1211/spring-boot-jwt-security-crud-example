package com.savvycom.repository;

import com.savvycom.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findCustomerByName(String name);

    Customer getCustomerByNo(Long id);

    Optional<Customer> findCustomerByNo(Long id);

    void deleteByNo(Long no);

    @Query("SELECT DISTINCT c.country FROM Customer c")
    List getAllCountry();

    @Query("SELECT DISTINCT c.salesPic FROM Customer c")
    List getAllSalesPic();

    Customer getCustomerByName(String name);

    List<Customer> getCustomerByCountry(String country);

    List<Customer> getCustomerBySalesPic(String sales_pic);

    List<Customer> getCustomerByCustomerIdContainingOrNameContaining(String id, String name);

    @Query("SELECT c FROM Customer c")
    Page<Customer> getCustomers(Pageable pageable);

}
