package com.savvycom.controller;

import com.savvycom.dto.APIResponse;
import com.savvycom.entity.Notification;
import com.savvycom.jwt.JwtUtils;
import com.savvycom.repository.NotificationRepository;
import com.savvycom.repository.UserRepository;
import com.savvycom.services.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author lam.le
 * @created 21/09/2021
 */

@CrossOrigin
@RestController
@RequestMapping("/api/notify")
public class NotificationController {

    @Autowired
    NotificationRepository notificationRepository;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserRepository userRepository;

    // truyền vào salename để tạo một request
    @GetMapping("/receive")
    public ResponseEntity requestNotify(@RequestParam(name = "saleName") String saleName, HttpServletRequest request) {
        APIResponse response = new APIResponse<>();
        response.setMessage("You received a request from <<" + saleName + ">>");
        response.setStatus(HttpStatus.OK.value());
        response.setData("There is no data you fool!");
        String headerAuth = request.getHeader("Authorization");
        String jwt = "";
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            jwt = headerAuth.substring(7, headerAuth.length());
        }
        String username = jwtUtils.getUserNameFromJwtToken(jwt);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        Notification notification = new Notification();
        notification.setUser(userDetails.getUsername());
        notification.setIsRead(0); // noti chua doc
        notification.setNotification("You received a request from " + saleName);
        notification.setCreatedUser(userDetails.getUsername());
        notificationRepository.save(notification);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // truyền vào user để lấy notify theo user
    @GetMapping("/getNotifyUser")
    public ResponseEntity<List> getNotifyByUser(@RequestParam(name = "user") String user) {
        List list = notificationRepository.getNotificationByUser(user);
        APIResponse<List> response = new APIResponse<>();
        response.setMessage("Successfully!");
        response.setStatus(HttpStatus.OK.value());
        response.setData(list);
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PostMapping("/writeNotify")
    public ResponseEntity getNotifyByUser(@RequestBody Notification notification) {
        notificationRepository.save(notification);
        APIResponse response = new APIResponse<>();
        response.setMessage("Successfully!");
        response.setStatus(HttpStatus.OK.value());
        response.setData(notification);
        return new ResponseEntity(response, HttpStatus.OK);
    }

//    @GetMapping("/cancel")
//    public ResponseEntity cancelNotify(@RequestParam(name = "dmName") String dmName) {
//        List list = notificationRepository.cancel(dmName);
//        APIResponse response = new APIResponse<>();
//        response.setMessage("<<" + dmName + ">>" + "canceled request" + dmName + ">>");
//        response.setStatus(HttpStatus.OK.value());
//        response.setData("Cancel success");
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }
}
