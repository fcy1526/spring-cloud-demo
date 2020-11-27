package org.example.userservice.controller;

import org.example.userservice.annotation.SysLogger;
import org.example.userservice.bean.User;
import org.example.userservice.dto.LoginDTO;
import org.example.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/login")
    @SysLogger("login")
    public LoginDTO login(@RequestParam("username") String username,
                          @RequestParam("password") String password) {
        return userService.login(username, password);
    }

    @PostMapping("/register")
    public User postUser(@RequestParam("username") String username,
                         @RequestParam("password") String password) {
        return userService.insertUser(username, password);
    }

    @GetMapping("/hello")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String hello(@RequestParam("username") String username) {
        return "hello, " + username;
    }
}
