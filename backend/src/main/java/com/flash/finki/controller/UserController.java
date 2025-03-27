package com.flash.finki.controller;

import com.flash.finki.model.User;
import com.flash.finki.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile")
    public ResponseEntity<User> findUserByJwtToken(@RequestHeader("Authorization") String jwtToken) throws Exception {

        User user = userService.findUserByJwtToken(jwtToken);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
