package org.example.userservice.feign;

import org.example.userservice.bean.JWT;
import org.example.userservice.hystrix.AuthServiceHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "uaa-service", fallback = AuthServiceHystrix.class)
public interface AuthServiceClient {

    @PostMapping("/oauth/token")
    JWT getToken(@RequestHeader(value="Authorization")String authorization,
                 @RequestParam("grant_type")String type,
                 @RequestParam("username")String username,
                 @RequestParam("password")String password);
}
