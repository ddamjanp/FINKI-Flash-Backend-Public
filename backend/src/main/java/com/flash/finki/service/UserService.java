package com.flash.finki.service;

import com.flash.finki.model.User;
import com.flash.finki.request.PasswordChangeRequest;
import com.flash.finki.request.VerificationRequest;

import java.util.List;

public interface UserService {

    User findUserByJwtToken(String jwt) throws Exception;

    User findUserByEmail(String email) throws Exception;

    void verifyUser(VerificationRequest request) throws Exception;

    void resendVerificationCode(String email) throws Exception;

    void initiatePasswordReset(String email) throws Exception;

    void resetPassword(PasswordChangeRequest request) throws Exception;

    // Admin functions
    List<User> findAllUsers();

    void updateUserStatus(Long userId, boolean enabled) throws Exception;
}