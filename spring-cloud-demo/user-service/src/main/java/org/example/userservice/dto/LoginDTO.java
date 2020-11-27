package org.example.userservice.dto;

import lombok.Data;
import org.example.userservice.bean.User;

@Data
public class LoginDTO {

    private User user;

    private String token;
}
