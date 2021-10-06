package com.savvycom.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * @author lam.le
 * @created 30/09/2021
 */


@Table(name = "user_roles")
@Data
public class UserRoles {
    @Column(name = "user_id")
    private String user_id;
    @Column(name = "role_id")
    private String role_id;
}
