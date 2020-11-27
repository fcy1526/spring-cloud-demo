package org.example.userservice.service;

import org.example.userservice.bean.User;
import org.example.userservice.dto.LoginDTO;

public interface UserService {

    LoginDTO login(String username, String password);

    User insertUser(String username, String password);
}
