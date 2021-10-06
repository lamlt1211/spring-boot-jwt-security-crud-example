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
public class UserDTO {
    private Long id;
    private String email;
    private String password;
    private String profilePicture;
    private String firstName;
    private String lastName;
    private int status;
    private List<RoleDTO> roleDTOList;
}
