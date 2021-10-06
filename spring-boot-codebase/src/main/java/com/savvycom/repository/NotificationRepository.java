package com.savvycom.repository;

import com.savvycom.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author lam.le
 * @created 21/09/2021
 */
@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    @Query("SELECT n.id, n.user,n.notification,n.isRead FROM Notification n WHERE n.user= :user")
    List getNotificationByUser(String user);

    //List cancel(String dmName);
}
