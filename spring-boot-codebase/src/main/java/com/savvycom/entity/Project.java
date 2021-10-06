package com.savvycom.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "project")
@Data
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;
    @Column(name = "projectCode")
    private String projectCode;
    @Column(name = "projectName")
    private String projectName;
    @Column(name = "projectManager")
    private String projectManager;
    @Column(name = "status")
    private int status;
    @Column(name = "createDate")
    private Date createDate;
}
