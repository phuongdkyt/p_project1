package com.example.demo.services.impl;

import com.example.demo.services.MailService;

import javax.mail.MessagingException;
import javax.validation.constraints.Email;

public class MailServiceImpl implements MailService {
    @Override
    public void sendEmail(Email email) throws MessagingException {

    }

    @Override
    public void resetPassword(String email, String token) throws MessagingException {

    }
}
