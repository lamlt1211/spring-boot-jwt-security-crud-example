package com.savvycom.services;

import com.savvycom.dto.UserDTO;

/**
 * @author lam.le
 * @created 28/09/2021
 */
public interface UserService {
    UserDTO getUserByUserName(String name);
}
