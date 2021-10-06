package com.savvycom.repository;

import com.savvycom.entity.Role;
import com.savvycom.entity.RoleStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author lam.le
 * @created 29/09/2021
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleStatus name);
}
