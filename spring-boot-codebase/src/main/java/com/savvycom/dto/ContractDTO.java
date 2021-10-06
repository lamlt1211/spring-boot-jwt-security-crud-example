package com.savvycom.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

/**
 * @author lam.le
 * @created 01/09/2021
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContractDTO {
    private Long no;
    @NotEmpty
    private String contractId;
    @NotEmpty
    private String customerName;
    @NotEmpty
    private String contractType;
    @NotEmpty
    private String salesPic;
    private Date createDate;
    private int status;
}
