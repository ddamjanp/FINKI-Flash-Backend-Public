package com.flash.finki.service;

import jakarta.mail.MessagingException;

public interface EmailService {

    void sendEmail(String to, String subject, String content) throws MessagingException;

    void sendVerificationEmail(String to, String subject, String text) throws MessagingException;
}
