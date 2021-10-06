package com.savvycom.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lam.le
 * @created 29/09/2021
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestController {
    @GetMapping("/all")
    public String allAccess() {
        return "Public Content.";
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER') or hasRole('HR')or hasRole('SALE') or hasRole('PM') or hasRole('DM')")
    public String userAccess() {
        return "User Content.";
    }

    @GetMapping("/hr")
    @PreAuthorize("hasRole('HR')")
    public String hrAccess() {
        return "HR Board.";
    }

    @GetMapping("/sale")
    @PreAuthorize("hasRole('SALE')")
    public String saleAccess() {
        return "SALE Board.";
    }

    @GetMapping("/pm")
    @PreAuthorize("hasRole('PM')")
    public String pmAccess() {
        return "PM Board.";
    }

    @GetMapping("/dm")
    @PreAuthorize("hasRole('DM')")
    public String dmAccess() {
        return "DM Board.";
    }
}
