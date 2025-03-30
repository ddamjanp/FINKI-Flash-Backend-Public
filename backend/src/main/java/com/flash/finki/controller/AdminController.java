package com.flash.finki.controller;

import com.flash.finki.model.User;
import com.flash.finki.request.UserStatusRequest;
import com.flash.finki.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.findAllUsers();
        return ResponseEntity.ok(users);
    }

    @PutMapping("/users/{userId}/status")
    public ResponseEntity<?> updateUserStatus(@PathVariable Long userId, @RequestBody UserStatusRequest request) {
        try {
            userService.updateUserStatus(userId, request.isEnabled());
            String status = request.isEnabled() ? "activated" : "deactivated";
            return ResponseEntity.ok("User successfully " + status);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
