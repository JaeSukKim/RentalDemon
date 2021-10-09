package com.rental.demon.dataprovider.mail;

public interface IMailSender {
    void sendMail(final String fromAddress, final String toAddress, final String subject, final String content);
}
