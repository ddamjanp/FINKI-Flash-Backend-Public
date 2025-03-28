package com.flash.finki.service.impl;

import com.flash.finki.config.JwtTokenProvider;
import com.flash.finki.model.User;
import com.flash.finki.repository.UserRepository;
import com.flash.finki.request.PasswordChangeRequest;
import com.flash.finki.request.VerificationRequest;
import com.flash.finki.service.EmailService;
import com.flash.finki.service.UserService;
import jakarta.mail.MessagingException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, JwtTokenProvider jwtTokenProvider,
                           EmailService emailService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User findUserByJwtToken(String jwt) throws Exception {
        String email = jwtTokenProvider.getEmailFromJwtToken(jwt);
        User user = findUserByEmail(email);
        return user;
    }

    @Override
    public User findUserByEmail(String email) throws Exception {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new Exception("User not found!");
        }
        return user;
    }

    @Override
    public void verifyUser(VerificationRequest request) throws Exception {
        User user = findUserByEmail(request.getEmail());

        if (user.isEnabled()) {
            throw new Exception("Account already verified!");
        }

        if (user.getVerificationExpiration().isBefore(LocalDateTime.now())) {
            throw new Exception("Verification code expired. Please request a new one.");
        }

        if (user.getVerificationCode().equals(request.getVerificationCode())) {
            user.setEnabled(true);
            user.setVerificationCode(null);
            user.setVerificationExpiration(null);
            userRepository.save(user);
        } else {
            throw new Exception("Invalid verification code.");
        }
    }

    @Override
    public void resendVerificationCode(String email) throws Exception {
        User user = findUserByEmail(email);

        if (user.isEnabled()) {
            throw new Exception("Account already verified!");
        }

        user.setVerificationCode(generateVerificationCode());
        user.setVerificationExpiration(LocalDateTime.now().plusMinutes(15));

        sendVerificationEmail(user);

        userRepository.save(user);
    }

    @Override
    public void initiatePasswordReset(String email) throws Exception {
        User user = findUserByEmail(email);

        // Generate 6-digit reset code
        String resetCode = generateVerificationCode();

        // Set expiration to 15 minutes from now
        LocalDateTime expiration = LocalDateTime.now().plusMinutes(15);

        user.setResetPasswordCode(resetCode);
        user.setResetPasswordExpiration(expiration);

        userRepository.save(user);

        // Send password reset email with code
        sendPasswordResetEmail(user, resetCode);
    }

    @Override
    public void resetPassword(PasswordChangeRequest request) throws Exception {
        // First find user by email
        User user = findUserByEmail(request.getEmail());

        // Check if reset code matches
        if (user.getResetPasswordCode() == null || !user.getResetPasswordCode().equals(request.getResetCode())) {
            throw new Exception("Invalid reset code");
        }

        // Check if code is expired
        if (user.getResetPasswordExpiration().isBefore(LocalDateTime.now())) {
            throw new Exception("Reset code has expired. Please request a new one.");
        }

        // Update password
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));

        // Clear reset code
        user.setResetPasswordCode(null);
        user.setResetPasswordExpiration(null);

        userRepository.save(user);
    }

    private void sendPasswordResetEmail(User user, String resetCode) throws Exception {
        try {
            String subject = "Password Reset Code";

            String content = "<div style='font-family: Arial, sans-serif; padding: 20px; max-width: 600px;'>"
                    + "<h2>Password Reset</h2>"
                    + "<p>Hi " + user.getFullName() + ",</p>"
                    + "<p>You requested to reset your password. Please use the code below to reset your password:</p>"
                    + "<div style='background-color: #f2f2f2; padding: 15px; border-radius: 5px; text-align: center; margin: 20px 0;'>"
                    + "<h1 style='color: #4CAF50; letter-spacing: 5px; font-size: 28px;'>" + resetCode + "</h1>"
                    + "</div>"
                    + "<p>This code will expire in 15 minutes.</p>"
                    + "<p>If you didn't request a password reset, please ignore this email or contact support if you have concerns.</p>"
                    + "<p>Regards,<br/>Your Application Team</p>"
                    + "</div>";

            emailService.sendEmail(user.getEmail(), subject, content);
        } catch (MessagingException e) {
            throw new Exception("Error sending password reset email: " + e.getMessage());
        }
    }

    private void sendVerificationEmail(User user) throws Exception {
        try {
            String subject = "Verify Your Account";

            String content = "<div style='font-family: Arial, sans-serif; padding: 20px; max-width: 600px;'>"
                    + "<h2>Account Verification</h2>"
                    + "<p>Hi " + user.getFullName() + ",</p>"
                    + "<p>Thanks for signing up! Please use the verification code below to activate your account:</p>"
                    + "<div style='background-color: #f2f2f2; padding: 15px; border-radius: 5px; text-align: center; margin: 20px 0;'>"
                    + "<h1 style='color: #4CAF50; letter-spacing: 5px; font-size: 28px;'>" + user.getVerificationCode()
                    + "</h1>"
                    + "</div>"
                    + "<p>This code will expire in 15 minutes.</p>"
                    + "<p>If you didn't create an account, please ignore this email.</p>"
                    + "<p>Regards,<br/>Your Application Team</p>"
                    + "</div>";

            emailService.sendEmail(user.getEmail(), subject, content);
        } catch (MessagingException e) {
            throw new Exception("Error sending verification email: " + e.getMessage());
        }
    }

    private String generateVerificationCode() {
        Random random = new Random();
        int code = random.nextInt(900000) + 100000; // 6-digit code
        return String.valueOf(code);
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void updateUserStatus(Long userId, boolean enabled) throws Exception {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new Exception("User not found with ID: " + userId));

        user.setEnabled(enabled);
        userRepository.save(user);
    }
}

