package org.example.userservice.service.impl;

import org.example.userservice.bean.JWT;
import org.example.userservice.bean.User;
import org.example.userservice.dao.UserDao;
import org.example.userservice.dto.LoginDTO;
import org.example.userservice.exception.LoginException;
import org.example.userservice.feign.AuthServiceClient;
import org.example.userservice.service.UserService;
import org.example.userservice.util.BPwdEncoderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    @Autowired
    AuthServiceClient client;

    @Override
    public LoginDTO login(String username, String password) {
        User user = userDao.findByUsername(username);
        if (null == user) {
            throw new LoginException("username error");
        }

        if (!BPwdEncoderUtil.matches(password, user.getPassword())) {
            throw new LoginException("pasword error");
        }
        JWT jwt = client.getToken("Basic dXNlci1zZXJ2aWNlOjEyMzQ1Ng==", "password", username, password);
        if (null == jwt) {
            throw new LoginException("jwt error");
        }
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setUser(user);
        loginDTO.setToken(jwt.getAccess_token());
        return loginDTO;
    }

    @Override
    public User insertUser(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(BPwdEncoderUtil.encode(password));
        return userDao.save(user);
    }



}
