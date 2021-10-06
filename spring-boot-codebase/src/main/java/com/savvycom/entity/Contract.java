package com.savvycom.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author lam.le
 * @created 01/09/2021
 */
@Entity
@Table(name = "contract")
@Data
public class Contract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;
    @Column(name = "contractId")
    private String contractId;
    @Column(name = "contractType")
    private String contractType;
    @Column(name = "salesPic")
    private String salesPic;
    @Column(name = "createDate")
    private Date createDate;
    @Column(name = "status")
    private int status;

    // một contract nằm trong một customer
    @ManyToOne
    @JoinColumn(name = "customer_name")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Customer customer;

}
