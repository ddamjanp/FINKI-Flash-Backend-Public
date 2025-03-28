package com.flash.finki.response;

import com.flash.finki.model.USER_ROLE;
import lombok.Data;

@Data
public class AuthResponse {
    private  String jwt;

    private String message;

    private USER_ROLE role;
}
