package com.savvycom.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @author lam.le
 * @created 28/09/2021
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoleDTO {
    private Long id;
    private String roleName;
    private List<UserDTO> userDTOList;
}
