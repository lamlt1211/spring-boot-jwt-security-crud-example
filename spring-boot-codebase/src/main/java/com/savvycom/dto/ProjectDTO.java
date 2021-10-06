package com.savvycom.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

/**
 * @author lam.le
 * @created 04/10/2021
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProjectDTO {
    private Long no;
    @NotEmpty
    private String projectCode;
    @NotEmpty
    private String projectName;
    @NotEmpty
    private String projectManager;
    private int status;
    private Date createDate;
}
