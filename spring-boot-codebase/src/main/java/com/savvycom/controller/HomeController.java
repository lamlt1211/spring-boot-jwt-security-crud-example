package com.savvycom.controller;

import org.springframework.web.bind.annotation.GetMapping;

public class HomeController {
    @GetMapping("/")
    public String login() {
        return "home";
    }
}
