package org.example.userservice.hystrix;

import org.example.userservice.bean.JWT;
import org.example.userservice.feign.AuthServiceClient;
import org.springframework.stereotype.Component;

@Component
public class AuthServiceHystrix implements AuthServiceClient {
    @Override
    public JWT getToken(String authorization, String type, String username, String password) {
        return null;
    }
}
