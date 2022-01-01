package com.example.demo.services;

import javax.mail.MessagingException;
import javax.validation.constraints.Email;

public interface MailService {
    void sendEmail(Email email) throws MessagingException;

    void resetPassword(String email, String token) throws MessagingException;

}
