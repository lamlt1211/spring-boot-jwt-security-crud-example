package com.savvycom.services;

import com.savvycom.dto.RoleDTO;

import java.util.List;

/**
 * @author lam.le
 * @created 28/09/2021
 */
public interface RoleService {
    List<RoleDTO> findByUsersUserName(String userName);
}
