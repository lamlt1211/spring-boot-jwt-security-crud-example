package com.savvycom.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author lam.le
 * @created 21/09/2021
 */

@Entity
@Table(name = "noti")
@Data
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    @Column
    public String user;
    @Column
    public String notification;
    @Column
    public int isRead;
    @Column
    @CreatedDate
    public Date createdDate;
    @Column
    public String createdUser;
}
